/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import acquaintance.IPersistence;
import java.util.UUID;
import java.util.Date;

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
    @Override
    public void saveCase(String firstName, String lastName, UUID caseID, long cprNumber, String type,
            String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO SocialCase VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                PreparedStatement existCheck = db.prepareStatement("SELECT COUNT(caseID) FROM SocialCase WHERE caseID = ?")) {
            existCheck.setString(1, caseID.toString());
            ResultSet tuples = existCheck.executeQuery();
            tuples.next();
            if (1 > tuples.getInt(1)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, caseID.toString());
                statement.setLong(4, cprNumber);
                statement.setString(5, type);
                statement.setString(6, mainBody);
                statement.setDate(7, new java.sql.Date(dateCreated.getTime()));
                statement.setDate(8, new java.sql.Date(dateClosed.getTime()));
                statement.setInt(9, departmentID);
                statement.setString(10, inquiry);
                statement.execute();
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
    }

    @Override
    public void saveCaseUserRelation(UUID caseID, int userID) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO CaseUserRelation VALUES (?, ?)");) {
            statement.setString(1, caseID.toString());
            statement.setInt(2, userID);
            statement.execute();

        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();

        }

    }
}
