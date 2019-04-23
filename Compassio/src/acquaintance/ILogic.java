/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public ArrayList<String> retrieveCaseTypes ();
     public boolean login(String username, String password);
    public boolean createCase(String firstName, String lastName, long cprNumber,  String type, String mainBody, 
            Date dateCreated, Date dateClosed, int departmentID, String inquiry, ArrayList<String> socialWorkers);
    public ArrayList<String> getDepartmentInfo();
    
    public boolean checkUserID(String userID);
    
    public String getUserID();
}
