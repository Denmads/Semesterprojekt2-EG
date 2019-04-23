/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import acquaintance.IPersistence;
import java.util.ArrayList;
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
                PreparedStatement statement = db.prepareStatement("SELECT type\n"
                        + "WHERE institutionID =(SELECT institutionID FROM "
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

    @Override
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
    @Override
    public boolean saveCase(UUID caseID, long cprNumber, String type,
            String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO \"socialcase\" VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            if (1 > checkForCaseID(db, caseID)) {
                statement.setString(1, caseID.toString());
                statement.setLong(2, cprNumber);
                statement.setLong(8, getTypeID(db, type));
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
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
            
        }
        return false;
    }

    @Override
    public void saveCaseUserRelation(UUID caseID, ArrayList<String> userID) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("INSERT INTO CaseUserRelation VALUES (?, ?)");) {
            for (int i = 0; i < userID.size(); i++) {
                statement.setString(1, caseID.toString());
                statement.setLong(2, Long.parseLong(userID.get(i)));
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

    public long getTypeID(Connection db, String type) {
        ResultSet tuples = null;
        try (PreparedStatement statement = db.prepareStatement("SELECT \"typeID\" FROM CaseTypeRelation WHERE name = ?")) {
            statement.setString(1, type);
            tuples = statement.executeQuery();
            tuples.next();
            return tuples.getLong(1);
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return -1;
    }

    public ArrayList<String> getDepartments() {
        ArrayList<String> departments = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement statement = db.prepareStatement("SELECT departmentid, name FROM Department")) {
            ResultSet tuples = statement.executeQuery();
            while (tuples.next()) {
                if (tuples.getLong(1) != -1) {
                    String departmentInfo = tuples.getLong(1) + " " + tuples.getString(2);
                    departments.add(departmentInfo);
                } else {
                    String departmentInfo = tuples.getLong(1) + " " + "Ukendt";
                }
//                System.out.println(departmentInfo);
//                String[] wat = departmentInfo.split(" ");
//                System.out.println(departmentInfo.substring(wat[0].trim().length()));
            }
            getTypeID(db, "Misbrug");
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return departments;
    }



    @Override
    public boolean validateUserID(String userID) {
        try (Connection db = DriverManager.getConnection(dbIP, username, password);
                PreparedStatement existCheck = db.prepareStatement("SELECT COUNT(userID) AS total FROM People WHERE userID = ?")) {
            existCheck.setLong(1, Long.parseLong(userID));
            ResultSet tuples = existCheck.executeQuery();
            tuples.next();
            if (0 < tuples.getInt("total")) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException | NumberFormatException ex) {
            return false;

        }

    }
    
        public static void main(String[] args) {
        PersistenceFacade virk = new PersistenceFacade();
//        virk.saveCase(UUID.randomUUID(), (long) 1204372878, "noget", "dette er en test", new Date(), new Date(), -1, "");
        virk.getDepartments();

    }

}
