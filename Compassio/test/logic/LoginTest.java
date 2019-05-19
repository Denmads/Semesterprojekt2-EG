
package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class LoginTest {

    
    @Before
    public void before(){
        LogicFacade instance = new LogicFacade();
    }
    
    
    
     /**
     * Test of login method, of class LogicFacade.
     */
    @Test
    public void testWrongPassword(){
        System.out.println("Wrong password");
        String username = "admin";
        String password = "";
        boolean expectedResult = false;
        boolean failedResult = instance.login(username, password);
        assertEquals(expectedResult, failedResult);
    }
    
    @Test
    public void testCorrectLogin() {
        System.out.println("Correct login");
        String username = "casetest";
        String password = "password";
        boolean expResult = true;
        boolean result = instance.login(username, password);
        assertEquals(expResult, result);

    }
    

    
    
}
