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
    User user;

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }

    @Override
    public boolean login(String username, String password) {
        String[] result = this.persistence.retrieveUser(username, password);
        if (result != null) {
            this.user = new User(result[0],result[1],result[2],result[3]);
            return true;
        } else { 
            return false;
        }
    }
    
    
}
