/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Frederik
 */
public class Case {

    private String firstName;

    private String lastName;

    private int caseID;

    private int cprNumber;

    private String type;

    private String mainBody;

    private Calendar dateCreated;

    private Calendar dateClosed;

    private int departmentID;

    private String inquiry;

    public Case(String firstName, String lastName, int caseID, int cprNumber, String type, String mainBody, Calendar dateCreated, Calendar dateClosed, int departmentID, String inquiry) {
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

    public int getCaseID() {
        return caseID;
    }

    public int getCprNumber() {
        return cprNumber;
    }

    public String getType() {
        return type;
    }

    public String getMainBody() {
        return mainBody;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public Calendar getDateClosed() {
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

}
