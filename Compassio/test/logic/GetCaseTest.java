
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

    private Object result;
    
    
    @Before
    public void before(){
               LogicFacade instance = new LogicFacade();
        instance.login("case", "password");
         ArrayList<Case> result = instance.getCases();
    }
    
     /**
     * Test of getCases method, of class LogicFacade.
     */
    @Test
    public void testGetCases() {
        System.out.println("getCases");
        ArrayList<Case> expResult = null;
        assertEquals(expResult, result);
        
    }
    
}
