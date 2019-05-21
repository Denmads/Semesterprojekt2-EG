package persistence;

import org.apache.commons.dbcp2.BasicDataSource;

import acquaintance.IPersistence;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import persistence.dataaccessobjects.*;

/**
 * Provides a facade to interact with the database through Data Access Objects.
 *
 * @author Peterzxcvbnm
 * @author Morten Kargo Lyngesen
 */
public class PersistenceFacade implements IPersistence {

    //Database connection parameters
    //#TODO: Move to a secure location
    private final String dbIP = "jdbc:postgresql://68.183.68.65:5432/compassio";
    private final String username = "postgres";
    private final String password = "software-f19-4";

    private final BasicDataSource connectionPool;

    //Data Access Objects
    private final UserDAO userDao;
    private final CaseDAO caseDao;
    private final DepartmentDAO departmentDao;
    private final CaseTypeRelationDAO caseTypeRelationDao;
    private final CprDAO cprDao;

    public PersistenceFacade() {
        //Configure connection pool
        connectionPool = new BasicDataSource();
        connectionPool.setUsername(this.username);
        connectionPool.setPassword(this.password);
        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(this.dbIP);
        connectionPool.setInitialSize(10);
        
        //Initialize DAO's
        userDao = new UserDAO(this.connectionPool);
        caseDao = new CaseDAO(this.connectionPool);
        departmentDao = new DepartmentDAO(this.connectionPool);
        caseTypeRelationDao = new CaseTypeRelationDAO(this.connectionPool);
        cprDao = new CprDAO(this.connectionPool);
    }
    
    //==========================================================================
    // Case methods
    //==========================================================================
    @Override
    public ArrayList<String> retrieveCaseTypeNames() {
        ArrayList<String[]> result = (ArrayList<String[]>) this.caseTypeRelationDao.getAll();
        Long[] array = departments.toArray(new String[result.size()]);
        String[] data = new String[departments.size()];
        for (int i = 0; i < departments.size(); i++) {
            data[i] = Long.toString(array[i]);
        }
        return this.caseTypeRelationDao.getAll().toArray(new String[result.size()]);
    }

    @Override
    public boolean saveCase(UUID caseID, long cprNumber, String type,
            String mainBody, LocalDate dateCreated, LocalDate dateClosed, int departmentID, String inquiry) {
        return this.caseDao.saveCase(caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
    }

    @Override
    public ArrayList<String[]> getCasesByUserID(String userID) {
        return this.caseDao.getCasesByUserID(userID);
    }

    @Override
    public void saveCaseUserRelation(UUID caseID, ArrayList<String> userID) {
        this.caseDao.saveCaseUserRelation(caseID, userID);
    }

    @Override
    public void insertNewPatient(long cpr, String firstName, String lastName) {
        this.cprDao.create(new String[]{Long.toString(cpr), firstName, lastName});
    }

    @Override
    public ArrayList<String[]> getCasesByDepartment(long departmentID) {
        return this.caseDao.getCasesByDepartment(departmentID);
    }
    
    //==========================================================================
    // Department methods
    //==========================================================================
    @Override
    public ArrayList<String> getDepartments() {
        return this.departmentDao.getAll();
    }

    @Override
    public String getDepartmentNameById(int departmentId) {
        return this.departmentDao.get(Integer.toString(departmentId))[1];
    }
    
    public String[] getDepartment(int departmentId) {
        return this.departmentDao.get(Integer.toString(departmentId));
    }

    //==========================================================================
    // User methods
    //==========================================================================
    @Override
    public ArrayList<Long> getUserDepartments(String userID) {
        return userDao.getUserDepartments(userID);
    }

    @Override
    public void createUser(String userName, String firstName, String lastName, String password, int typeid, int departmentid) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.userDao.createUser(userName, firstName, lastName, password, typeid, departmentid);
    }

    @Override
    public String[] getUser(String username, String password) {
        return this.userDao.get(new String[]{username, password});
    }

    @Override
    public boolean validateUserID(String userID) {
        return this.userDao.validateUserID(userID);
    }
    
    @Override
    public boolean validateUserPassword(long userID, String password) {
        return this.userDao.validateUserPassword(userID, password);
    }

    @Override
    public String[] getUserTypes() {
        return this.userDao.getUserTypes();
    }

    @Override
    public ArrayList<String[]> getAllUsers(ArrayList<Long> departments) {
        Long[] array = departments.toArray(new Long[departments.size()]);
        String[] data = new String[departments.size()];
        for (int i = 0; i < departments.size(); i++) {
            data[i] = Long.toString(array[i]);
        }
        return (ArrayList<String[]>) this.userDao.getAll(data);
    }
    
    @Override
    public boolean changePassword(String newPassword, String oldPassword, String username) {
        if (getUser(username, oldPassword) != null) {
            return userDao.changePassword(newPassword, username);
        } else {
            return false;
        }
    }

    @Override
    public void updateUserInfo(long userID, int role, boolean inactive) {
        this.userDao.updateInfo(userID, role, inactive);
    }

}
