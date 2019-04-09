/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import acquaintance.IPersistence;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peterzxcvbnm
 */
public class PersistenceFacade implements IPersistence {

    private String dbIP = "jdbc:postgresql://68.183.68.65:5432/compassio";
    private String username = "postgres";
    private String password = "software-f19-4";

    public PersistenceFacade() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> retrieveCaseTypeNames () {
        ArrayList<String> types = new ArrayList<>();
        
        try (Connection db = DriverManager.getConnection(dbIP, username, password)) {
            
            ResultSet result = db.prepareStatement("SELECT name FROM casetyperelation").executeQuery();
            
            while (result.next()) {                
                types.add(result.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersistenceFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return types;
    }
    
    public ArrayList<Long> getUserDepartments(String userID) {
        ArrayList<Long> departments = new ArrayList();
        try (Connection db = DriverManager.getConnection(dbIP, this.username, this.password);
                PreparedStatement statement = db.prepareStatement("SELECT * FROM employeesofdepartment WHERE userID=?")) {
            statement.setLong(1, Long.parseLong(userID));
            ResultSet rs = statement.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                do {
                    departments.add(rs.getLong("departmentID"));
                } while (rs.next());
                return departments;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getUserType(String userID) {
        ArrayList<Long> departments = this.getUserDepartments(userID);
        if (departments == null) {
            return "user";
        }
        try (Connection db = DriverManager.getConnection(dbIP, this.username, this.password);
                PreparedStatement statement = db.prepareStatement("SELECT type\n" +
                "WHERE institutionID =(SELECT institutionID FROM "
                        + "InstitutionDeparmentRelatition "
                        + "WHERE deparmentID=?)")) {
            statement.setLong(1, departments.get(0));
            ResultSet rs = statement.executeQuery();

            if (rs.next() == false) {
                return "user";
            } else if (rs.getString("type").equals("Kommune")) {
                return "caseworker";
            } else {
                return "socialworker";
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "user";
        }
    }

    public String[] getUser(String username, String password) {
        String[] user = new String[4];
        try (Connection db = DriverManager.getConnection(dbIP, this.username, this.password);
                PreparedStatement statement = db.prepareStatement("SELECT * FROM people WHERE username=? AND hashedpassword =?")) {
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
