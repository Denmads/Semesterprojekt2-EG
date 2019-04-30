package persistence.dataaccessobjects;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
 */
public class UserDAO {

    private final PasswordTool passTool;
    private final BasicDataSource connectionPool;

    public UserDAO(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
        passTool = new PasswordTool();
    }

    public ArrayList<Long> getUserDepartments(String userID) {
        ArrayList<Long> departments = new ArrayList();
        try (Connection db = connectionPool.getConnection();
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void createUser(String userName, String firstName, String lastName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = this.passTool.generateSalt();
        try (
                final Connection db = connectionPool.getConnection();
                final PreparedStatement statement = db.prepareStatement("INSERT INTO people VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setLong(1, 1L);
            statement.setString(2, userName);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setBytes(5, this.passTool.hashPassword(password, salt));
            statement.setBytes(6, salt);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validateUserID(String userID) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement existCheck = db.prepareStatement("SELECT COUNT(userID) AS total FROM People WHERE userID = ?")) {
            existCheck.setLong(1, Long.parseLong(userID));
            ResultSet tuples = existCheck.executeQuery();
            tuples.next();
            return 0 < tuples.getInt("total");

        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }
    
    public String[] getUser(String username, String password) {
        String[] user = new String[5];
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("SELECT salt FROM people WHERE username=?")) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                byte[] salt = rs.getBytes("salt");

                try (PreparedStatement checkStatement = db.prepareStatement("SELECT * FROM people WHERE username=? AND hashedpassword=?")) {
                    checkStatement.setString(1, username);
                    checkStatement.setBytes(2, this.passTool.hashPassword(password, salt));
                    ResultSet res = checkStatement.executeQuery();
                    if (res.next()) {
                        user[0] = res.getString("userid");
                        user[1] = res.getString("username");
                        user[2] = res.getString("firstname");
                        user[3] = res.getString("lastname");
                        user[4] = this.getUserType(user[0]);
                        return user;
                    } else {
                        return null;
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private String getUserType(String userID) {
        ArrayList<Long> departments = this.getUserDepartments(userID);
        if (departments == null) {
            return "user";
        }
        try (Connection db = connectionPool.getConnection();
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "user";
        }
    }
}