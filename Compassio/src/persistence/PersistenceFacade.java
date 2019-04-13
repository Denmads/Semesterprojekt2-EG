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
    public void saveCase(UUID caseID, long cprNumber, long typeID,
            String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO \"socialcase\" VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            if (1 > checkForCaseID(db, caseID)) {
                statement.setString(1, caseID.toString());
                statement.setLong(2, cprNumber);
                statement.setLong(8, typeID);
                statement.setString(3, mainBody);
                if (dateCreated != null) {
                    statement.setDate(4, new java.sql.Date(dateCreated.getTime()));
                } else {
                    statement.setDate(4, null);
                }
                if (dateClosed != null) {
                    statement.setDate(5, new java.sql.Date(dateClosed.getTime()));
                } else {
                    statement.setDate(5, null);
                }
                statement.setInt(6, departmentID);
                statement.setString(7, inquiry);
                statement.execute();
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
    }

    @Override
    public void saveCaseUserRelation(UUID caseID, int[] userID) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO CaseUserRelation VALUES (?, ?)");) {
            for (int i = 0; i < userID.length; i++) {
                statement.setString(1, caseID.toString());
                statement.setInt(2, userID[i]);
                statement.execute();
            }

        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();

        }

    }

    private int checkForCaseID(Connection db, UUID caseID) {
        try (PreparedStatement existCheck = db.prepareStatement("SELECT COUNT(caseID) AS total FROM SocialCase WHERE caseID = ?")) {
            existCheck.setString(1, caseID.toString());
            ResultSet tuples = existCheck.executeQuery();
            tuples.next();
            return tuples.getInt("total");

        } catch (SQLException ex) {
            Logger.getLogger(PersistenceFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    @Override
    public void insertNewPatient(long cpr, String firstName, String lastName) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO cpr VALUES (?, ?, ?)")) {
            statement.setLong(1, cpr);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        PersistenceFacade virk = new PersistenceFacade();
        virk.saveCase(UUID.randomUUID(), (long) 1204372878, (long) 1, "dette er en test", new Date(), new Date(), -1, "");

    }
}
