package main.java;

import java.sql.*;

public class IndividualMapper {

    public Individual getIndividualById(int id){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(KhaneBeDoosh.dbUri);
            PreparedStatement stmt = connection.prepareStatement("select * from Individual where id=?;");
            stmt.setInt(1, id);
            stmt.setQueryTimeout(30);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                return new Individual(res.getString("username"), res.getInt("balance"), res.getString("displayname"));
            }
            res.close();
        } catch (SQLException e) {
//            resp.getWriter().write(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
//                resp.getWriter().write(e.getMessage());
            }
        }
        return null;
    }

    public Individual getIndividualByUsername(String username){
        return null;
    }
}
