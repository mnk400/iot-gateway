/**
 * 
 */
package neu.manikkumar.connecteddevices.labs;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.manikkumar.connecteddevices.labs.module01.SystemCpuUtilTask;
import neu.manikkumar.connecteddevices.labs.module01.SystemMemUtilTask;
import neu.manikkumar.connecteddevices.labs.module01.SystemPerformanceAdapter;

/**
 * Test class for all requisite Module01 functionality.
 * 
 * Instructions:
 * 1) Rename 'testSomething()' method such that 'Something' is specific to your needs; add others as needed, beginning each method with 'test...()'.
 * 2) Add the '@Test' annotation to each new 'test...()' method you add.
 * 3) Import the relevant modules and classes to support your tests.
 * 4) Run this class as unit test app.
 * 5) Include a screen shot of the report when you submit your assignment.
 * 
 * Please note: While some example test cases may be provided, you must write your own for the class.
 */
public class Module01Test{
	// setup methods
	
	SystemMemUtilTask mem_test;
	SystemCpuUtilTask cpu_test;
	SystemPerformanceAdapter adapter_test;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception{
		this.mem_test 		= new SystemMemUtilTask();
		this.cpu_test 		= new SystemCpuUtilTask();
		this.adapter_test 	= new SystemPerformanceAdapter(1,1);

	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception{
	}
	
	// test methods
	
	@Test
	public void testSystemCpuUtilTask(){
		float cpuPer;
		cpuPer = cpu_test.retcpu();

		assertTrue("CPU load less than 0", cpuPer>=0);
		assertTrue("CPU load greater than 100", cpuPer<100);	
	}
	
	@Test
	public void testSystemMemUtilTask(){
		float memPer;
		memPer = cpu_test.retcpu();
		
		assertTrue("Memory usage less than 0", memPer>=0);	
		assertTrue("Memory usage greater than 100", memPer<100);
	}
	
	@Test
	public void testSystemPerformanceAdapter() {
		try {
			adapter_test.run();
			assertTrue("True when expected False", adapter_test.checkSuccess() == false);
			adapter_test.enableSystemPerformanceAdapter = true;
			adapter_test.run();
			assertTrue("False when expected True", adapter_test.checkSuccess() == true);
		} catch (Exception e) {
			System.out.println("Adapter threw an exception" + e);
		}
	}
	
	
}
