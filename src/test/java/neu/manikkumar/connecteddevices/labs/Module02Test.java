/**
 * 
 */
package neu.manikkumar.connecteddevices.labs;

import static org.junit.Assert.assertEquals;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.manikkumar.connecteddevices.common.ConfigUtil;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module02.SmtpClientConnector;
import neu.manikkumar.connecteddevices.labs.module02.TempEmulatorAdapter;
import neu.manikkumar.connecteddevices.labs.module02.TempSensorEmulatorTask;

/**
 * Test class for all requisite Module02 functionality.
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
public class Module02Test
{

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
		SmtpTest     = new SmtpClientConnector();
		EmulatorTest = new TempSensorEmulatorTask();
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
	 * 
	 */
	@Test
	public void testSomething()
	{
//		fail("Not yet implemented");
	}
	@Test
	public void testPublishMessage() throws ConfigurationException{
		if (this.SmtpTest.configReader.configFileLoaded == true){
			System.out.println("Case 1 (Email should be sent): ");
			assertEquals(true,this.SmtpTest.sendMail("TestMail","Test Message"));	
			System.out.println("Case 2 (SMTP should not connect): ");
			this.SmtpTest.configReader = new ConfigUtil(TEST_PIPELINE_CFG_FILE);	
			this.SmtpTest.configReader.loadConfigData();
			assertEquals(true,this.SmtpTest.sendMail("TestMail", "Test Message"));
		}
		else{
			this.SmtpTest.configReader = new ConfigUtil(TEST_PIPELINE_CFG_FILE);	
			this.SmtpTest.configReader.loadConfigData();
			System.out.println("Case Pipeline");
			assertEquals(false,this.SmtpTest.sendMail("TestMail", "Test Message"));
		}
	  }

	@Test
	public void testSendNotification(){
		assertEquals(true, EmulatorTest.sendNotification("testSendNotification from TempSensorEmulatorTask.java"));
	}  

	@Test
	public void testGenerateData(){
		assertEquals(true,this.EmulatorTest.generateData());
		}
	
	@Test	
	public void testGetSensorData(){
		assertEquals(this.EmulatorTest.getSensorData(),(SensorData)this.EmulatorTest.getSensorData());
		}
	@Test
	public void testGenerateString(){
		this.EmulatorTest.generateData();
		assertEquals(this.EmulatorTest.generateString(),(String)this.EmulatorTest.generateString());
		}
	
	@Test
	public void testRun_Emulation(){
		
		TempEmulatorAdapter.enableTempEmulatorAdapter = true;
		this.Emulation.run();
		assertEquals(true,TempEmulatorAdapter.runCheck);
	
		TempEmulatorAdapter.enableTempEmulatorAdapter = false;
		this.Emulation.run();
		assertEquals(false,TempEmulatorAdapter.runCheck);
		
		this.Emulation = new TempEmulatorAdapter(0,1);
		TempEmulatorAdapter.enableTempEmulatorAdapter = true;
		this.Emulation.run();
		assertEquals(true,TempEmulatorAdapter.runCheck);

		this.Emulation = new TempEmulatorAdapter(0,-1);
		this.Emulation.run();
		assertEquals(false,TempEmulatorAdapter.runCheck);

		this.Emulation = new TempEmulatorAdapter(-1,2);
		this.Emulation.run();
		assertEquals(false,TempEmulatorAdapter.runCheck);

		}
	
	}
