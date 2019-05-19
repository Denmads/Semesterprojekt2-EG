
package persistence;

import java.time.LocalDate;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class SaveCaseTest {

    private UUID beb46cb10f1542cba94295b116fa53f6;
    
    
    @Before
    public void before(){
        UUID testUUID = beb46cb10f1542cba94295b116fa53f6;
    }
    
     /**
     * Test of saveCase method, of class PersistenceFacade.
     */
//    @Test
//    public void testSaveCase(UUID testUUID) {
//        System.out.println("saveCase");
//        UUID caseID = testUUID;
//        long cprNumber = 1148903278;
//        String type = "2";
//        String mainBody = "Save test";
//        LocalDate dateCreated = null;
//        LocalDate dateClosed = null;
//        int departmentID = 2;
//        String inquiry = "Save a case";
//        PersistenceFacade instance = new PersistenceFacade();
//        boolean expResult = true;
//        boolean result = instance.saveCase(caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
//        assertEquals(expResult, result);
//
//    }

    @Test
    public void testSaveCase() {
        System.out.println("saveCase");
        UUID caseID = null;
        long cprNumber = 0L;
        String type = "";
        String mainBody = "";
        LocalDate dateCreated = null;
        LocalDate dateClosed = null;
        int departmentID = 0;
        String inquiry = "";
        PersistenceFacade instance = new PersistenceFacade();
        boolean expResult = false;
        boolean result = instance.saveCase(caseID, cprNumber, type, mainBody, dateCreated, dateClosed, departmentID, inquiry);
        assertEquals(expResult, result);
    }
    
}
