/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;
import logic.CaseWorker;

/**
 *
 * @author Peterzxcvbnm
 */
public class LogicFacade implements ILogic {

    private static IPersistence persistence;
    private User user;
    private String userType;

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }

    @Override
    public ArrayList<Case> getCases() {
        ArrayList<Case> response = new ArrayList<>();
        if (userType.equals("caseworker")) {
            ArrayList<String[]> cases = persistence.getCasesByUserID(user.getUserID());
            String[] singleCase = cases.remove(cases.size() - 1);
            response.add(new Case(singleCase[0], singleCase[1], UUID.fromString(singleCase[2]), Long.parseLong(singleCase[3]),
                    singleCase[4], singleCase[5], Date.valueOf(singleCase[6]), Date.valueOf(singleCase[7]), Integer.parseInt(singleCase[8]), singleCase[9]));
        } else if (userType.equals("socialworker")) {
            SocialWorker currentUser = (SocialWorker) user;
            ArrayList<Long> departments = currentUser.getDepartments();
            ArrayList<String[]> cases = new ArrayList<>();
            departments.forEach(d -> {
                cases.addAll(persistence.getCasesByDepartment(d));
            });
            String[] singleCase = cases.remove(cases.size() - 1);
            response.add(new Case(singleCase[0], singleCase[1], UUID.fromString(singleCase[2]), Long.parseLong(singleCase[3]),
                    singleCase[4], singleCase[5], Date.valueOf(singleCase[6]), Date.valueOf(singleCase[7]), Integer.parseInt(singleCase[8]), singleCase[9]));
        }
        return response;
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
                    this.userType = userType;
                    break;
                case "caseworker":
                    this.user = new CaseWorker(result[0], result[1], result[2],
                            result[3], this.persistence.getUserDepartments(result[0]));
                    this.userType = userType;
                    break;
                case "socialworker":
                    this.user = new SocialWorker(result[0], result[1], result[2],
                            result[3], this.persistence.getUserDepartments(result[0]));
                    this.userType = userType;
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
