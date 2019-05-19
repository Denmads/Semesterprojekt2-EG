
package persistence;

import logic.LogicFacade;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class ChangePasswordTest {

    LogicFacade instance = new LogicFacade();
    
    @Before
    public void before(){
        instance.login("passwordtest", "password");

    }

    /**
     * Test of changePassword method, of class PersistenceFacade.
     */

    @Test
    public void testChangePasswordFail() {
        System.out.println("changePasswordFail");
        String newPassword = "change";
        String oldPassword = "notthis";
        boolean expResult = false;
        boolean result = instance.changePassword(newPassword, oldPassword);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testChangePassword() {
        System.out.println("change Password test");
        String newPassword = "change";
        String oldPassword = "password";
        boolean expResult = true;
        boolean result = instance.changePassword(newPassword, oldPassword);
        assertEquals(expResult, result);

    }

    
}
