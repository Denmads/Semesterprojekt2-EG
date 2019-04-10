/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Peterzxcvbnm
 */
public class LogicFacade implements ILogic {

    private static IPersistence persistence;
    private User user;

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }

    @Override
    public ArrayList<Case> getCases() {
        ArrayList<Case> response = new ArrayList<>();
        if (user.isInstanceOf(CaseWorker)) {
            ArrayList<String[]> cases = persistence.getCasesByUserID(user.getID());
            String[] singleCase = cases.remove(cases.size() - 1);
            response.add(new Case(singleCase[0], singleCase[1], Integer.parseInt(singleCase[2]), Integer.parseInt(singleCase[3]),
                    singleCase[4], singleCase[5], singleCase[6], singleCase[7], Integer.parseInt(singleCase[8])), singleCase[9]);
        } else if (user.isInstanceOf(SocialWorker)) {
            ArrayList<String[]> cases = persistence.getCasesByDepartment(user.getDepartmentID());
            String[] singleCase = cases.remove(cases.size() - 1);
            response.add(new Case(singleCase[0], singleCase[1], Integer.parseInt(singleCase[2]), Integer.parseInt(singleCase[3]),
                    singleCase[4], singleCase[5], singleCase[6], singleCase[7], Integer.parseInt(singleCase[8])), singleCase[9]);

        }
    }

    @Override
    public void createCase(long CPR, int[] socialWorkers) {
        UUID caseID = UUID.randomUUID();
        Case newCase = new Case(CPR, caseID);
        newCase.saveCase();

    }

    public static IPersistence getPersistence() {
        return persistence;
    }

    @Override
    public ArrayList<String> retrieveCaseTypes() {
        return persistence.retrieveCaseTypeNames();
    }

    @Override
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
