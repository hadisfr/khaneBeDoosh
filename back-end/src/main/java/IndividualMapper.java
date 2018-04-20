package main.java;

import java.io.File;
import java.sql.*;

public class IndividualMapper {

    private static final String IndividualTableName = "Individual";
    private static final String UsernameKey = "username";
    private static final String BalanceKey = "balance";
    private static final String DisplayNameKey = "displayName";
    private static final String dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
            "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));

    public static Individual getByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Individual ret = null;
//        insert(new Individual("behnam", 200, "بهنام همایون"));
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection(dbUri);
            PreparedStatement stmt = connection.prepareStatement(String.format("select * from %s where %s=?;",
                    IndividualTableName, UsernameKey));
            stmt.setString(1, username);
//            Statement stmt = connection.createStatement();
            stmt.setQueryTimeout(30);
            ResultSet res = stmt.executeQuery();
//            ResultSet res = stmt.executeQuery(String.format("select * from %s;", IndividualTableName));
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
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUri);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
//            statement.executeUpdate("drop table if exists Individuals");
//            statement.executeUpdate("CREATE TABLE Individual"
//                    + "('username' TEXT PRIMARY KEY NOT NULL, 'balance' INTEGER, 'displayName' INTEGER)");
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
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        int ret = 0;
        try {
            connection = DriverManager.getConnection(dbUri);
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
