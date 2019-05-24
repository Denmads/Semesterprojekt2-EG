
package logic;

import acquaintance.ILogic;
import acquaintance.IPersistence;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import persistence.PersistenceFacade;

/**
 *
 * @author bentw
 */
public class LoginTest {

    ILogic logic = new LogicFacade();
    IPersistence persistence = new PersistenceFacade();
        
    @Before
    public void before(){
    logic.injectPersistence(persistence);
    }
    
     /**
     * Test of login method, of class LogicFacade.
     */
    
    @Test
    public void testWrongPassword(){

        System.out.println("Testing login in with wrong password");
        String username = "admin";
        String password = "";
        boolean expectedResult = false;
        boolean failedResult = logic.login(username, password);
        assertEquals(expectedResult, failedResult);
    }
    
    @Test
    public void testCorrectLogin() {
        System.out.println("Testing login in with correct login");
        String username = "casetest";
        String password = "password";
        boolean expResult = true;
        boolean result = logic.login(username, password);
        assertEquals(expResult, result);

    }
    

    
    
}
