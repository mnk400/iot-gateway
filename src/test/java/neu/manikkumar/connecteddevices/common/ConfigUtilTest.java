/**
 * 
 */

package neu.manikkumar.connecteddevices.common;
import static org.junit.Assert.*;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;


public class ConfigUtilTest
{
	/*
	 * Unittest for the ConfigUtil
	 */
	public static final String TEST_PIPELINE_CFG_FILE   = "sample/ConnectedDevicesConfig_NO_EDIT_TEMPLATE_ONLY.props";
	
	ConfigUtil configTest;
	
	@Before
	public void setUp() throws Exception
	{	
		/*
		 *Setting up required resources
		 */
		this.configTest = new ConfigUtil();
		this.configTest.loadConfigData();
		//The following case is for the github/gitbucket pipeline where the real config file is not pushed hence the tests are performed using a SAMPLE
		if (this.configTest.hasConfigData() == false){
			//loading the sample config file
			this.configTest = new ConfigUtil(TEST_PIPELINE_CFG_FILE);
			this.configTest.loadConfigData();
		}
		System.out.println(this.configTest.hasConfigData());
	}
	
	// test methods
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#getBooleanProperty(java.lang.String, java.lang.String)}.
	 * Tests retrieval of a boolean property.
	 */
	@Test
	public void testGetBooleanProperty()
	{;
		Boolean checkBoolean = true;
		//tesing when the key is a Boolean, should return a true
		assertEquals(checkBoolean,this.configTest.getBooleanValue("ubidots.cloud","useWebAccess"));
		checkBoolean = null;
		//testing when the key is not a boolean, should return a none
		assertEquals(checkBoolean,this.configTest.getBooleanValue("smtp.cloud","host"));
		//testing when random values are passed into section and key, should expect a none
		assertEquals(checkBoolean,this.configTest.getBooleanValue("randomVal","randomKey"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#getIntegerProperty(java.lang.String, java.lang.String)}.
	 * Tests retrieval of an integer property.
	 */
	@Test
	public void testGetIntegerProperty()
	{
		Integer checkInt = 465;
		//testing when the key is a Integer, should return an int
		assertEquals(checkInt,this.configTest.getIntegerValue("smtp.cloud","port"));
		checkInt = null;
		//testing when the key is not a Integer, should return a false
		assertEquals(checkInt,this.configTest.getIntegerValue("ubidots.cloud","host"));
		//testing when random values are passed into section and key, should expect a false
		assertEquals(checkInt,this.configTest.getIntegerValue("randomVal","randomKey"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#loadConfig(java.lang.String)}.
	 * Tests retrieval of a string property.
	 */
	@Test
	public void testGetProperty()
	{
		String checkString = "test.mosquitto.org";
		//#testing when the key is a string, should return the same string
		assertEquals(checkString,this.configTest.getValue("mqtt.cloud","host"));
		checkString = "null";
		//testing when random values are passed into section and key, should expect a false
		assertEquals(checkString, this.configTest.getValue("randomVal","randomKey"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#hasProperty(java.lang.String, java.lang.String)}.
	 * Tests if a property exists.
	 */
	@Test
	public void testHasProperty()
	{
		String  checkString = "null";
		Integer checkInt    = null;
		Boolean checkBool   = null;
		//testing getValue when a key is asked for that doesn't exist
		assertEquals(checkString,this.configTest.getValue("mqtt.cloud","NOKEY"));
		//testing getIngegerValue when a key is asked for that doesn't exist
		assertEquals(checkInt,this.configTest.getIntegerValue("ubidots.cloud","NOKEY"));
		//testing getBooleanValue when a key is asked for that doesn't exist
		assertEquals(checkBool,this.configTest.getBooleanValue("ubidots.cloud","NOKEY"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#hasSection(java.lang.String)}.
	 * Tests if a section exists.
	 */
	@Test
	public void testHasSection()
	{
		String  checkString = "null";
		Integer checkInt    = null;
		Boolean checkBool   = null;
		//testing getValue when a section is asked for that doesn't exist
		assertEquals(checkString,this.configTest.getValue("NOSEC","ports"));
		//testing getIntegerValue when a section is asked for that doesn't exist
		assertEquals(checkInt,this.configTest.getIntegerValue("NOSEC","host"));
		//testing getBooleanValue when a section is asked for that doesn't exist
		assertEquals(checkBool,this.configTest.getBooleanValue("NOSEC","AuthToken"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#isConfigDataLoaded()}.
	 * @throws ConfigurationException
	 * Tests if the configuration is loaded.
	 */
	@Test
	public void testIsConfigDataLoaded() throws ConfigurationException
	{
		//checking if the config file has been loaded, when the path is correct
		assertEquals(true, this.configTest.loadConfigData());
		//checking if the config file has been loaded, when the path is wrong
		this.configTest = new ConfigUtil("/path/to/somewhere/else.props");
		assertEquals(false, this.configTest.loadConfigData());
	}
	
}
