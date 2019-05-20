package persistence.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
 */
public class DepartmentDAO {
   
    private final BasicDataSource connectionPool;

    public DepartmentDAO(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
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
