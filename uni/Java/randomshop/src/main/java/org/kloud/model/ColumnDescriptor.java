package org.kloud.model;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.common.CustomDatatype;
import org.kloud.common.Field;
import org.kloud.utils.Logger;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class ColumnDescriptor<T extends Serializable> {

    @FunctionalInterface
    public interface DbReader<T> {
        T read(ResultSet set) throws SQLException;
    }

    @FunctionalInterface
    public interface DbWriter<T> {
        void write(PreparedStatement statement, T value, int index) throws SQLException;
    }

    @NotNull
    public final String datatype;
    @Nullable
    public Pair<DbReader<T>, DbWriter<T>> datatypeMapper;
    @NotNull
    public final String columnName;

    @Nullable
    private Field<T> parentField;

    @SuppressWarnings("unchecked")
    public ColumnDescriptor(@NotNull Field<T> parentField) {
        this.parentField = parentField;
        this.columnName = parentField.name.trim().toLowerCase()
                .replace("-", " ")
                .replace(" ", "_")
                .replace("(", "")
                .replace(")", "");

        var klass = parentField.klass;
        if (klass.equals(String.class)) {
            datatype = "varchar";
            datatypeMapper = new Pair<>(
                    set -> (T) set.getString(columnName),
                    (statement, value, index) -> statement.setString(index, (String) value));
        } else if (klass.equals(Integer.class)) {
            datatype = "int4";
            datatypeMapper = new Pair<>(
                    set -> (T) Integer.valueOf(set.getInt(columnName)),
                    (statement, value, index) -> statement.setInt(index, (Integer) value));
        } else if (klass.equals(Long.class)) {
            datatype = "int8";
            datatypeMapper = new Pair<>(
                    set -> (T) Long.valueOf(set.getLong(columnName)),
                    (statement, value, index) -> statement.setLong(index, (Long) value));
        } else if (klass.equals(Float.class)) {
            datatype = "float4";
            datatypeMapper = new Pair<>(
                    set -> (T) Float.valueOf(set.getFloat(columnName)),
                    (statement, value, index) -> statement.setFloat(index, (Float) value));
        } else if (klass.equals(Double.class)) {
            datatype = "float8";
            datatypeMapper = new Pair<>(
                    set -> (T) Double.valueOf(set.getDouble(columnName)),
                    (statement, value, index) -> statement.setDouble(index, (Double) value));
        } else if (klass.equals(Boolean.class)) {
            datatype = "bool";
            datatypeMapper = new Pair<>(
                    set -> (T) Boolean.valueOf(set.getBoolean(columnName)),
                    (statement, value, index) -> statement.setBoolean(index, (Boolean) value));
        } else if (klass.equals(LocalDate.class)) {
            datatype = "date";
            datatypeMapper = new Pair<>(
                    set -> (T) set.getDate(columnName).toLocalDate(),
                    (statement, value, index) -> statement.setDate(index, Date.valueOf((LocalDate) value)));
        } else if (klass.isEnum()) {
            datatype = "varchar";
            //noinspection rawtypes
            datatypeMapper = new Pair<>(
                    set -> (T) Enum.valueOf((Class<Enum>) klass, set.getString(columnName)),
                    (statement, value, index) -> statement.setString(index, ((Enum) value).name()));
        } else if (klass.equals(Color.class)) {
            datatype = "varchar";
            //noinspection rawtypes
            datatypeMapper = new Pair<>(
                    set -> {
                        var strValue = set.getString(columnName);
                        var parts = strValue.split("--");
                        return (T) new Color(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    },
                    (statement, value, index) -> {
                        var color = ((Color) value);
                        var strValue = color.getRed() + "--" + color.getGreen() + "--" + color.getBlue();
                        statement.setString(index, strValue);
                    });
        } else {
            Logger.warn("No native datatype mapping for " + parentField.klass + " will fall back to string marshalling/unmarshalling");
            datatype = "varchar";
            datatypeMapper = new Pair<>(
                    set -> {
                        try {
                            var instance = (CustomDatatype) klass.getDeclaredConstructor().newInstance();
                            instance.deserializeFromString(set.getString(columnName));
                            return (T) instance;
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                 NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    (statement, value, index) -> statement.setString(index, ((CustomDatatype) value).serializeToString()));
        }
    }

    public ColumnDescriptor(@NotNull String columnName, @NotNull String datatype) {
        this.columnName = columnName.toLowerCase();
        this.datatype = datatype.toLowerCase();
    }

    public String getDDLStatement() {
        return columnName + " " + datatype;
    }

    public void readFromDB(@NotNull ResultSet set) {
        try {
            String warn = parentField.set(datatypeMapper.getKey().read(set));
            if (!warn.isEmpty()) {
                Logger.warn(warn);
            }
        } catch (SQLException e) {
            Logger.error("Error setting " + parentField + " from DB: " + e.getMessage());
        }
    }

    public void writeToDB(@NotNull PreparedStatement statement, int paramIndex) {
        try {
            datatypeMapper.getValue().write(statement, parentField.get(), paramIndex);
        } catch (SQLException e) {
            Logger.error("Error writing " + parentField + " to DB: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Column " + columnName + " " + datatype;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ColumnDescriptor<?> that = (ColumnDescriptor<?>) object;
        return Objects.equals(datatype, that.datatype) && Objects.equals(columnName, that.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datatype, columnName);
    }
}
