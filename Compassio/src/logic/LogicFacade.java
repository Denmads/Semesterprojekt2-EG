package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.sql.Date;
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

    public boolean createCase(String firstName, String lastName, long cprNumber,
            String type, String mainBody, java.util.Date dateCreated, java.util.Date dateClosed, int departmentID, String inquiry, ArrayList<String> socialWorkers) {
        UUID caseID = UUID.randomUUID();
        Case newCase = new Case(firstName, lastName, caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
        newCase.addPatientToDatabase();
        boolean caseSaved = newCase.saveCase();
        socialWorkers.add(user.getUserID());

        persistence.saveCaseUserRelation(caseID, socialWorkers);
        return caseSaved;
    }

    @Override
    public ArrayList<Case> getCases() {
        ArrayList<Case> response = new ArrayList<>();
        if (this.user.getUserType() == UserType.CASEWORKER) {
            ArrayList<String[]> cases = persistence.getCasesByUserID(user.getUserID());
            while (cases.size() > 0) {
                String[] singleCase = cases.remove(cases.size() - 1);
                if (singleCase[7] != null) {
                    response.add(new Case(singleCase[0], singleCase[1], UUID.fromString(singleCase[2]), Long.parseLong(singleCase[3]),
                            singleCase[4], singleCase[5], Date.valueOf(singleCase[6]), Date.valueOf(singleCase[7]), Integer.parseInt(singleCase[8]), singleCase[9]));
                } else {
                    response.add(new Case(singleCase[0], singleCase[1], UUID.fromString(singleCase[2]), Long.parseLong(singleCase[3]),
                            singleCase[4], singleCase[5], Date.valueOf(singleCase[6]), null, Integer.parseInt(singleCase[8]), singleCase[9]));
                }
            }
        } else if (this.user.getUserType() == UserType.SOCIALWORKER) {
            ArrayList<Long> departments = this.user.getDepartments();
            ArrayList<String[]> cases = new ArrayList<>();
            departments.forEach(d -> {
                cases.addAll(persistence.getCasesByDepartment(d));
            });
            while (cases.size() > 0) {
                String[] singleCase = cases.remove(cases.size() - 1);
                response.add(new Case(singleCase[0], singleCase[1], UUID.fromString(singleCase[2]), Long.parseLong(singleCase[3]),
                        singleCase[4], singleCase[5], Date.valueOf(singleCase[6]), Date.valueOf(singleCase[7]), Integer.parseInt(singleCase[8]), singleCase[9]));
            }
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
        UserType userType;
        switch (result[4]) {
            case "socialworker":
                userType = UserType.SOCIALWORKER;
                break;
            case "caseworker":
                userType = UserType.CASEWORKER;
                break;
            default:
                userType = UserType.USER;
                break;
        }
        if (result != null) {
            this.user = new User(result[0], result[1], result[2],
                    result[3], LogicFacade.persistence.getUserDepartments(result[0]), userType);
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

    public String getUserID() {
        return user.getUserID();
    }

    /**
     * @return user first and last name seperated by a space char
     */
    @Override
    public String getUserName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    /**
     * @return users given type in all uppercase
     */
    @Override
    public String getUserType() {
        return this.user.getUserType().toString();
    }

    @Override
    public String getDepartmentNameById(int departmentId) {
        return persistence.getDepartmentNameById(departmentId);
    }
}
