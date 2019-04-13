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

    private String dbIP = "jdbc:postgresql://68.183.68.65:5432/postgres";
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
    @Override
    public void saveCase(String firstName, String lastName, UUID caseID, long cprNumber, String type,
            String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO SocialCase VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                PreparedStatement existCheck = db.prepareStatement("SELECT COUNT(caseID) AS total FROM SocialCase WHERE caseID = ?")) {
//            existCheck.setString(1, caseID.toString());
//            ResultSet tuples = existCheck.executeQuery();
//            tuples.next();
//            if (1 > tuples.getInt("total")) {
//            statement.setString(1, firstName);
//            statement.setString(2, lastName);
            statement.setString(1, caseID.toString());
            statement.setLong(2, cprNumber);
            statement.setString(3, type);
            statement.setString(4, mainBody);
            if (dateCreated != null) {
                statement.setDate(5, new java.sql.Date(dateCreated.getTime()));
            }
            else{
                statement.setDate(5, null);
            }
            if (dateClosed != null) {
                statement.setDate(6, new java.sql.Date(dateClosed.getTime()));
            }
            else{
                statement.setDate(6, null);
            }
            statement.setInt(7, departmentID);
            statement.setString(8, inquiry);
            statement.executeUpdate();
//            }
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
