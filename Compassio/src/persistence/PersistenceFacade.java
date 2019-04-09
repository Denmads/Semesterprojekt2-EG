/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import acquaintance.IPersistence;
import java.util.ArrayList;

/**
 *
 * @author Peterzxcvbnm
 */
public class PersistenceFacade implements IPersistence {

    private String dbIP = "jdbc:postgresql://68.163.68.65:5432/postgres";
    private String username = "postgres";
    private String password = "software-f19-4";

    public PersistenceFacade() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    public String[] getCasesByUserID(int userID) {
        ArrayList<String> cases = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement(
                        "SELECT * FROM SocialCase NATURAL JOIN CaseUserRelation NATURAL JOIN CPR WHERE 'userID'=(?)")) {
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                StringBuilder queryResult = new StringBuilder();
                queryResult.append(rs.getString("firstname") + ";");
                queryResult.append(rs.getString("lastname") + ";");
                queryResult.append(rs.getString("caseid") + ";");
                queryResult.append(rs.getString("cprnumber") + ";");
                queryResult.append(rs.getString("type") + ";");
                queryResult.append(rs.getString("mainBody") + ";");
                queryResult.append(rs.getString("datecreated") + ";");
                queryResult.append(rs.getString("dateclosed") + ";");
                queryResult.append(rs.getString("departmentid") + ";");
                queryResult.append(rs.getString("inquiry"));
                cases.add(queryResult.toString());
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return (String[]) cases.toArray();
    }

    public String[] getCasesByDepartment(int departmentID) {
        ArrayList<String> cases = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement(
                        "SELECT * FROM SocialCase NATURAL JOIN CaseUserRelation NATURAL JOIN CPR WHERE 'userID'=(?)")) {
            statement.setInt(1, departmentID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                StringBuilder queryResult = new StringBuilder();
                queryResult.append(rs.getString("firstname") + ";");
                queryResult.append(rs.getString("lastname") + ";");
                queryResult.append(rs.getString("caseid") + ";");
                queryResult.append(rs.getString("cprnumber") + ";");
                queryResult.append(rs.getString("type") + ";");
                queryResult.append(rs.getString("mainBody") + ";");
                queryResult.append(rs.getString("datecreated") + ";");
                queryResult.append(rs.getString("dateclosed") + ";");
                queryResult.append(rs.getString("departmentid") + ";");
                queryResult.append(rs.getString("inquiry"));
                cases.add(queryResult.toString());
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return (String[]) cases.toArray();
    }
}
