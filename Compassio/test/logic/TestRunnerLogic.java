
package logic;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author bentw
 */
public class TestRunnerLogic {
    
    public static void main(String[] args) {
      Result result = JUnitCore.runClasses(LogicSuite.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
    
}
