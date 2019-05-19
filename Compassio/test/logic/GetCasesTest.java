package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        logic.login("case", "password");
        System.out.println("getCases");
        ArrayList<Case> result = logic.getCases();
        assertNotNull(result);
        logic.logout();
    }

    @Test
    public void testGetCasesNoCases() {
        logic.login("passwordtest", "change");
        System.out.println("When no cases are present");
        ArrayList<Case> result = logic.getCases();
        ArrayList<Case> expResult = null;
        assertEquals(expResult, result);

    }

}
