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
public class CaseTypeRelationDAO implements DataAccessObject {
    
    private final BasicDataSource connectionPool;
    
    public CaseTypeRelationDAO(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public String[] get(String... id) {
        ArrayList<String> types = new ArrayList<>();
        try (Connection db = connectionPool.getConnection()) {
            ResultSet result = db.prepareStatement("SELECT name FROM casetyperelation WHERE id").executeQuery();
            while (result.next()) {
                types.add(result.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return types.toArray(new String[0]);
    }

    @Override
    public List<String[]> getAll(String... cond) {
         ArrayList<String[]> types = new ArrayList<>();
        try (Connection db = connectionPool.getConnection()) {
            ResultSet result = db.prepareStatement("SELECT name FROM casetyperelation").executeQuery();
            while (result.next()) {
                types.add(new String[]{result.getString("name")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return types;
    }

    @Override
    public boolean create(String[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(long id, String[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(long id) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("DELETE FROM casetyperelation WHERE typeid=?")) {
            statement.setLong(1, id);
            statement.executeQuery();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

}
