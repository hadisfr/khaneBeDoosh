package main.java;

import java.sql.*;

public class IndividualMapper {

    private static final String IndividualTableName = "Individual";
    private static final String UsernameKey = "username";
    private static final String BalanceKey = "balance";
    private static final String DisplayNameKey = "displayName";

    public static Individual getByUsername(String username) throws SQLException {
        Connection connection = null;
        Individual ret = null;
        try {
            connection = DriverManager.getConnection(KhaneBeDoosh.dbUri);
            PreparedStatement stmt = connection.prepareStatement("select * from ? where ?=?;");
            stmt.setString(1, IndividualTableName);
            stmt.setString(2, UsernameKey);
            stmt.setString(3, username);
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

    public static int update(Individual individual) throws SQLException {
        Connection connection = null;
        int ret = 0;
        try {
            connection = DriverManager.getConnection(KhaneBeDoosh.dbUri);
            PreparedStatement stmt = connection.prepareStatement("update ? set ?=?, ?=?, ?=? where ?=?;");
            stmt.setString(1, IndividualTableName);
            stmt.setString(2, UsernameKey);
            stmt.setString(3, individual.getUsername());
            stmt.setString(4, BalanceKey);
            stmt.setInt(5, individual.getBalance());
            stmt.setString(6, DisplayNameKey);
            stmt.setString(7, individual.getDisplayName());
            stmt.setString(8, UsernameKey);
            stmt.setString(9, individual.getUsername());
            stmt.setQueryTimeout(30);
            ret = stmt.executeUpdate();
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

}
