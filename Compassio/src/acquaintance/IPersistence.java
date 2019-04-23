package acquaintance;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Peterzxcvbnm
 */
public interface IPersistence {

    public ArrayList<String[]> getCasesByUserID(String userID);

    public ArrayList<String[]> getCasesByDepartment(long departmentID);

    public ArrayList<String> retrieveCaseTypeNames();

    public String[] getUser(String username, String password);

    public String getUserType(String userID);

    public ArrayList<Long> getUserDepartments(String userID);

    public boolean saveCase(UUID caseID, long cprNumber, String type, String mainBody,
            Date dateCreated, Date dateClosed, int departmentID, String inquiry);

    public void saveCaseUserRelation(UUID caseID, ArrayList<String> userID);

    public void insertNewPatient(long cpr, String firstName, String lastName);

    public ArrayList<String> getDepartments();

    public boolean validateUserID(String userID);
    
    public String getDepartmentNameById(int departmentId);
}
