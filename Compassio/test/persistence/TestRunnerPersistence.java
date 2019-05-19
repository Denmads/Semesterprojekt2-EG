
package persistence;

import logic.LogicSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author bentw
 */
public class TestRunnerPersistence {
    
        public static void main(String[] args) {
      Result result = JUnitCore.runClasses(PersistenceSuite.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
    
    
}
