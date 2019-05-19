
package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class LoginTest {

    private User admin;
    
     /**
     * Test of login method, of class LogicFacade.
     */
    @Test
    public void testCorrectLogin() {
        System.out.println("Correct login");
        String username = "case";
        String password = "password";
        LogicFacade instance = new LogicFacade();
        boolean expResult = true;
        boolean result = instance.login(username, password);
        assertEquals(expResult, result);

    }
    
    @Test
    public void testWrongPassword(){
        System.out.println("Wrong password");
        User user = admin;
        
        String username = "admin";
        String password = "";
        LogicFacade failedInstance = new LogicFacade();
        boolean expectedResult = false;
        boolean failedResult = failedInstance.login(username, password);
        assertEquals(expectedResult, failedResult);
    }
    
    
}
