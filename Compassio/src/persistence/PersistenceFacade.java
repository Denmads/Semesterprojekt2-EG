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
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
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

    public PersistenceFacade() {
        //Configure connection pool
        connectionPool = new BasicDataSource();
        connectionPool.setUsername(this.username);
        connectionPool.setPassword(this.password);
        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(this.dbIP);
        connectionPool.setInitialSize(10);

        userDao = new UserDAO(this.connectionPool);
        caseDao = new CaseDAO(this.connectionPool);
    }

    //==========================================================================
    // Case methods
    //==========================================================================
    @Override
    public ArrayList<String> retrieveCaseTypeNames() {
        return this.caseDao.retrieveCaseTypeNames();
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
        this.caseDao.insertNewPatient(cpr, firstName, lastName);
    }

    @Override
    public ArrayList<String[]> getCasesByDepartment(long departmentID) {
        return this.caseDao.getCasesByDepartment(departmentID);
    }

    @Override
    public ArrayList<String> getDepartments() {
        return this.caseDao.getDepartments();
    }

    @Override
    public String getDepartmentNameById(int departmentId) {
        return this.caseDao.getDepartmentNameById(departmentId);
    }

    //==========================================================================
    // User methods
    //==========================================================================
    @Override
    public ArrayList<Long> getUserDepartments(String userID) {
        return userDao.getUserDepartments(userID);
    }

    @Override
    public void createUser(String userName, String firstName, String lastName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.userDao.createUser(userName, firstName, lastName, password);
    }

    @Override
    public String[] getUser(String username, String password) {
        return this.userDao.getUser(username, password);
    }

    @Override
    public boolean validateUserID(String userID) {
        return this.userDao.validateUserID(userID);
    }
}
