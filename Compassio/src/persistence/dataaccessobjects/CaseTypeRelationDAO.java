package persistence.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import persistence.util.ArgumentParser;

/**
 *
 * @author Morten Kargo Lyngesen
 */
public class CaseTypeRelationDAO implements DataAccessObject {

    private final BasicDataSource connectionPool;

    public CaseTypeRelationDAO() {
        this.connectionPool = DatabaseConnection.getInstance().getConnectionPool();
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
        ArrayList<String[]> list = new ArrayList<>();
        try (Connection db = connectionPool.getConnection()) {
            ResultSet result = db.prepareStatement("SELECT * FROM casetyperelation").executeQuery();
            while (result.next()) {
                list.add(new String[]{result.getString("name"), result.getString("typeid")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    /**
     * Creates an entry in the CaseUserRelation table
     * @param args Must specify an "-id" for the case and "-users" for users assigned to case
     * @return <code>true</code> on success and <code>false</code> on failure.
     */
    @Override
    public boolean create(String... args) {
        Map<String, List<String>> options = ArgumentParser.parse(args);
        if (!options.containsKey("id") && !options.containsKey("users")) {
            return false;
        }
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("INSERT INTO CaseUserRelation VALUES (?, ?)");) {
            for (int i = 0; i < options.get("users").size(); i++) {
                statement.setString(1, options.get("id").toString());
                statement.setLong(2, Long.parseLong(options.get("users").get(i)));
                statement.execute();
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(long id, String[] args) {
        throw new UnsupportedOperationException("Not supported yet.");
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
