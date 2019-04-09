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

    public void createCase(long cpr, int[] socialWorkers) {
        UUID caseID = UUID.randomUUID();
        Case newCase = new Case(cpr, caseID, new Date());
        newCase.saveCase();
        
        for (int i = 0 ; i < socialWorkers.length ; i++){
            persistence.saveCaseUserRelation(cpr, socialWorkers[i]);
        }

    }
    

    public static IPersistence getPersistence() {
        return persistence;
    }

}
