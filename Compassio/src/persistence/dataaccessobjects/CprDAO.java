package persistence.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Provides standard operations for the cpr table in the database.
 * @author kargo
 */
public class CprDAO implements DataAccessObject {
    private final BasicDataSource connectionPool;

    public CprDAO (BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public String[] get(String... id) {
      return null;  
    }

    @Override
    public List<String[]> getAll(String... cond) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(String... args) {
        Map<String, List<String>> options = ArgumentParser.parse(args);
        if (!options.containsKey("cpr") && !options.containsKey("firstname")
                && !options.containsKey("lastname")) { 
            return false;
        }
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("INSERT INTO cpr VALUES (?, ?, ?)")) {
            statement.setLong(1, Long.parseLong(options.get("cpr").get(0)));
            statement.setString(2, options.get("firstname").get(0));
            statement.setString(3, options.get("lastname").get(0));
            statement.execute();
            return true;
        } catch (SQLException ex) {
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
