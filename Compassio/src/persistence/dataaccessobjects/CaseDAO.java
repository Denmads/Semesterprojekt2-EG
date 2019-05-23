package persistence.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Morten Kargo Lyngesen
 */
public class CaseDAO implements DataAccessObject {

    private final BasicDataSource connectionPool;
    
    private static final CaseDAO INSTANCE = new CaseDAO();

    private CaseDAO() {
        this.connectionPool = DatabaseConnection.getInstance().getConnectionPool();
    }

    public static CaseDAO getInstance() {
        return INSTANCE;
    }

    public boolean saveCase(UUID caseID, long cprNumber, String type,
            String mainBody, LocalDate dateCreated, LocalDate dateClosed, int departmentID, String inquiry) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("INSERT INTO socialcase VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (caseid) DO UPDATE SET (mainbody, departmentid, inquiry, typeid, dateclosed) = (?, ?, ?, ?, ?)")) {
            statement.setString(1, caseID.toString());
            statement.setLong(2, cprNumber);
            statement.setLong(8, getTypeID(type));
            statement.setLong(12, getTypeID(type));
            statement.setString(3, mainBody);
            statement.setString(9, mainBody);

            if (dateCreated != null) {
                statement.setDate(4, java.sql.Date.valueOf(dateCreated));
            } else {
                statement.setDate(4, null);
            }
            if (dateClosed != null) {
                statement.setDate(5, java.sql.Date.valueOf(dateClosed));
                statement.setDate(13, java.sql.Date.valueOf(dateClosed));
            } else {
                statement.setDate(5, null);
                statement.setDate(13, null);
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

    private long getTypeID(String type) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("SELECT typeid FROM CaseTypeRelation WHERE name = ?")) {
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
            return null;

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

    @Override
    public String[] get(String ... id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String[]> getAll(String ... cond) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(String... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(long id, String[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(long id) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("DELETE FROM socialcase WHERE caseid=?")) {
            statement.setLong(1, id);
            statement.executeQuery();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
