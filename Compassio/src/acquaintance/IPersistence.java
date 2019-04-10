/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public ArrayList<String[]> getCasesByUserID(int userID);

    public ArrayList<String[]> getCasesByDepartment(int departmentID);

    public ArrayList<String> retrieveCaseTypeNames ();
}
