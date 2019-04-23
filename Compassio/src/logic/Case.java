/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.UUID;
import java.util.Date;

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

    public Case(String firstName, String lastName, UUID caseID, long cprNumber,
            String type, String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.caseID = caseID;
        this.cprNumber = cprNumber;
        this.type = type;
        this.mainBody = mainBody;
        this.dateCreated = dateCreated;
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

    public void saveCase() {
        LogicFacade.getPersistence().saveCase(firstName, lastName, caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
    }
}
