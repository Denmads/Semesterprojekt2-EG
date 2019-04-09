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

    @Override
    public void injectPersistence(IPersistence PersistenceLayer) {
        persistence = PersistenceLayer;
    }

    public void createCase(int CPR, SocialWorker[] socialWorkers) {

    }

    @Override
    public ArrayList<Case> getCases() {
        ArrayList<Case> response = new ArrayList<>();
        if (user.isInstanceOf(SocialWorker)) {
            ArrayList<String[]> cases = persistence.getCasesByUserID(user.getID());
            String[] singleCase = cases.remove(cases.size() - 1);
            response.add(new Case(singleCase[0], singleCase[1], Integer.parseInt(singleCase[2]), Integer.parseInt(singleCase[3]),
                    singleCase[4], singleCase[5], singleCase[6], singleCase[7], Integer.parseInt(singleCase[8])), singleCase[9]);
        }
    }

}
