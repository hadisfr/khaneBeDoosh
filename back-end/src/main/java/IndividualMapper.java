package main.java;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

public class IndividualMapper {

    private static final String IndividualTableName = "Individual";
    private static final String UsernameKey = "username";
    private static final String BalanceKey = "balance";
    private static final String DisplayNameKey = "displayName";
    private static final String dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
            "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));

    public static Individual getByUsername(String username) throws SQLException, ClassNotFoundException {
        Logger logger = Logger.getLogger(IndividualMapper.class.getName());
        logger.info(String.format("get Individual(username=%s) from %s", username, dbUri));

        Connection connection = null;
        Individual ret = null;
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection(dbUri);

            DatabaseMetaData md = connection.getMetaData();
            ResultSet trs = md.getTables(null, null, "%", null);
            while (trs.next())
                logger.info("table: " + trs.getString(3));

            PreparedStatement stmt = connection.prepareStatement(String.format("select * from %s where %s=?;",
                    IndividualTableName, UsernameKey));
            stmt.setString(1, username);
            stmt.setQueryTimeout(30);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                ret = new Individual(res.getString(UsernameKey), res.getInt(BalanceKey),
                        res.getString(DisplayNameKey));
            }
            res.close();
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

    public static void insert(Individual individual) throws SQLException, ClassNotFoundException {
        Logger logger = Logger.getLogger(IndividualMapper.class.getName());
        logger.info(String.format("insert Individual(username=%s) from %s", individual.getUsername(), dbUri));

        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUri);

            DatabaseMetaData md = connection.getMetaData();
            ResultSet trs = md.getTables(null, null, "%", null);
            while (trs.next())
                logger.info("table: " + trs.getString(3));

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("drop table if exists Individuals");
            statement.executeUpdate("CREATE TABLE Individual"
                    + "('username' TEXT PRIMARY KEY NOT NULL, 'balance' INTEGER, 'displayName' INTEGER)");
            connection.setAutoCommit(false);
            try {
                PreparedStatement insertStatement = connection.prepareStatement(String.format(
                        "insert into %s (%s, %s, %s) values(?, ?, ?)",
                        IndividualTableName, UsernameKey, BalanceKey, DisplayNameKey)
                );
                insertStatement.setString(1, individual.getUsername());
                insertStatement.setInt(2, individual.getBalance());
                insertStatement.setString(3, individual.getDisplayName());
                insertStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
            connection.setAutoCommit(true);
        } finally {
            if (connection != null)
                connection.close();
        }
    }

    public static int update(Individual individual) throws SQLException, ClassNotFoundException {
        Logger logger = Logger.getLogger(IndividualMapper.class.getName());
        logger.info(String.format("update Individual(username=%s) from %s", individual.getUsername(), dbUri));

        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        int ret = 0;
        try {
            connection = DriverManager.getConnection(dbUri);

            DatabaseMetaData md = connection.getMetaData();
            ResultSet trs = md.getTables(null, null, "%", null);
            while (trs.next())
                logger.info("table: " + trs.getString(3));

            PreparedStatement stmt = connection.prepareStatement(String.format(
                    "update %s set %s=?, %s=?, %s=? where %s=?;",
                    IndividualTableName, UsernameKey, BalanceKey, DisplayNameKey, UsernameKey
            ));
            stmt.setString(1, individual.getUsername());
            stmt.setInt(2, individual.getBalance());
            stmt.setString(3, individual.getDisplayName());
            stmt.setString(4, individual.getUsername());
            stmt.setQueryTimeout(30);
            ret = stmt.executeUpdate();
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

}
