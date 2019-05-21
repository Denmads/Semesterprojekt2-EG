package logic;

import java.time.LocalDate;
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

    private LocalDate dateCreated;

    private LocalDate dateClosed;

    private int departmentID;

    private String inquiry;

    public Case(long cprNumber) {
        this.cprNumber = cprNumber;
        this.dateCreated = LocalDate.now();
        this.caseID = UUID.randomUUID();
    }

    public Case(long cprNumber, UUID caseID, LocalDate dateCreated) {
        this.cprNumber = cprNumber;
        this.dateCreated = dateCreated;
        this.caseID = caseID;
    }

    public Case(String firstName, String lastName, UUID caseID, long cprNumber, String type, String mainBody, LocalDate	 dateCreated, LocalDate dateClosed, int departmentID, String inquiry) {
        this(cprNumber, caseID, dateCreated);
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.mainBody = mainBody;
        this.dateClosed = dateClosed;
        this.departmentID = departmentID;
        this.inquiry = inquiry;
    }
    
    public Case(String firstName, String lastName, long cprNumber, String type, String mainBody, LocalDate dateCreated, LocalDate dateClosed, int departmentID, String inquiry) {
        this(cprNumber, UUID.randomUUID(), dateCreated);
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.mainBody = mainBody;
        this.dateClosed = dateClosed;
        this.departmentID = departmentID;
        this.inquiry = inquiry;
    }
    
    public Case(String firstName, String lastName, long cprNumber, String type, String mainBody, LocalDate dateCreated, LocalDate dateClosed, int departmentID, String inquiry, UUID caseID) {
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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getDateClosed() {
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

    public void setDateClosed(LocalDate dateClosed) {
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

    public boolean compareTo(Case c) {
    boolean mainBodyEquals =(mainBody !=null ?mainBody.equals(c.mainBody) : mainBody ==c.mainBody );
    boolean dateClosedEquals =(dateClosed !=null ?dateClosed.equals(c.dateClosed) : dateClosed == c.dateClosed );
    boolean inquiryEquals =(inquiry !=null ?inquiry.equals(c.inquiry) : inquiry == c.inquiry );
    
    return firstName.equals(c.firstName)&&lastName.equals(c.lastName)&&caseID.equals(c.caseID)&&cprNumber ==c.cprNumber&&type.equals(c.type)&& mainBodyEquals &&dateCreated.equals(c.dateCreated)&&dateClosedEquals&&departmentID == c.departmentID &&inquiryEquals;
    }
}
