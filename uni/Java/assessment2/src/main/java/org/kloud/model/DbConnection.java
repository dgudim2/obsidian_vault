package org.kloud.model;

import org.jetbrains.annotations.NotNull;
import org.kloud.utils.ConfigurationSingleton;
import org.kloud.utils.ErrorHandler;
import org.kloud.utils.Logger;

import java.sql.*;

public class DbConnection {

    @FunctionalInterface
    public interface PreparedStatementConsumer {
        void consume(PreparedStatement statement) throws SQLException;
    }

    @NotNull
    private final String address;
    @NotNull
    private final String pasword;
    @NotNull
    private final String user;

    private Connection rawDbConnection;

    public DbConnection() {
        var conf = ConfigurationSingleton.getInstance();
        this.address = conf.dbAddress.getValue();
        this.user = conf.dbUser.getValue();
        this.pasword = conf.dbPassword.getValue();
    }

    public boolean ensureConnected() {
        try {
            if (rawDbConnection != null && rawDbConnection.isValid(7) && !rawDbConnection.isClosed()) {
                Logger.info("Connection to " + address + " already established");
                return true;
            }
        } catch (SQLException e) {
            Logger.warn("Connection " + address + " is not valid, trying to reconnect");
        }
        try {
            rawDbConnection = DriverManager.getConnection(address, user, pasword);
            if (rawDbConnection.isReadOnly()) {
                Logger.error("Db connection " + address + " is unusable because it's read-only");
                return false;
            }
            Logger.success("Successfully connected to " + address);
            return true;
        } catch (SQLException e) {
            return Boolean.TRUE.equals(ErrorHandler.displayException(e).handleWithAction(this::ensureConnected));
        }
    }

    public void dumpDatatypeMap() {

        try {
            ResultSet rs = rawDbConnection.getMetaData().getTypeInfo();
            while (rs.next())
                System.out.println(rs.getString("TYPE_NAME") + "\t" + JDBCType.valueOf(rs.getInt("DATA_TYPE")).getName());
        } catch (SQLException e) {
            ErrorHandler.displayException(e).handleDefault();
        }
    }

    public ResultSet executeQuery(@NotNull String query) throws SQLException {
        if (!ensureConnected()) {
            Logger.error("Could not execute " + query + " because connection failed");
            return null;
        }
        var stmt = rawDbConnection.createStatement();
        //noinspection SqlSourceToSinkFlow // Yeah, I know, I know, this is unsafe, shut-up
        var set = stmt.executeQuery(query);
        Logger.success("Executed " + query);
        return set;
    }

    public boolean executeUpdate(@NotNull String query) throws SQLException {
        if (!ensureConnected()) {
            Logger.error("Could not execute " + query + " because connection failed");
            return false;
        }
        try (var stmt = rawDbConnection.createStatement()) {
            //noinspection SqlSourceToSinkFlow
            stmt.executeUpdate(query);
            Logger.success("Executed " + query);
            return true;
        }
    }

    public boolean executePreparedUpdate(@NotNull String statement, PreparedStatementConsumer parameterConsumer) throws SQLException {
        if (!ensureConnected()) {
            Logger.error("Could not execute " + statement + " because connection failed");
            return false;
        }
        //noinspection SqlSourceToSinkFlow
        try (var stmt = rawDbConnection.prepareStatement(statement)) {
            parameterConsumer.consume(stmt);
            stmt.executeUpdate();
            Logger.success("Executed " + statement);
            return true;
        }
    }

    public Boolean tableExists(String tableName) {
        if (!ensureConnected()) {
            Logger.error("Could not get catalogs because connection failed");
            return null;
        }

        String tableNameL = tableName.toLowerCase();

        try {
            var resultSet = rawDbConnection.getMetaData().getTables(null, null, null, new String[] {"TABLE"});
            while (resultSet.next()) {
                if(resultSet.getString("TABLE_NAME").toLowerCase().equals(tableNameL)) {
                    return true;
                }
            }
            resultSet.close();
            return false;
        } catch (SQLException e) {
            return ErrorHandler.displayException(e).handleWithAction(() -> tableExists(tableNameL));
        }
    }

    public void close() throws SQLException {
        if (rawDbConnection != null) {
            rawDbConnection.close();
        }
        rawDbConnection = null;
    }

    public boolean isValid() {
        return ensureConnected();
    }
}
