/**
 * 
 */
package neu.manikkumar.connecteddevices.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SensorDataTest{
	/**
     * Test class for SensorData functionality.
     */

	// setup methods
	SensorData SensorDataTests;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		//get a sensorData instance
		SensorDataTests = new SensorData();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	/**
	 * Testing the addValue method
	 */
	@Test
	public void testAddValue(){
		//Checking if a float value could be added
		assertEquals(true, this.SensorDataTests.addValue(6.0f));
	}

	/**
	 * Testing the getAverageValue method
	 */
	@Test
	public void testGetAverageValue(){
		//Checking if a float value could be added
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(10.3f);
		this.SensorDataTests.addValue(3.2f);
		//Checking if it returns the correct averageValue
		assertEquals(6.5f,this.SensorDataTests.getAverageValue(),0.0f);

	}
	/**
	 * Testing the getCount method
	 */
	@Test
	public void testGetCount(){
		//Checking if a series of float value could be added
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(10.3f);
		//Checking if it returns the correct count
		assertEquals(2,this.SensorDataTests.getTotal());
	}
	/**
	 * Testing the getCurrentValue method
	 */
	@Test
	public void testGetCurrentValue(){
		//Checking if a series of float value could be added
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(10.3f);
		//Checking if it returns the correct CurrentValue
		assertEquals(10.3f,this.SensorDataTests.getCurrentValue(),0.0f);
	}
	/**
	 * Testing the getGetMaxValue method
	 */
	@Test
	public void testGetMaxValue(){
		//Checking if a series of float value could be added
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(10.3f);
		//Checking if it returns the correct MaxValue
		assertEquals(10.3f,this.SensorDataTests.getMaxValue(),0.0f);
	}
	/**
	 * Testing the getGetMinValue method
	 */
	@Test
	public void testGetMinValue(){
		//Checking if a series of float value could be added
		this.SensorDataTests.addValue(6.0f);
		this.SensorDataTests.addValue(0);
		this.SensorDataTests.addValue(8.8f);
		//Checking if it returns the correct MinValue
		assertEquals(0,this.SensorDataTests.getMinValue(),0.0f);
	}
	/**
	 * Testing the getName method
	 */
	@Test
	public void testGetName(){
		//Checking the getName returns the correct name
		assertEquals("Not Set", this.SensorDataTests.getName());
		this.SensorDataTests.setName("TESTNAME");
		//Checking the getName returns the correct name after setting a new name
		assertEquals("TESTNAME", this.SensorDataTests.getName());
	}
	/**
	 * Testing the setName method
	 */
	@Test
	public void testSetName(){
		//Checking the gettName returns the correct name after setting a new name using setNam
		this.SensorDataTests.setName("TESTNAME");
		assertEquals("TESTNAME", this.SensorDataTests.getName());

	}
	
}
