/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Peterzxcvbnm
 */
public class LogicFacade implements ILogic {


    User user;

    private static IPersistence persistence;

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }

    public boolean createCase(String firstName, String lastName, long cprNumber,
            String type, String mainBody, Date dateCreated, Date dateClosed, int departmentID, String inquiry, ArrayList<String> socialWorkers) {
        UUID caseID = UUID.randomUUID();
        Case newCase = new Case(firstName, lastName, caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
        newCase.addPatientToDatabase();
        boolean caseSaved = newCase.saveCase();
        socialWorkers.add(user.getUserID());
        persistence.saveCaseUserRelation(caseID, socialWorkers);
        return caseSaved;
    }

    public static IPersistence getPersistence() {
        return persistence;
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

    @Override
    public ArrayList<String> getDepartmentInfo() {
        return persistence.getDepartments();
    }

    @Override
    public boolean checkUserID(String userID) {
        return persistence.validateUserID(userID);
    }

}
