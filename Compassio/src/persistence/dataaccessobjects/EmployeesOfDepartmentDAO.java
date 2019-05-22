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

/**
 *
 * @author kargo
 */
public class EmployeesOfDepartmentDAO implements DataAccessObject {
    private final BasicDataSource connectionPool;

    public EmployeesOfDepartmentDAO (BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }
    
    @Override
    public String[] get(String... id) {
        Map<String, List<String>> options = ArgumentParser.parse(id);
        if (!options.containsKey("id")) { 
            return null;
        }
        
        ArrayList<String> departments = new ArrayList();
        try (Connection db = connectionPool.getConnection();
                PreparedStatement statement = db.prepareStatement("SELECT * FROM employeesofdepartment WHERE userID=?")) {
            statement.setLong(1, Long.parseLong(options.get("id").get(0)));
            ResultSet rs = statement.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                do {
                    departments.add(rs.getString("departmentID"));
                } while (rs.next());
                return departments.toArray(new String[departments.size()]);
            }
        } catch (SQLException ex) {
            System.out.println("wierdness");
            return null;
        }
    }

    @Override
    public List<String[]> getAll(String... cond) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
