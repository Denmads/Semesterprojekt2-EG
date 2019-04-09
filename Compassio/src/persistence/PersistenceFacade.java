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

    private String dbIP = "jdbc:postgresql://68.18S3.68.65:5432/postgres";
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
    /**
     * Get the cases connected to the userID
     *
     * @param userID The userID for which all the cases are connected to
     * @return An ArrayList with a String array containing all the attributes of
     * the case
     */
    @Override
    public ArrayList<String[]> getCasesByUserID(int userID) {
        ArrayList<String[]> cases = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement(
                        "SELECT * FROM SocialCase NATURAL JOIN CaseUserRelation NATURAL JOIN CPR WHERE 'userID'=(?)")) {
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] singleCase = new String[10];
                singleCase[0] = rs.getString("firstname");
                singleCase[1] = rs.getString("lastname");
                singleCase[2] = rs.getString("caseid");
                singleCase[3] = rs.getString("cprnumber");
                singleCase[4] = rs.getString("type");
                singleCase[5] = rs.getString("mainBody");
                singleCase[6] = rs.getString("datecreated");
                singleCase[7] = rs.getString("dateclosed");
                singleCase[8] = rs.getString("departmentid");
                singleCase[9] = rs.getString("inquiry");
                cases.add(singleCase);
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return cases;
    }

    /**
     * Get the cases connected to the departmentID
     *
     * @param departmentID The userID for which all the cases are connected to
     * @return An ArrayList with a String array containing all the attributes of
     * the case
     */
    @Override
    public ArrayList<String[]> getCasesByDepartment(int departmentID) {
        ArrayList<String[]> cases = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement(
                        "SELECT * FROM SocialCase NATURAL JOIN CPR WHERE 'departmentID'=(?)")) {
            statement.setInt(1, departmentID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] singleCase = new String[10];
                singleCase[0] = rs.getString("firstname");
                singleCase[1] = rs.getString("lastname");
                singleCase[2] = rs.getString("caseid");
                singleCase[3] = rs.getString("cprnumber");
                singleCase[4] = rs.getString("type");
                singleCase[5] = rs.getString("mainBody");
                singleCase[6] = rs.getString("datecreated");
                singleCase[7] = rs.getString("dateclosed");
                singleCase[8] = rs.getString("departmentid");
                singleCase[9] = rs.getString("inquiry");
                cases.add(singleCase);
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return cases;
    }
}
