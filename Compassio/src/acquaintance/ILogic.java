package acquaintance;

import java.util.ArrayList;
import java.util.Date;
import logic.Case;

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

    public String getUserID();

    public String getUserName();

    public String getUserType();

    public String getDepartmentNameById(int departmentId);

    /**
     * Method to change a users password
     *
     * @param newPassword The password the user wants to change to
     * @param oldPassword The users old password
     * @return True if the password was change and false if the old password is wrong
     */
    public Boolean changePassword(String newPassword, String oldPassword);
}
