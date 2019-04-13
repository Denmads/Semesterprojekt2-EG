/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acquaintance;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Peterzxcvbnm
 */
public interface IPersistence {
    
    public void saveCase(UUID caseID, long cprNumber, long typeID, String mainBody,
            Date dateCreated, Date dateClosed, int departmentID, String inquiry);
    
    public void saveCaseUserRelation(UUID caseID, int[] userID);
    
    public void insertNewPatient(long cpr, String firstName, String lastName);
}
