/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import acquaintance.IPersistence;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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

    public ArrayList<String> retrieveCaseTypeNames() {
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

    @Override
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

    @Override
    public String getUserType(String userID) {
        ArrayList<Long> departments = this.getUserDepartments(userID);
        if (departments == null) {
            return "user";
        }
        try (Connection db = DriverManager.getConnection(dbIP, this.username, this.password);
                PreparedStatement statement = db.prepareStatement("SELECT type FROM institution "
                        + "WHERE institutionID=?");
                PreparedStatement getInstitutionID = db.prepareStatement("SELECT institutionID FROM "
                        + "InstitutionDeparmentRelatition "
                        + "WHERE deparmentID=?")) {
            getInstitutionID.setLong(1, departments.get(0));
            ResultSet institutionID = getInstitutionID.executeQuery();
            institutionID.next();
            statement.setLong(1, institutionID.getLong(1));
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

    @Override
    public String[] getUser(String username, String password) {
        String[] user = new String[4];
        try (Connection db = DriverManager.getConnection(dbIP, this.username, this.password);
                PreparedStatement statement = db.prepareStatement("SELECT salt FROM people WHERE username=?")) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                byte[] salt = rs.getBytes("salt");

                try (PreparedStatement checkStatement = db.prepareStatement("SELECT * FROM people WHERE username=? AND hashedpassword=?")) {
                    checkStatement.setString(1, username);
                    checkStatement.setBytes(2, hashPassword(password, salt));
                    ResultSet res = checkStatement.executeQuery();

                    if (res.next()) {
                        user[0] = res.getString("userid");
                        user[1] = res.getString("username");
                        user[2] = res.getString("firstname");
                        user[3] = res.getString("lastname");
                        return user;
                    } else {
                        return null;
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    ex.printStackTrace();
                    return null;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private byte[] generateSalt() {
        SecureRandom secRan = new SecureRandom();
        byte[] salt = new byte[128];
        secRan.nextBytes(salt);
        return salt;
    }

    private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return factory.generateSecret(spec).getEncoded();
    }

    /**
     * Creates a user with a hashed password
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public void createUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        byte[] pass = hashPassword("admin", salt);

        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO people VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setLong(1, 1L);
            statement.setString(2, "admin");
            statement.setString(3, "admin");
            statement.setString(4, "admin");
            statement.setBytes(5, pass);
            statement.setBytes(6, salt);

            System.out.println(statement);

            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
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
    public ArrayList<String[]> getCasesByUserID(String userID) {
        ArrayList<String[]> cases = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement(
                        "SELECT * FROM SocialCase NATURAL JOIN CaseUserRelation NATURAL JOIN CPR NATURAL JOIN CaseTypeRelation WHERE userID=(?)")) {
            statement.setLong(1, Long.parseLong(userID));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] singleCase = new String[10];
                singleCase[0] = rs.getString("firstname");
                singleCase[1] = rs.getString("lastname");
                singleCase[2] = rs.getString("caseid");
                singleCase[3] = rs.getString("cprnumber");
                singleCase[4] = rs.getString("name");
                singleCase[5] = rs.getString("mainBody");
                singleCase[6] = rs.getString("datecreated").substring(0, 10);
                singleCase[7] = rs.getString("dateclosed").substring(0, 10);
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
    public ArrayList<String[]> getCasesByDepartment(long departmentID) {
        ArrayList<String[]> cases = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement(
                        "SELECT firstname, lastname, caseid, cprnumber, name, departmentid, inquiry FROM SocialCase NATURAL JOIN CPR NATURAL JOIN CaseTypeRelation WHERE 'departmentID'=(?)")) {
            statement.setLong(1, departmentID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] singleCase = new String[10];
                singleCase[0] = rs.getString("firstname");
                singleCase[1] = rs.getString("lastname");
                singleCase[2] = rs.getString("caseid");
                singleCase[3] = rs.getString("cprnumber");
                singleCase[4] = rs.getString("name");
                singleCase[5] = rs.getString("departmentid");
                singleCase[6] = rs.getString("inquiry");
                cases.add(singleCase);
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return cases;
    }

    @Override
    public void saveCase(String firstName, String lastName, UUID caseID, long cprNumber, String type, String mainBody, java.util.Date dateCreated, java.util.Date dateClosed, int departmentID, String inquiry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
