/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import acquaintance.IPersistence;

/**
 *
 * @author Peterzxcvbnm
 */
public class PersistenceFacade implements IPersistence {

    private String dbIP = "jdbc:postgresql://139.59.208.42:5432/postgres";
    private String username = "postgres";
    private String password = "compassio";

    public PersistenceFacade() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String[] retrieveUser(String username, String password) {
        String[] user = new String[4];
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("SELECT * FROM people WHERE username=? AND password =?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                user[0] = rs.getString("userid");
                user[1] = rs.getString("username");
                user[2] = rs.getString("firstname");
                user[3] = rs.getString("lastname");
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    public void example() {
//         try (Connection db = DriverManager.getConnection(dbIP, username, password);
//                PreparedStatement statement = db.prepareStatement("INSERT INTO test VALUES (?, ?)")) {
//            statement.setString(1, "Peter");
//            statement.setInt(2, 2);
//            statement.execute();
//        } catch (SQLException ex) {
//            System.out.println("SQL exception");
//            ex.printStackTrace();
//        }
//    }
}
