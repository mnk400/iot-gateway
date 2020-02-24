/**
 * 
 */
package neu.manikkumar.connecteddevices.common;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.common.ActuatorData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for DataUtil functionality.
 * 
 * Instructions:
 * 1) Rename 'testSomething()' method such that 'Something' is specific to your needs; add others as needed, beginning each method with 'test...()'.
 * 2) Add the '@Test' annotation to each new 'test...()' method you add.
 * 3) Import the relevant modules and classes to support your tests.
 * 4) Run this class as unit test app
 * 5) Include a screen shot of the report when you submit your assignment
 * 
 * Please note: While some example test cases may be provided, you must write your own for the class.
 */
public class DataUtilTest
{
	// Class instances for testing
	DataUtil dataUtil;
	SensorData sensorData;
	ActuatorData actuatorData;

	// JSON strings for testing
	String sensorJSON = "{\"currentValue\":10.0,\"totalCount\":1,\"totalValue\":10.0,\"maxValue\":10.0,\"minValue\":10.0,\"timestamp\":\"2020-02-23 16:29:41.32\",\"name\":\"Temperature\"}";
	String actuatorJSON = "{\"command\":\"Decrease\",\"name\":\"Temperature Sensor Data\",\"value\":\"DOWNARROW\"}";
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		/*
		Setting up resources for the tests
		*/
		//DataUtil to test on
		this.dataUtil = new DataUtil();
		//SensorData instance filled with data
		this.sensorData = new SensorData();
		this.sensorData.setName("TestSensor");
		this.sensorData.addValue(10);
		//ActuatorData instance filled with data
		this.actuatorData = new ActuatorData();
		this.actuatorData.setName("TestActuator");
		this.actuatorData.setCommand("TestCommand");
		this.actuatorData.setValue(0.0);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		/*
        Getting rid of resources
		*/
		this.dataUtil = null;
		this.sensorData = null;
		this.actuatorData = null;
	}
	
	// test methods
	
	@Test
	public void testToJsonFromActuatorData()
	{
		/*
		Testing if ActuatorData instance is converted to JSON properly.
		*/

		//Reading the json from the method
		String jsonStr = dataUtil.toJsonFromActuatorData(actuatorData);
		//Converting the json to actuatorData instace to check if both the instances are the same
		ActuatorData tempActuatorData = dataUtil.toActuatorDataFromJson(jsonStr);
		//Following datapoints in the instance should be equal
		assertEquals(this.actuatorData.getValue(), tempActuatorData.getValue());
		assertEquals(this.actuatorData.getName(), tempActuatorData.getName());
		assertEquals(this.actuatorData.getCommand(), tempActuatorData.getCommand());
	}
	
	@Test
	public void testToActuatorDataFromJson()
	{
		/*
		Testing if JSON strings are created properly from ActuatorData
		*/

		//Creating an actuatroData instance
		ActuatorData tempActuatorData = dataUtil.toActuatorDataFromJson(this.actuatorJSON);
		//Creaing a json string usinf the above instance
		String jsonStr = dataUtil.toJsonFromActuatorData(tempActuatorData);
		assertEquals(this.actuatorJSON, jsonStr);
	}
	
	@Test
	public void testToJsonFromSensorData()
	{
		/*
		Testing if SensorData instance is converted to JSON properly
		*/

		//Reading the json from the method
		String jsonStr = dataUtil.toJsonFromSensorData(sensorData);
		//Converting the json to actuatorData instace to check if both the instances are the same
		SensorData tempSensorData = dataUtil.toSensorDataFromJson(jsonStr);
		//Following datapoints in the instance should be equal
		assertEquals(this.sensorData.getName(), tempSensorData.getName());
		assertEquals(this.sensorData.getAverageValue(), tempSensorData.getAverageValue(),0);
		assertEquals(this.sensorData.getCurrentValue(), tempSensorData.getCurrentValue(),0);
		assertEquals(this.sensorData.getMaxValue(), tempSensorData.getMaxValue(),0);
		assertEquals(this.sensorData.getMinValue(), tempSensorData.getMinValue(),0);
		assertEquals(this.sensorData.getTotal(), tempSensorData.getTotal());
	}
	
	@Test
	public void testToSensorDataFromJson()
	{
		/*
		Testing if JSON strings are created properly from sensorData
		*/

		//Creating an actuatroData instance
		SensorData tempSensorData = dataUtil.toSensorDataFromJson(this.sensorJSON);
		//Creaing a json string usinf the above instance
		String jsonStr = dataUtil.toJsonFromSensorData(tempSensorData);
		assertEquals(this.sensorJSON, jsonStr);
	}
	
	@Test
	public void writeActuatorDataToFile()
	{
		/*
		Testing if data is being written to log file
		*/	

		//Checking if we can write an object of actuatorData to the lof file.
		assertEquals(true, this.dataUtil.writeActuatorDataToFile(this.dataUtil.toJsonFromActuatorData(this.actuatorData)));
	}
	
	@Test
	public void writeSensorDataToFile()
	{
		/*
		Testing if data is being written to log file
		*/
		
		//Checking if we can write an object of sensorData to the lof file.
		assertEquals(true, this.dataUtil.writeSensorDataToFile(this.dataUtil.toJsonFromSensorData(this.sensorData)));
	}
	
}
