package acquaintance;

import java.util.ArrayList;
import java.util.Date;
import logic.Case;
import logic.UserInfo;

/**
 *
 * @author Peter Brændgaard
 */
public interface ILogic {

    public void injectPersistence(IPersistence PersistenceLayer);
    
    public ArrayList<Case> getCases();

    public void createCase(long CPR, int[] socialWorkers);

    public ArrayList<String> retrieveCaseTypes();

    public boolean login(String username, String password);

    public boolean createCase(String firstName, String lastName, long cprNumber, String type, String mainBody,
            Date dateCreated, Date dateClosed, int departmentID, String inquiry, ArrayList<String> socialWorkers);

    public ArrayList<String> getDepartmentInfo();

    public boolean checkUserID(String userID);
    
    /**
     * Used for validating a entered password
     * @param password The password to validate
     * @return <code>True</code> if the password is correct and <code>false</code> if it is wrong
     */
    public boolean checkUserPassword(String password);

    public String getUserID();

    public String getUserName();

    public String getUserType();
    
    public String[] getUserTypes ();
    
    public ArrayList<UserInfo> getAllUsers ();

    public String getDepartmentNameById(int departmentId);

    /**
     * Method to change a users password
     *
     * @param newPassword The password the user wants to change to
     * @param oldPassword The users old password
     * @return <code>True</code> if the password was change and <code>false</code> if the old password is wrong
     */
    public Boolean changePassword(String newPassword, String oldPassword);
    
    /**
     * Mathod to update the role and inactive state of a user
     * @param userID The id of the user to update
     * @param newRole The new role of the user
     * @param newInactiveState the state of the user
     */
    public void updateUserState (long userID, String newRole, boolean newInactiveState);
}
