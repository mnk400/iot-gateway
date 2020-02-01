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
	// static
	public static final String TEST_PIPELINE_CFG_FILE   = "sample/ConnectedDevicesConfig_NO_EDIT_TEMPLATE_ONLY.props";
	
	ConfigUtil configTest;
	
	@Before
	public void setUp() throws Exception
	{
		this.configTest = new ConfigUtil();
		this.configTest.loadConfigData();
		if (this.configTest.hasConfigData() == false){
			this.configTest = new ConfigUtil(TEST_PIPELINE_CFG_FILE);
			this.configTest.loadConfigData();
		}
		System.out.println(this.configTest.hasConfigData());
	}
	
	// test methods
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#getBooleanProperty(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetBooleanProperty()
	{;
		Boolean checkBoolean = true;
		assertEquals(checkBoolean,this.configTest.getBooleanValue("ubidots.cloud","useWebAccess"));
		checkBoolean = null;
		assertEquals(checkBoolean,this.configTest.getBooleanValue("smtp.cloud","host"));
		assertEquals(checkBoolean,this.configTest.getBooleanValue("randomVal","randomKey"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#getIntegerProperty(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetIntegerProperty()
	{
		Integer checkInt = 465;
		assertEquals(checkInt,this.configTest.getIntegerValue("smtp.cloud","port"));
		checkInt = null;
		assertEquals(checkInt,this.configTest.getIntegerValue("ubidots.cloud","host"));
		assertEquals(checkInt,this.configTest.getIntegerValue("randomVal","randomKey"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#loadConfig(java.lang.String)}.
	 */
	@Test
	public void testGetProperty()
	{
		String checkString = "test.mosquitto.org";
		assertEquals(checkString,this.configTest.getValue("mqtt.cloud","host"));
		checkString = "null";
		assertEquals(checkString, this.configTest.getValue("randomVal","randomKey"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#hasProperty(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testHasProperty()
	{
		String  checkString = "null";
		Integer checkInt    = null;
		Boolean checkBool   = null;
		assertEquals(checkString,this.configTest.getValue("mqtt.cloud","NOKEY"));
		assertEquals(checkInt,this.configTest.getIntegerValue("ubidots.cloud","NOKEy"));
		assertEquals(checkBool,this.configTest.getBooleanValue("ubidots.cloud","NOKEY"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#hasSection(java.lang.String)}.
	 */
	@Test
	public void testHasSection()
	{
		String  checkString = "null";
		Integer checkInt    = null;
		Boolean checkBool   = null;
		assertEquals(checkString,this.configTest.getValue("NOSEC","ports"));
		assertEquals(checkInt,this.configTest.getIntegerValue("NOSEC","host"));
		assertEquals(checkBool,this.configTest.getBooleanValue("NOSEC","AuthToken"));
	}
	
	/**
	 * Test method for {@link com.labbenchstudios.iot.common.ConfigUtil#isConfigDataLoaded()}.
	 * @throws ConfigurationException
	 */
	@Test
	public void testIsConfigDataLoaded() throws ConfigurationException
	{
		assertEquals(true, this.configTest.loadConfigData());
		this.configTest = new ConfigUtil("/path/to/somewhere/else.props");
		assertEquals(false, this.configTest.loadConfigData());
	}
	
}
