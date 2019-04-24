package acquaintance;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Peterzxcvbnm
 */
public interface IPersistence {

    /**
     * Get the cases connected to the userID
     *
     * @param userID The userID for which all the cases are connected to
     * @return An ArrayList with a String array containing all the attributes of
     * the case
     */
    public ArrayList<String[]> getCasesByUserID(String userID);

    /**
     * Get the cases connected to the departmentID
     *
     * @param departmentID The userID for which all the cases are connected to
     * @return An ArrayList with a String array containing all the attributes of
     * the case
     */
    public ArrayList<String[]> getCasesByDepartment(long departmentID);

    /**
     * Returns all case type names.
     *
     * @return case type names.
     */
    public ArrayList<String> retrieveCaseTypeNames();

    /**
     *
     * @param username username of the user to get
     * @param password password of the specified user
     * @return the user
     */
    public String[] getUser(String username, String password);

    /**
     * Creates a user with a hashed password
     *
     * @param userName users username. Used for login.
     * @param firstName users first name
     * @param lastName users last name
     * @param password password to hash and add to database
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public void createUser(String userName, String firstName, String lastName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    /**
     * Returns a list of departments associated with a user.
     *
     * @param userID user to find associated departments.
     * @return list of departments. Will return <code>null</code> if none are
     * found.
     */
    public ArrayList<Long> getUserDepartments(String userID);

    /**
     *
     * @param caseID
     * @param cprNumber
     * @param type
     * @param mainBody notes entered by the user
     * @param dateCreated
     * @param dateClosed
     * @param departmentID
     * @param inquiry
     * @return
     */
    public boolean saveCase(UUID caseID, long cprNumber, String type, String mainBody,
            Date dateCreated, Date dateClosed, int departmentID, String inquiry);

    /**
     *
     * @param caseID 
     * @param userID 
     */
    public void saveCaseUserRelation(UUID caseID, ArrayList<String> userID);

    /**
     * Inserts a new patient into the CPR register.
     * 
     * @param cpr the patients CPR number
     * @param firstName the patients first name
     * @param lastName the patients last name
     */
    public void insertNewPatient(long cpr, String firstName, String lastName);

    /**
     * Returns a list of all departments.
     * 
     * @return a list of all departments.
     */
    public ArrayList<String> getDepartments();

    /**
     * Returns if the specified user is valid  
     * 
     * @param userID The user to check for
     * @return returns <code>true</code> if user exists or <code>false</code> if
     * they don't
     */
    public boolean validateUserID(String userID);

    /**
     * Returns the name of name of the department
     *
     * @param departmentId the ID of the department to return the name of.
     * @return name of the department. Will return <code>null</code> if
     * department doesn't exist
     */
    public String getDepartmentNameById(int departmentId);
}
