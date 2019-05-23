package persistence;

import acquaintance.IPersistence;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import persistence.dataaccessobjects.*;

/**
 * Provides a facade to interact with the database through Data Access Objects.
 *
 * @author Peterzxcvbnm
 * @author Morten Kargo Lyngesen
 */
public class PersistenceFacade implements IPersistence {

    //Data Access Objects
    private final UserDAO userDao;
    private final CaseDAO caseDao;
    private final DepartmentDAO departmentDao;
    private final CaseTypeRelationDAO caseTypeRelationDao;
    private final CprDAO cprDao;
    private final EmployeesOfDepartmentDAO employeesOfDepartmentDAO;
    private final UserTypeRelationDAO userTypeRelationDao;

    public PersistenceFacade() {
        //Initialize DAO's
        userDao = new UserDAO();
        caseDao = CaseDAO.getInstance();
        departmentDao = new DepartmentDAO();
        caseTypeRelationDao = new CaseTypeRelationDAO();
        cprDao = new CprDAO();
        employeesOfDepartmentDAO = new EmployeesOfDepartmentDAO();
        userTypeRelationDao = UserTypeRelationDAO.getInstance();
    }

    //==========================================================================
    // Case methods
    //==========================================================================
    @Override
    public ArrayList<String> retrieveCaseTypeNames() {
        List<String[]> list = this.caseTypeRelationDao.getAll();
        String[][] array = list.toArray(new String[][]{});
        ArrayList<String> arrayList = new ArrayList<>();
        for (String[] arr : array) {
            arrayList.add(arr[0]);
        }
        return arrayList;
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
        StringBuilder builder = new StringBuilder();
        userID.stream().map((String userID1) -> {
            builder.append(userID1);
            return userID1;
        }).forEachOrdered((String _item) -> {
            builder.append(" ");
        });
        this.caseTypeRelationDao.create("-id " + caseID.toString(), "-users " + builder.toString());
    }

    @Override
    public void insertNewPatient(long cpr, String firstName, String lastName) {
        this.cprDao.create("-cpr " + Long.toString(cpr), "-firstname " + firstName, "-lastname " + lastName);
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
        String[] dataset = this.employeesOfDepartmentDAO.get("-id " + userID);
        ArrayList<Long> longList = new ArrayList<>();
        if (dataset != null) {
            for (String dataset1 : dataset) {
                longList.add(Long.valueOf(dataset1));
            }
        }
        return longList;
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
        ArrayList<String[]> dataset = (ArrayList<String[]>) this.userTypeRelationDao.getAll();
        String[] types = new String[dataset.size()];
        for (int i = 0; i < dataset.size(); i++) {
            types[i] = dataset.get(i)[0];
        }
        return types;
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
