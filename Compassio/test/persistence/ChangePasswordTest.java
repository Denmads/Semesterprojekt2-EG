
package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class ChangePasswordTest {
    
    

    /**
     * Test of changePassword method, of class PersistenceFacade.
     */
    @Test
    public void testChangePassword() {
        System.out.println("changePassword");
        String newPassword = "";
        String oldPassword = "";
        String username = "";
        PersistenceFacade instance = new PersistenceFacade();
        boolean expResult = true;
        boolean result = instance.changePassword(newPassword, oldPassword, username);
        assertEquals(expResult, result);

    }
    
    

    @Test
    public void testChangePasswordFail() {
        System.out.println("changePasswordFail");
        String newPassword = "";
        String oldPassword = "";
        String username = "";
        PersistenceFacade instance = new PersistenceFacade();
        boolean expResult = false;
        boolean result = instance.changePassword(newPassword, oldPassword, username);
        assertEquals(expResult, result);
    }

    
}
