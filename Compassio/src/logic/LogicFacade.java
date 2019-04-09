/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;

/**
 *
 * @author Peterzxcvbnm
 */
public class LogicFacade implements ILogic {

    private IPersistence persistence;

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }
    
    public void createCase(int CPR, SocialWorker[] socialWorkers){
        
    }

}
