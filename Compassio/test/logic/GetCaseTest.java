
package logic;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class GetCaseTest {

 
    
    LogicFacade instance = new LogicFacade();

    
    
    @Before
    public void before(){
        instance.login("case", "password");
    }
    
     /**
     * Test of getCases method, of class LogicFacade.
     */
    @Test
    public void testGetCases() {
        System.out.println("getCases");
        ArrayList<Case> result = instance.getCases();
        ArrayList<Case> expResult = null;
        assertEquals(expResult, result);
        
    }
    
}
