/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acquaintance;

import java.util.Date;

/**
 *
 * @author Peter Brændgaard
 */
public interface ILogic {

    public void injectPersistence(IPersistence PersistenceLayer);
    
    public void createCase(String firstName, String lastName, long cprNumber,  long typeID, String mainBody, 
            Date dateCreated, Date dateClosed, int departmentID, String inquiry, int[] socialWorkers);

}
