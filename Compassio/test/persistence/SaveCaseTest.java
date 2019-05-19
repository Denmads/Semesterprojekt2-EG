
package persistence;

import java.time.LocalDate;
import java.util.UUID;
import logic.LogicFacade;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author bentw
 */
public class SaveCaseTest {

    LogicFacade instance = new LogicFacade();
    
        @Before
    public void before(){
        instance.login("case", "password");

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
