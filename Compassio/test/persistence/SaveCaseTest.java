
package persistence;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import logic.Case;
import logic.LogicFacade;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class SaveCaseTest {

    ILogic logic = new LogicFacade();
    IPersistence persistence = new PersistenceFacade();
    private Case unchanged;

    @Before
    public void before() {
        logic.injectPersistence(persistence);
        logic.login("case", "password");

        ArrayList<Case> cases = logic.getCases();
        Case unchanged = cases.get(0);
   
    }
    
     /**
     * Test of saveCase method, of class PersistenceFacade.
     */

    @Test
    public void testSaveCase() {
        logic.login("case", "password");

        ArrayList<Case> cases = logic.getCases();
        Case changed = cases.get(0);
        UUID testuuid = changed.getCaseID();
        long testcpr = changed.getCprNumber();
        String testType = changed.getType();
        String testMainBody = "Save this";
        LocalDate testcreated = changed.getDateCreated();
        LocalDate testClosed = changed.getDateClosed();
        int testdepartmentID = changed.getDepartmentID();
        String testInquiry = changed.getInquiry();


        persistence.saveCase(testuuid, testcpr, testType, testMainBody, testcreated, testClosed, testdepartmentID, testInquiry);
        
        System.out.println("Testing save Case");
        assertNotSame(unchanged, changed);
    }
    
}
