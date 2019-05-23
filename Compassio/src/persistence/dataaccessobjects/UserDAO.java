package persistence.dataaccessobjects;

import persistence.util.PasswordTool;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import persistence.util.ArgumentParser;

/**
 * Provides standard operations for interacting with the people table in the database.
 * @author Morten Kargo Lyngesen
 */
public class UserDAO implements DataAccessObject {

    private final BasicDataSource connectionPool;

    public UserDAO() {
        this.connectionPool = DatabaseConnection.getInstance().getConnectionPool();
    }

    public void createUser(String userName, String firstName, String lastName, String password, int typeid, int departmentid) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = PasswordTool.generateSalt();
        try (
                final Connection db = connectionPool.getConnection();
                final PreparedStatement statement = db.prepareStatement("INSERT INTO people(firstname, lastname, username, hashedpassword, salt, typeid, inactive) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                final PreparedStatement relationStatement = db.prepareStatement("INSERT INTO employeesofdepartment VALUES (?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, userName);
            statement.setBytes(4, PasswordTool.hashPassword(password, salt));
            statement.setBytes(5, salt);
            statement.setInt(6, typeid);
            statement.setBoolean(7, false);
            statement.executeUpdate();

            ResultSet key = statement.getGeneratedKeys();

            if (key.next()) {
                relationStatement.setLong(1, departmentid);
                relationStatement.setLong(2, key.getLong(1));
                relationStatement.execute();
            }
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

    public boolean validateUserPassword(long userID, String password) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement existCheck = db.prepareStatement("SELECT hashedpassword, salt FROM People WHERE userID = ?")) {
            existCheck.setLong(1, userID);
            ResultSet res = existCheck.executeQuery();

            if (res.next()) {
                byte[] salt = res.getBytes("salt");
                byte[] passDB = res.getBytes("hashedpassword");
                byte[] hashedpass = PasswordTool.hashPassword(password, salt);

                return Arrays.equals(passDB, hashedpass);
            } else {
                return false;
            }

        } catch (SQLException | NumberFormatException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    public boolean changePassword(String newPassword, String username) {
        byte[] salt = PasswordTool.generateSalt();
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("UPDATE people SET hashedpassword=?, salt=? WHERE username=?");) {
            statement.setBytes(1, PasswordTool.hashPassword(newPassword, salt));
            statement.setBytes(2, salt);
            statement.setString(3, username);
            statement.execute();
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return false;
        }
        return true;
    }

    @Override
    public String[] get(String ... id) {
        if (id.length != 2) {
            return null;
        }
        String[] user = new String[5];
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("SELECT salt FROM people WHERE username=? AND inactive=false")) {
            statement.setString(1, id[0]);
            ResultSet rs = statement.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                byte[] salt = rs.getBytes("salt");

                try (PreparedStatement checkStatement = db.prepareStatement("SELECT * FROM people WHERE username=? AND hashedpassword=?")) {
                    checkStatement.setString(1, id[0]);
                    checkStatement.setBytes(2, PasswordTool.hashPassword(id[1], salt));
                    ResultSet res = checkStatement.executeQuery();
                    if (res.next()) {
                        user[0] = res.getString("userid");
                        user[1] = res.getString("username");
                        user[2] = res.getString("firstname");
                        user[3] = res.getString("lastname");
                        String[] type = UserTypeRelationDAO.getInstance().get("-id " + user[0]);
                        if(type != null){
                            user[4] = type[0];
                        }
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
    
    /**
     * Returns all users that satisfy the condition(s)
     * @param cond department ID's that the user must be associated with.
     * @return all entries that satisfy the conditions
     */
    @Override
    public List<String[]> getAll(String... cond) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement getUserType = db.prepareStatement("SELECT people.userid, username, firstname, lastname, typeid, inactive, departmentid FROM people NATURAL JOIN employeesofdepartment")) {

            ResultSet rs = getUserType.executeQuery();

            ArrayList<String[]> users = new ArrayList<>();

            while (rs.next()) {
                if (Arrays.asList(cond).contains(Long.toString(rs.getLong("departmentid")))) {
                    String[] user = new String[]{
                        rs.getLong("userid") + "",
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getInt("typeid") + "",
                        rs.getBoolean("inactive") + ""
                    };
                    users.add(user);
                }
            }
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean create(String[] args) {
        return false;
    }

    @Override
    public boolean update(long id, String... args) {
        Map<String, List<String>> options = ArgumentParser.parse(args);
                if (!options.containsKey("type") && !options.containsKey("inactive")) {
                    return false;
                }
        try (final Connection db = connectionPool.getConnection();
                final PreparedStatement statement = db.prepareStatement("UPDATE people SET typeid=?, inactive=? WHERE userid=?")) {
            statement.setInt(1, Integer.parseInt(options.get("type").get(0)));
            statement.setBoolean(2, Boolean.parseBoolean(options.get("inactive").get(0)));
            statement.setLong(3, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("DELETE FROM people WHERE userid=?")) {
            statement.setLong(1, id);
            statement.executeQuery();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
