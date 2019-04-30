package persistence.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author 
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
 */
public class CaseDAO {

    private final BasicDataSource connectionPool;

    public CaseDAO(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    public ArrayList<String> retrieveCaseTypeNames() {
        ArrayList<String> types = new ArrayList<>();
        try (Connection db = connectionPool.getConnection()) {
            ResultSet result = db.prepareStatement("SELECT name FROM casetyperelation").executeQuery();
            while (result.next()) {
                types.add(result.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return types;
    }

    public boolean saveCase(UUID caseID, long cprNumber, String type,
            String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("INSERT INTO socialcase VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (caseid) DO UPDATE SET (mainbody, departmentid, inquiry, typeid) = (?, ?, ?, ?)")) {
                statement.setString(1, caseID.toString());
                statement.setLong(2, cprNumber);
                statement.setLong(8, getTypeID(db, type));
                statement.setLong(12, getTypeID(db, type));
                statement.setString(3, mainBody);
                statement.setString(9, mainBody);
                
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
                statement.setInt(10, departmentID);
                statement.setString(7, inquiry);
                statement.setString(11, inquiry);
                statement.execute();
                return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private int checkForCaseID(Connection db, UUID caseID) {
        try (PreparedStatement existCheck = db.prepareStatement("SELECT COUNT(caseID) AS total FROM SocialCase WHERE caseID = ?")) {
            existCheck.setString(1, caseID.toString());
            ResultSet tuples = existCheck.executeQuery();
            tuples.next();
            return tuples.getInt("total");

        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    private long getTypeID(Connection db, String type) {
        try (PreparedStatement statement = db.prepareStatement("SELECT typeid FROM CaseTypeRelation WHERE name = ?")) {
            statement.setString(1, type);
            ResultSet tuples = statement.executeQuery();
            tuples.next();
            return tuples.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<String[]> getCasesByUserID(String userID) {
        ArrayList<String[]> cases = new ArrayList<>();
        try (Connection db = connectionPool.getConnection();
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
                if (rs.getString("datecreated") != null) {
                    singleCase[6] = rs.getString("datecreated").substring(0, 10);
                }
                if (rs.getString("dateclosed") != (null)) {
                    singleCase[7] = rs.getString("dateclosed").substring(0, 10);
                }
                singleCase[8] = rs.getString("departmentid");
                singleCase[9] = rs.getString("inquiry");
                cases.add(singleCase);
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return cases;
    }

    public ArrayList<String[]> getCasesByDepartment(long departmentID) {
        ArrayList<String[]> cases = new ArrayList<>();
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement(
                        "SELECT * FROM SocialCase NATURAL JOIN CPR NATURAL JOIN CaseTypeRelation WHERE 'departmentID'=(?)")) {
            statement.setLong(1, departmentID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String[] singleCase = new String[10];
                singleCase[0] = rs.getString("firstname");
                singleCase[1] = rs.getString("lastname");
                singleCase[2] = rs.getString("caseid");
                singleCase[3] = rs.getString("cprnumber");
                singleCase[4] = rs.getString("name");
                singleCase[5] = rs.getString("mainBody");
                if (!rs.getString("datecreated").equals("")) {
                    singleCase[6] = rs.getString("datecreated");
                }
                if (!rs.getString("dateclosed").equals("")) {
                    singleCase[7] = rs.getString("dateclosed");
                }
                singleCase[8] = rs.getString("departmentid");
                singleCase[9] = rs.getString("inquiry");
                cases.add(singleCase);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cases;
    }

    public void saveCaseUserRelation(UUID caseID, ArrayList<String> userID) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("INSERT INTO CaseUserRelation VALUES (?, ?)");) {
            for (int i = 0; i < userID.size(); i++) {
                statement.setString(1, caseID.toString());
                statement.setLong(2, Long.parseLong(userID.get(i)));
                statement.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertNewPatient(long cpr, String firstName, String lastName) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("INSERT INTO cpr VALUES (?, ?, ?)")) {
            statement.setLong(1, cpr);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String> getDepartments() {
        ArrayList<String> departments = new ArrayList<>();
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("SELECT departmentid, name FROM Department")) {
            ResultSet tuples = statement.executeQuery();
            while (tuples.next()) {
                if (tuples.getLong(1) != -1) {
                    String departmentInfo = tuples.getLong(1) + " " + tuples.getString(2);
                    departments.add(departmentInfo);
                } else {
                    String departmentInfo = tuples.getLong(1) + " " + "Ukendt";
                    departments.add(departmentInfo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return departments;
    }
    
    public String getDepartmentNameById(int departmentId) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("SELECT name FROM department WHERE departmentid=?")) {
            statement.setInt(1, departmentId);
            ResultSet rs = statement.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
