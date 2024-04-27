package org.kloud.daos;

import org.jetbrains.annotations.NotNull;
import org.kloud.model.BaseModel;
import org.kloud.model.ColumnDescriptor;
import org.kloud.common.DbConnection;
import org.kloud.utils.ErrorHandler;
import org.kloud.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Implementation of {@link BasicDAO} for interaction with a database, handles migration, table creations, etc. automatically
 * @param <T> Type of objects stored
 */
public abstract class BasicDBDAO<T extends BaseModel> extends BasicDAO<T> {

    @NotNull
    private final DbConnection conn = new DbConnection();

    @NotNull
    protected abstract String getTableName();

    @NotNull
    protected abstract List<? extends T> getStoredClasses();

    private final ColumnDescriptor<Long> idColumn = new ColumnDescriptor<>("id", "int8");
    private final ColumnDescriptor<Long> klassColumn = new ColumnDescriptor<>("klass", "varchar");

    private HashSet<ColumnDescriptor<?>> getAllColumnDescriptors() {
        var columnDescriptors = new HashSet<ColumnDescriptor<?>>();
        for (var objClass : getStoredClasses()) {
            columnDescriptors.addAll(objClass.getColumnDescriptors());
        }
        return columnDescriptors;
    }

    private boolean ensureSchema() {
        var targetTable = getTableName();
        var columnDescriptors = getAllColumnDescriptors();
        Boolean tableExists = conn.tableExists(targetTable);

        if (tableExists == null) {
            // An error occurred
            return false;
        }

        if (!tableExists) {
            Logger.info("Table " + targetTable + " does not exist, creating");
            StringBuilder sqlQuery = new StringBuilder("CREATE TABLE " + targetTable
                    + "(" + idColumn.getDDLStatement() + " NOT NULL,"
                    + klassColumn.getDDLStatement() + " NOT NULL");
            for (var descriptor : columnDescriptors) {
                sqlQuery.append(",").append(descriptor.getDDLStatement()).append("\n");
            }
            sqlQuery.append(",").append("PRIMARY KEY (").append(idColumn.columnName).append("))");
            try {
                var res = conn.executeUpdate(sqlQuery.toString());
                Logger.success("Table created: " + targetTable);
                return res;
            } catch (SQLException ex) {
                return Boolean.TRUE.equals(ErrorHandler.displayException(ex).handleWithAction(this::ensureSchema));
            }
        }

        try (var resultSet = conn.executeQuery("SELECT * FROM " + targetTable + " LIMIT 1")) {
            var metadata = resultSet.getMetaData();
            var tableColumnDescriptors = new HashSet<ColumnDescriptor<?>>();
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                tableColumnDescriptors.add(new ColumnDescriptor<>(metadata.getColumnName(i), metadata.getColumnTypeName(i)));
            }
            // Add klass and id columns to object so our migrator won't try to delete them
            columnDescriptors.add(idColumn);
            columnDescriptors.add(klassColumn);
            var allColumnDescriptors = new HashSet<>(columnDescriptors);
            allColumnDescriptors.addAll(tableColumnDescriptors);
            var res = true;
            for (var desc : allColumnDescriptors) {
                if (tableColumnDescriptors.contains(desc) && columnDescriptors.contains(desc)) {
                    Logger.debug(desc + " matches");
                    continue;
                }

                if (tableColumnDescriptors.contains(desc) && !columnDescriptors.contains(desc)) {
                    Logger.info(desc + " exists in table, but not on our side, deleting");
                    res = conn.executeUpdate("ALTER TABLE " + targetTable + " DROP COLUMN " + desc.columnName) && res;
                    continue;
                }

                if (!tableColumnDescriptors.contains(desc) && columnDescriptors.contains(desc)) {
                    Logger.info(desc + " exists on our side, but not in the table, creating");
                    res = conn.executeUpdate("ALTER TABLE " + targetTable + " ADD " + desc.getDDLStatement()) && res;
                }
            }
            return res;
        } catch (SQLException e) {
            // TABLE exists but there was an access error
            return Boolean.TRUE.equals(ErrorHandler.displayException(e).handleWithAction(this::ensureSchema));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected @NotNull ArrayList<T> readObjectsInternal() {
        var readObjects = new ArrayList<T>();
        if (!ensureSchema()) {
            return readObjects;
        }
        var columnDescriptors = getAllColumnDescriptors();
        StringBuilder sqlQuery = new StringBuilder("SELECT " + idColumn.columnName + "," + klassColumn.columnName);
        for (var desc : columnDescriptors) {
            sqlQuery.append(",").append(desc.columnName);
        }
        sqlQuery.append(" from ").append(getTableName()).append(";");
        try (var result = conn.executeQuery(sqlQuery.toString())) {
            try {
                while (result.next()) {
                    T object = (T) Class.forName(result.getString(klassColumn.columnName))
                            .getDeclaredConstructor(long.class).newInstance(result.getLong(idColumn.columnName));
                    for (var field : object.getFields()) {
                        field.getColumnDescriptor().readFromDB(result);
                    }
                    readObjects.add(object);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                Logger.error("Error instantiating: " + e.getMessage());
            }
        } catch (SQLException e) {
            var res = ErrorHandler.displayException(e).handleWithAction(this::readObjectsInternal);
            return res == null ? readObjects : res;
        }
        return readObjects;
    }

    @Override
    protected boolean writeObjectsInternal() {
        return true;
    }

    @Override
    public boolean addOrUpdateObject(@NotNull T product) {
        if (!ensureSchema()) {
            return false;
        }
        var columnDescriptors = product.getColumnDescriptors();
        StringBuilder columns = new StringBuilder("(" + idColumn.columnName + "," + klassColumn.columnName);
        StringBuilder values = new StringBuilder("(?, ?");
        for (var descriptor : columnDescriptors) {
            columns.append(", ").append(descriptor.columnName);
            values.append(", ?");
        }
        columns.append(")");
        values.append(")");
        StringBuilder sqlStatement = new StringBuilder("INSERT INTO " + getTableName() + " " + columns
                + " VALUES " + values
                + " ON CONFLICT (" + idColumn.columnName + ") "
                + " DO UPDATE SET " + klassColumn.columnName + " = EXCLUDED." + klassColumn.columnName);
        for (var descriptor : columnDescriptors) {
            sqlStatement.append(", ").append(descriptor.columnName).append(" = EXCLUDED.").append(descriptor.columnName);
        }
        sqlStatement.append(";");
        boolean res;
        try {
            res = conn.executePreparedUpdate(sqlStatement.toString(), statement -> {
                // Start from 3 because 1 and 2 are our id and klass fields
                int index = 3;
                statement.setLong(1, product.id);
                statement.setString(2, product.getClass().getName());
                for (var descriptor : columnDescriptors) {
                    descriptor.writeToDB(statement, index);
                    index++;
                }
            });
        } catch (SQLException e) {
            res = Boolean.TRUE.equals(ErrorHandler.displayException(e).handleWithAction(() -> addOrUpdateObject(product)));
        }
        if (res) {
            return super.addOrUpdateObject(product);
        }
        return false;
    }

    @Override
    public boolean removeObject(@NotNull T product) {
        if (!ensureSchema()) {
            return false;
        }
        var sqlStatement = "DELETE FROM " + getTableName() + " WHERE id = " + product.id + ";";
        boolean res;
        try {
            res = conn.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            res = Boolean.TRUE.equals(ErrorHandler.displayException(e).handleWithAction(() -> removeObject(product)));
        }
        if (res) {
            return super.removeObject(product);
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return conn.isValid();
    }

    @Override
    public void close() {
        conn.close();
    }
}
