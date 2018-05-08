package main.java;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class IndividualMapper {

    private static final String IndividualTableName = "Individual";
    private static final String PaidHousesTableName = "PaidHouses";
    private static final String UsernameKey = "username";
    private static final String BalanceKey = "balance";
    private static final String DisplayNameKey = "displayName";
    private static final String OwnerIdKey = "ownerId";
    private static final String HouseIdKey = "houseId";
    private static final String PayerKey = "individualId";
    private static final String IsAdminKey = "isAdmin";
    private static final Logger logger = Logger.getLogger(IndividualMapper.class.getName());
    private static final String dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
            "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));

    public static Individual getByUsername(String username) throws SQLException, ClassNotFoundException {
        logger.info(String.format("get Individual(username=%s) from %s", username, dbUri));

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        Individual ret;
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
            String userName = "";
            int balance = 0;
            String displayName = "";
            boolean isAdmin = false;
            if (res.next()) {
                userName = res.getString(UsernameKey);
                balance = res.getInt(BalanceKey);
                displayName = res.getString(DisplayNameKey);
                isAdmin = res.getBoolean(IsAdminKey);
            }
            res.close();
            stmt.close();

            ret = userName.isEmpty() ? null : new Individual(username, balance, displayName, isAdmin,
                    getPaidHouses(userName, connection));
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

    private static ArrayList<StringStringPair> getPaidHouses(String userName, Connection connection)
            throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(String.format("select %s, %s from %s where %s=?;",
                OwnerIdKey, HouseIdKey, PaidHousesTableName, PayerKey));
        stmt.setString(1, userName);
        stmt.setQueryTimeout(30);
        ArrayList<StringStringPair> paidHouses = new ArrayList<StringStringPair>();
        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            paidHouses.add(new StringStringPair(res.getString(OwnerIdKey), res.getString(HouseIdKey)));
        }
        res.close();
        stmt.close();
        return paidHouses;
    }

    public static ArrayList<Individual> getIndividuals() throws SQLException, ClassNotFoundException {
        logger.info(String.format("get Individuals from %s", dbUri));

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        ArrayList<Individual> ret = new ArrayList<Individual>();
        try {
            connection = DriverManager.getConnection(dbUri);

            DatabaseMetaData md = connection.getMetaData();
            ResultSet trs = md.getTables(null, null, "%", null);
            while (trs.next())
                logger.info("table: " + trs.getString(3));

            PreparedStatement stmt = connection.prepareStatement(String.format("select * from %s;",
                    IndividualTableName));
            stmt.setQueryTimeout(30);
            ResultSet res = stmt.executeQuery();
            String userName;
            while (res.next()) {
                userName = res.getString(UsernameKey);
                if (!userName.isEmpty())
                    ret.add(new Individual(userName, res.getInt(BalanceKey), res.getString(DisplayNameKey),
                            res.getBoolean(IsAdminKey), getPaidHouses(userName, connection)));
            }
            res.close();
            stmt.close();
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

    public static int insert(Individual individual) throws SQLException, ClassNotFoundException {
        logger.info(String.format("insert Individual(username=%s) from %s", individual.getUsername(), dbUri));

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        int ret;
        try {
            connection = DriverManager.getConnection(dbUri);

            DatabaseMetaData md = connection.getMetaData();
            ResultSet trs = md.getTables(null, null, "%", null);
            while (trs.next())
                logger.info("table: " + trs.getString(3));

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            connection.setAutoCommit(false);
            try {
                PreparedStatement insertStatement = connection.prepareStatement(String.format(
                        "insert into %s (%s, %s, %s) values(?, ?, ?);",
                        IndividualTableName, UsernameKey, DisplayNameKey, BalanceKey)
                );
                insertStatement.setString(1, individual.getUsername());
                insertStatement.setString(2, individual.getDisplayName());
                insertStatement.setInt(3, individual.getBalance());
                insertStatement.setQueryTimeout(30);
                ret = insertStatement.executeUpdate();
                insertStatement.close();

                insertStatement = connection.prepareStatement(String.format(
                        "insert into %s (%s, %s, %s) values(?, ?, ?);", PaidHousesTableName,
                        PayerKey, OwnerIdKey, HouseIdKey));
                insertStatement.setQueryTimeout(30);
                for (StringStringPair pair : individual.getPaidHouses()) {
                    insertStatement.setString(1, individual.getUsername());
                    insertStatement.setString(2, pair.getFirst());
                    insertStatement.setString(3, pair.getSecond());
                    insertStatement.executeUpdate();
                }
                insertStatement.close();

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
        return ret;
    }

    public static int update(Individual individual) throws SQLException, ClassNotFoundException {
        logger.info(String.format("update Individual(username=%s) from %s", individual.getUsername(), dbUri));

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        int ret;
        try {
            connection = DriverManager.getConnection(dbUri);

            DatabaseMetaData md = connection.getMetaData();
            ResultSet trs = md.getTables(null, null, "%", null);
            while (trs.next())
                logger.info("table: " + trs.getString(3));

            connection.setAutoCommit(false);

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
            stmt.close();

            stmt = connection.prepareStatement(String.format("delete from %s where %s=?;",
                    PaidHousesTableName, PayerKey));
            stmt.setString(1, individual.getUsername());
            stmt.setQueryTimeout(30);
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement(String.format(
                    "insert into %s (%s, %s, %s) values(?, ?, ?);", PaidHousesTableName,
                    PayerKey, OwnerIdKey, HouseIdKey));
            stmt.setQueryTimeout(30);
            for (StringStringPair pair : individual.getPaidHouses()) {
                stmt.setString(1, individual.getUsername());
                stmt.setString(2, pair.getFirst());
                stmt.setString(3, pair.getSecond());
                stmt.executeUpdate();
            }
            stmt.close();

            connection.commit();
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

    public static boolean isAuthenticationValid(String username, String password)
            throws SQLException, ClassNotFoundException {
        logger.info(String.format("validate authentication (username=%s)", username));
        // TODO: validate password
        return getByUsername(username) != null;
    }
}
