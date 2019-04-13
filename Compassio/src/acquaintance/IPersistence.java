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
    public ArrayList<String> retrieveCaseTypeNames ();
    public String[] getUser(String username, String password);
    public String getUserType(String userID);
    public ArrayList<Long> getUserDepartments(String userID);
    
    public void saveCase(UUID caseID, long cprNumber, long typeID, String mainBody,
            Date dateCreated, Date dateClosed, int departmentID, String inquiry);
    
    public void saveCaseUserRelation(UUID caseID, int[] userID);
    
    public void insertNewPatient(long cpr, String firstName, String lastName);
}
