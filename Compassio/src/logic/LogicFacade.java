/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Peterzxcvbnm
 */
public class LogicFacade implements ILogic {

    private static IPersistence persistence;

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }

    public void createCase(String firstName, String lastName, long cprNumber,  
            String type, String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry, int[] socialWorkers) {
        UUID caseID = UUID.randomUUID();
        Case newCase = new Case(firstName, lastName, caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
        newCase.saveCase();
        
        for (int i = 0 ; i < socialWorkers.length ; i++){
            persistence.saveCaseUserRelation(caseID, socialWorkers[i]);
        }

    }
    

    public static IPersistence getPersistence() {
        return persistence;
    }

}
