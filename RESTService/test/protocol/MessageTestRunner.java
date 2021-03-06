package protocol;

import org.junit.runner.Result;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

public class MessageTestRunner {
	
	public static void main(String[] args){

		System.out.println("Test message parsing");
		Result testResult = JUnitCore.runClasses(MessageTest.class);
	    for (Failure fail : testResult.getFailures()){
	        System.out.println(fail.toString());
	    }

	    System.out.println("Test Successful: " + testResult.wasSuccessful());
	}
	
    

}
