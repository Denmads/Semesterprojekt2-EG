
package logic;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class GetCaseTest {
    
     /**
     * Test of getCases method, of class LogicFacade.
     */
    @Test
    public void testGetCases() {
        System.out.println("getCases");
        LogicFacade instance = new LogicFacade();
        ArrayList<Case> expResult = null;
        ArrayList<Case> result = instance.getCases();
        assertEquals(expResult, result);
        
    }
    
}
