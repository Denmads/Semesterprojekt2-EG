/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.util.ArrayList;

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
    
    public void createCase(int CPR, SocialWorker[] socialWorkers){
        
    }

    @Override
    public ArrayList<String> retrieveCaseTypes() {
        return persistence.retrieveCaseTypeNames();
    }
    
    public boolean login(String username, String password) {
        String[] result = this.persistence.getUser(username, password);

        if (result != null) {
            String userType = this.persistence.getUserType(result[0]);
            switch (userType) {
                case "user":
                    this.user = new User(result[0], result[1], result[2], result[3]);
                    break;
                case "caseworker":
                    this.user = new CaseWorker(result[0], result[1], result[2], 
                            result[3], this.persistence.getUserDepartments(result[0]));
                    break;
                case "socialworker":
                    this.user = new SocialWorker(result[0], result[1], result[2], 
                            result[3], this.persistence.getUserDepartments(result[0]));
                    break;
                default:
                    break;
            }
            return true;
        } else {
            return false;
        }
    }
}
