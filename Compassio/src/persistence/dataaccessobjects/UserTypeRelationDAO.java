package persistence.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Morten Kargo Lyngesen
 */
public class UserTypeRelationDAO implements DataAccessObject {

    private final BasicDataSource connectionPool;

    public UserTypeRelationDAO() {
        this.connectionPool = DatabaseConnection.getInstance().getConnectionPool();
    }

    @Override
    public String[] get(String... id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String[]> getAll(String... cond) {
         try (Connection db = connectionPool.getConnection();
                PreparedStatement getUserType = db.prepareStatement("SELECT * FROM usertyperelation")) {

            ResultSet rs = getUserType.executeQuery();

            if (rs.next()) {
                ArrayList<String[]> names = new ArrayList<>();
                do {
                    names.add(new String[]{rs.getInt("typeid") + "," + rs.getString("name")});
                } while (rs.next());
                return names;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public boolean create(String... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(long id, String... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
