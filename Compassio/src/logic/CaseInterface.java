package logic;

import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author Morten Kargo Lyngesen
 */
public interface CaseInterface {

    /**
     * Returns the case id
     * @return id of the case
     */
    UUID getCaseID();

    /**
     * Returns cpr-number of the citizen.
     * @return cpr-number of the citizen.
     */
    long getCprNumber();

    /**
     * Returns the date the case was closed.
     * @return date case was closed
     */
    LocalDate getDateClosed();

    /**
     * Returns the date the case was created.
     * @return date case was created
     */
    LocalDate getDateCreated();

    /**
     * Returns the id of associated department.
     * @return id of associated department.
     */
    int getDepartmentID();

    /**
     * Returns the first name of the citizen associated with the case.
     * @return first name of citizen.
     */
    String getFirstName();

    /**
     * Returns the inquiry associated with the case.
     * @return inquiry message of case.
     */
    String getInquiry();

    /**
     * Returns the last name of the citizen associated with the case.
     * @return first name of citizen.
     */
    String getLastName();

    /**
     * Returns the main body of the case.
     * @return main body of case
     */
    String getMainBody();

    /**
     * Returns the case type.
     * @return case type.
     */
    String getType();

    /**
     * Sets the case ID of the case.
     * @param caseID the id of the case.
     */
    void setCaseID(UUID caseID);

    /**
     * Sets the date the case was closed. Returns <code>true</code> on success.
     * Returns <code>false</code> if closed date is before the creation date.
     * @param dateClosed
     * @return Returns <code>true</code> on success. <code>false</code> false on failure.
     */
    boolean setDateClosed(LocalDate dateClosed);

    /**
     *
     * @param departmentID
     */
    void setDepartmentID(int departmentID);

    /**
     * Sets the first name of the citizen associated with the case.
     * @param firstName the first name of the citizen.
     */
    void setFirstName(String firstName);

    /**
     * Sets the case inquiry message.
     * @param inquiry inquiry message to department.
     */
    void setInquiry(String inquiry);

    /**
     * Sets the main body text of the case.
     * @param mainBody main body text of case.
     */
    void setMainBody(String mainBody);

    /**
     * Sets case type.
     * @param type type of case.
     */
    void setType(String type);
    
}
