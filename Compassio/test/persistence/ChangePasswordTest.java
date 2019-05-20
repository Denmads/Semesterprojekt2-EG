
package persistence;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import logic.LogicFacade;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class ChangePasswordTest {

    ILogic logic = new LogicFacade();
    IPersistence persistence = new PersistenceFacade();
    
    @Before
    public void before(){
        logic.injectPersistence(persistence);
        logic.login("passwordtest", "change");

    }
    
    @After
    public void after(){
        logic.injectPersistence(persistence);
        logic.login("passwordtest", "password");
        logic.changePassword("change", "password");
    }

    /**
     * Test of changePassword method, of class PersistenceFacade.
     */

    @Test
    public void testChangePasswordFail() {
        System.out.println("Testing when changing password fails");
        String newPassword = "changes";
        String oldPassword = "notthis";
        boolean expResult = false;
        boolean result = logic.changePassword(newPassword, oldPassword);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testChangePassword() {
        System.out.println("Testing succesful password change");
        String newPassword = "password";
        String oldPassword = "change";
        boolean expResult = true;
        boolean result = logic.changePassword(newPassword, oldPassword);
        assertEquals(expResult, result);

    }

    
}
