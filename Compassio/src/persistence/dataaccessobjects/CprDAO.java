package persistence.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author kargo
 */
public class CprDAO implements DataAccessObject {
    private final BasicDataSource connectionPool;

    public CprDAO (BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public String[] get(String... id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String[]> getAll(String... cond) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(String[] args) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("INSERT INTO cpr VALUES (?, ?, ?)")) {
            statement.setLong(1, Long.parseLong(args[0]));
            statement.setString(2, args[1]);
            statement.setString(3, args[2]);
            statement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(long id, String[] args) {
         try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("UPDATE cpr SET firstname = ?, lasttname= ? WHERE cprnumber=?")) {
            statement.setLong(1, id);
            statement.executeQuery();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("DELETE FROM cpr WHERE cprnumber=?")) {
            statement.setLong(1, id);
            statement.executeQuery();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
