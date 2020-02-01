/**
 * 
 */
package neu.manikkumar.connecteddevices.labs;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import neu.manikkumar.connecteddevices.common.ConfigUtil;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module02.SmtpClientConnector;
import neu.manikkumar.connecteddevices.labs.module02.TempEmulatorAdapter;
import neu.manikkumar.connecteddevices.labs.module02.TempSensorEmulatorTask;

public class Module02Test
{
	private final static Logger LOGGER = Logger.getLogger("MODULE02TEST");
	public static final String TEST_PIPELINE_CFG_FILE   = "sample/ConnectedDevicesConfig_NO_EDIT_TEMPLATE_ONLY.props";
	
	SmtpClientConnector SmtpTest;
	TempSensorEmulatorTask EmulatorTest;
	TempEmulatorAdapter Emulation;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		//Get a SMTPclient instance
		SmtpTest     = new SmtpClientConnector();
		//Get a TempSensorEmulator instance
		EmulatorTest = new TempSensorEmulatorTask();
		//Get a TempEmulatorAdapter instance
		Emulation    = new TempEmulatorAdapter(1,1);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	// test methods
	
	/**
	 * Testing the publicMessage method from SMTPClientConnector to check if emails 
	 * can be sent properly and if errors are handled properly
	 */
	@Test
	public void testPublishMessage() throws ConfigurationException{
		if (this.SmtpTest.configReader.configFileLoaded == true){
			//If true the code will run locally where the real ConfigFile 
			LOGGER.info("Case 1 (Email should be sent): ");
			//Case one where connection will establish and email will be sent
			assertEquals(true,this.SmtpTest.sendMail("TestMail","Test Message"));
			//Case two where connection will fail because of wrong data 
			//and the email will not be sent but method will return a false	
			LOGGER.info("Case 2 (SMTP should not connect): ");
			//loading wrong data intentionally 
			this.SmtpTest.configReader = new ConfigUtil(TEST_PIPELINE_CFG_FILE);	
			this.SmtpTest.configReader.loadConfigData();
			assertEquals(false,this.SmtpTest.sendMail("TestMail", "Test Message"));
		}
		else{
			//If false the code will on the pipeline where the real ConfigFile doesn't exist
			//Load the Sample config file
			//Only case for the pipeline where the Sample config is loaded
			this.SmtpTest.configReader = new ConfigUtil(TEST_PIPELINE_CFG_FILE);	
			this.SmtpTest.configReader.loadConfigData();
			LOGGER.info("Case Pipeline");
			//should return a false because the pipeline doesn't have correct config data
			assertEquals(false,this.SmtpTest.sendMail("TestMail", "Test Message"));
		}
	  }

	/**
	 * Testing GenerateData from TempEmulatorTask to generate new random data which is then stored in configUtil
	 */
	@Test
	public void testSendNotification(){
		if(EmulatorTest.smtp.configReader.configFileLoaded == true){
			assertEquals(true, EmulatorTest.sendNotification("testSendNotification from TempSensorEmulatorTask.java"));
		}
	}  

	/**
	 * Testing GenerateData from TempEmulatorTask to generate new random data which is then stored in configUtil
	 */
	@Test
	public void testGenerateData(){
		//generateData returns a true if the method ran as it was intended to
		//which it should 100% of the time
		//test failing would mean imply a bug in the code
		assertEquals(true,this.EmulatorTest.generateData());
		}

	/**
	 * Testing SensorData from SensorDataTest to check if sensorData object is being returned
	 */	
	@Test	
	public void testGetSensorData(){
		//Testing if getSensorData returns an object of the sensorData
		assertEquals(this.EmulatorTest.getSensorData(),(SensorData)this.EmulatorTest.getSensorData());
		}

	/**
	 * Testing the generate String method from TempEmulatorTask if method returns a String
	 */		
	@Test
	public void testGenerateString(){
		this.EmulatorTest.generateData();
		//Testing if generateString method returns a String object
		assertEquals(this.EmulatorTest.generateString(),(String)this.EmulatorTest.generateString());
		}

	/**
	 * Testing the Run_Emulation from the TempEmulatorAdapter to check if the Emulation runs properly
	 */	
	@Test
	public void testRun_Emulation(){
		//Running emulation when enable is true, we should be returned a true
		TempEmulatorAdapter.enableTempEmulatorAdapter = true;
		this.Emulation.run();
		assertEquals(true,TempEmulatorAdapter.runCheck);
		//Running emulation when enable is false, we should be returned a false
		TempEmulatorAdapter.enableTempEmulatorAdapter = false;
		this.Emulation.run();
		assertEquals(false,TempEmulatorAdapter.runCheck);

		//setting the sleeptime and looptime to 0 and 1, should run once and return a true
		this.Emulation = new TempEmulatorAdapter(0,1);
		TempEmulatorAdapter.enableTempEmulatorAdapter = true;
		this.Emulation.run();
		assertEquals(true,TempEmulatorAdapter.runCheck);

		//setting the sleeptime and looptime to 0 and -1, should not run and return a false
		this.Emulation = new TempEmulatorAdapter(0,-1);
		this.Emulation.run();
		assertEquals(false,TempEmulatorAdapter.runCheck);

		//setting the sleeptime and looptime to -1 and 2, should not run and return a false
		this.Emulation = new TempEmulatorAdapter(-1,2);
		this.Emulation.run();
		assertEquals(false,TempEmulatorAdapter.runCheck);

		}
	
	}
