package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.util.ArrayList;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import persistence.PersistenceFacade;

/**
 *
 * @author bentw
 */
public class GetCasesTest {

    ILogic logic = new LogicFacade();
    IPersistence persistence = new PersistenceFacade();

    @Before
    public void before() {
        logic.injectPersistence(persistence);
        
    }

    /**
     * Test of getCases method, of class LogicFacade.
     */
    @Test
    public void testGetCases() {
        logic.login("casetest", "password");
        System.out.println("Tests that the case is the same as the control.");
        ArrayList<Case> testedCases = logic.getCases();
        Case tested = testedCases.get(0);
        logic.logout();
        logic.login("casetest", "password");
        ArrayList<Case> cases = logic.getCases();
        Case testControl = cases.get(0);
        assertSame(testControl.compareTo(tested), true);
    }

    @Test
    public void testGetCasesNotEqual() {
        logic.login("case", "password");
        System.out.println("Test that the cases of different users are not the same.");
        ArrayList<Case> result = logic.getCases();
        Case tested = result.get(0);
        logic.logout();
        logic.login("casetest", "password");
        ArrayList<Case> cases = logic.getCases();
        Case testControl = cases.get(0);
        assertSame(testControl.compareTo(tested), false);

    }

}
