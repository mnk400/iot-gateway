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


public class Module01Test{
	// setup methods
	
	SystemMemUtilTask mem_test;
	SystemCpuUtilTask cpu_test;
	SystemPerformanceAdapter adapter_test;
	

	@Before
	public void setUp() throws Exception{
		this.mem_test 		= new SystemMemUtilTask();
		this.cpu_test 		= new SystemCpuUtilTask();
		this.adapter_test 	= new SystemPerformanceAdapter(1,1);

	}
	
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
