package logic;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Frederik
 */
public class Case {

    private String firstName;

    private String lastName;

    private UUID caseID;

    private long cprNumber;

    private String type;

    private String mainBody;

    private Date dateCreated;

    private Date dateClosed;

    private int departmentID;

    private String inquiry;

    public Case(long cprNumber, UUID caseID) {
        this.cprNumber = cprNumber;
        this.dateCreated = new Date();
        this.caseID = caseID;
    }

    public Case(long cprNumber, UUID caseID, Date dateCreated) {
        this.cprNumber = cprNumber;
        this.dateCreated = dateCreated;
        this.caseID = caseID;
    }

    public Case(String firstName, String lastName, UUID caseID, long cprNumber, String type, String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry) {
        this(cprNumber, caseID, dateCreated);
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.mainBody = mainBody;
        this.dateClosed = dateClosed;
        this.departmentID = departmentID;
        this.inquiry = inquiry;
    }

    public UUID getCaseID() {
        return caseID;
    }

    public long getCprNumber() {
        return cprNumber;
    }

    public String getType() {
        return type;
    }

    public String getMainBody() {
        return mainBody;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public String getInquiry() {
        return inquiry;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCaseID(UUID caseID) {
        this.caseID = caseID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMainBody(String mainBody) {
        this.mainBody = mainBody;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public boolean saveCase() {
        return LogicFacade.getPersistence().saveCase(caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
    }
    
    public void addPatientToDatabase(){
        LogicFacade.getPersistence().insertNewPatient(cprNumber, firstName, lastName);

    }
}
