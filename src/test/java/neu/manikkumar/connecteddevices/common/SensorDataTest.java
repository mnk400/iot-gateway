/**
 * 
 */
package neu.manikkumar.connecteddevices.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for SensorData functionality.
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
public class SensorDataTest
{
	// setup methods
	SensorData SensorDataTests;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		SensorDataTests = new SensorData();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	// test methods
	
	@Test
	public void testAddValue(){
		assertEquals(true, this.SensorDataTests.addValue(6.0f));
	}

	@Test
	public void testGetAverageValue(){
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(10.3f);
		this.SensorDataTests.addValue(3.2f);
		assertEquals(6.5f,this.SensorDataTests.getAverageValue(),0.0f);

	}

	@Test
	public void testGetCount(){
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(10.3f);
		assertEquals(2,this.SensorDataTests.getTotal());
	}

	@Test
	public void testGetCurrentValue(){
		this.SensorDataTests.addValue(6.0f);
	 	this.SensorDataTests.addValue(10.3f);
		assertEquals(10.3f,this.SensorDataTests.getCurrentValue(),0.0f);
	}

	@Test
	public void testGetMaxValue(){
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(10.3f);
		assertEquals(10.3f,this.SensorDataTests.getMaxValue(),0.0f);
	}

	@Test
	public void testGetMinValue(){
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(0);
		this.SensorDataTests.addValue(8.8f);
		assertEquals(0,this.SensorDataTests.getMinValue(),0.0f);
	}

	@Test
	public void testGetName(){
		assertEquals("Not Set", this.SensorDataTests.getName());
		this.SensorDataTests.setName("TESTNAME");
		assertEquals("TESTNAME", this.SensorDataTests.getName());
	}
	
	@Test
	public void testSetName(){
		this.SensorDataTests.setName("TESTNAME");
		assertEquals("TESTNAME", this.SensorDataTests.getName());

	}
	
}
