package acquaintance;

import java.time.LocalDate;
import java.util.ArrayList;
import logic.Case;

/**
 *
 * @author Peter Brændgaard
 */
public interface ILogic {

    public void injectPersistence(IPersistence PersistenceLayer);

    public ArrayList<Case> getCases();

    public void createCase(long CPR, int[] socialWorkers);

    public ArrayList<String> retrieveCaseTypes ();
     public boolean login(String username, String password);
    public boolean createCase(String firstName, String lastName, long cprNumber,  String type, String mainBody, 
            LocalDate dateCreated, LocalDate dateClosed, int departmentID, String inquiry, ArrayList<String> socialWorkers);
    public ArrayList<String> getDepartmentInfo();
    
    public boolean checkUserID(String userID);
    
    public String getUserID();

    public String getUserName();
    public String getUserType();
    public String getDepartmentNameById(int departmentId);
}
