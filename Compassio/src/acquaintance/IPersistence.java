package acquaintance;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Peterzxcvbnm
 */
public interface IPersistence {

    public void saveCase(String firstName, String lastName, UUID caseID, long cprNumber,
            String type, String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry);

    public ArrayList<String[]> getCasesByUserID(String userID);

    public ArrayList<String[]> getCasesByDepartment(long departmentID);

    public ArrayList<String> retrieveCaseTypeNames();

    public String[] getUser(String username, String password);

    public String getUserType(String userID);

    public ArrayList<Long> getUserDepartments(String userID);
    
    public String getDepartmentNameById(int departmentId);
}
