package neu.manikkumar.connecteddevices.project;
import static org.junit.Assert.assertEquals;

import java.net.SocketException;

import javax.naming.ConfigurationException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.manikkumar.connecteddevices.project.MqttClientConnector;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.ConfigUtil;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.project.GatewayDataManager;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import neu.manikkumar.connecteddevices.project.HRSensorDataHandler;
import neu.manikkumar.connecteddevices.project.SpO2SensorDataHandler;
import neu.manikkumar.connecteddevices.project.SmtpClientConnector;
import neu.manikkumar.connecteddevices.project.ResponseChecker;


/**
 * Test class for all requisite Project functionality.
 */
public class ProjectTest
{
	public static final String TEST_PIPELINE_CFG_FILE   = "sample/ConnectedDevicesConfig_NO_EDIT_TEMPLATE_ONLY.props";
	
	SmtpClientConnector SmtpTest;
	// setup methods
	GatewayDataManager dataHandler;
	MqttClientConnector mqtt;
	CoAPServer coAP;
	HRSensorDataHandler hrResource;
	SpO2SensorDataHandler spoResource;
	ActuatorData actuatorData;
	SensorData sensorData;
	UbidotsClientConnector ubiDots;
	ResponseChecker response;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{	
		//SMTPClient
		this.SmtpTest = new SmtpClientConnector();

		//Setting up resources
		this.dataHandler = new GatewayDataManager();
		this.hrResource = new HRSensorDataHandler();
		this.spoResource = new SpO2SensorDataHandler();

		//CoAPClient
		this.coAP = new CoAPServer();

		//MQTTClient
		this.mqtt = new MqttClientConnector();

        //Adding data to actuatorData
        this.actuatorData = new ActuatorData();
        this.actuatorData.setName("TestActuator");
		this.actuatorData.setCommand("TestCommand");
		this.actuatorData.setValue(0.0);
		
        //SensorData instance filled with data
		this.sensorData = new SensorData();
		this.sensorData.setName("TestSensor");
		this.sensorData.addValue(10);

		//Ubidots instance
		this.ubiDots = new UbidotsClientConnector();

		//ResponseChecker
		this.response = new ResponseChecker();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		this.dataHandler = null;
		this.mqtt = null;
		this.ubiDots = null;
		this.sensorData = null;
	}
	
	
	/**
	 * Test to test the setHRStatusTopic in MqttClientConnecter
	 */
	@Test
	public void testSetHRTopic(){
		assertEquals(true, this.mqtt.setHrStatusTopic("test"));
	}

	/**
	 * Test to test the setSpoTopic in MqttClientConnecter
	 */
	@Test
	public void testSetSpoTopic(){
		assertEquals(true, this.mqtt.setSpoTopic("test"));
	}

	/**
	 * Test to test the setSensorTopic in MqttClientConnecter
	 */
	@Test
	public void testSensorTopic(){
		assertEquals(true, this.mqtt.setSensorTopic("test"));
	}

	/**
	 * Test to test the setSensorTopic in MqttClientConnecter
	 */
	@Test
	public void testActuatorTopic(){
		assertEquals(true, this.mqtt.setActuatorTopic("test"));
	}

	/**
	 * Test to test the setUserCheckerTopic in MqttClientConnecter
	 */
	@Test
	public void testUserCheckerTopic(){
		assertEquals(true, this.mqtt.setUserCheckerTopic("test"));
	}

	/**
	 * Testing the subscribeSensorData in the MqttClientConnecter class
	 * @throws MqttException
	 * @throws MqttSecurityException
	 */
	@Test
	public void testSubscribeSensorData() throws MqttSecurityException, MqttException
	{
		//Should always return a true
		assertEquals(true, this.mqtt.subscribeSensorData());
	}

	/**
	 * Testing the subscribeActuatorData in the MqttClientConnecter class
	 * @throws MqttException
	 * @throws MqttSecurityException
	 */
	@Test
	public void testSubscribeActuatorData() throws MqttSecurityException, MqttException
	{
		//Should always return a true
		assertEquals(true, this.mqtt.subscribeActuatorData());
	}
	
	/**
	 * Testing the subscribeSensorData in the MqttClientConnecter class
	 * @throws MqttException
	 * @throws MqttSecurityException
	 */
	@Test
	public void testPublishSensorData() throws MqttSecurityException, MqttException
	{
		//Should always return a true
		assertEquals(true, this.mqtt.publishSensorData(this.sensorData));
	}

	/**
	 * Testing the subscribeSensorData in the MqttClientConnecter class
	 * @throws MqttException
	 * @throws MqttSecurityException
	 */
	@Test
	public void testPublishActuatorData() throws MqttSecurityException, MqttException
	{
		//Should always return a true
		assertEquals(true, this.mqtt.publishActuatorData(this.actuatorData));
	}

	/**
	 * Testing the Run in the GatewayDataManager class
	 * @throws MqttException
	 * @throws MqttSecurityException
	 * @throws InterruptedException
	 */
	@Test
	public void testRun() throws MqttSecurityException, MqttException, InterruptedException
	{
		GatewayDataManager.enableCoAP=false;
		GatewayDataManager.enableMqttListener=false;
		GatewayDataManager.enableSysPerf=false;
		//Should always return a true
		assertEquals(true, this.dataHandler.run());
	}
	
	/**
	 * Checking the checkResponse method in ResponseChecker
	 * @throws MqttSecurityException
	 * @throws MqttException
	 */
	@Test
	public void checkResponse() throws MqttSecurityException, MqttException{
		assertEquals(true, this.response.checkResponse(this.actuatorData, false));
	}
	/**
	 * Testing the Run in the GatewayDataManager class
	 */
	@Test
	public void testSendHRPayload()
	{
		//Should always return a true
		assertEquals(true, this.ubiDots.sendHrPayload(this.sensorData));
	}

	/**
	 * Testing the Run in the GatewayDataManager class
	 * @throws MqttException
	 */
	@Test
	public void testSendHRPayloadMQTT() throws MqttException
	{
		//Should always return a true
		assertEquals(true, this.ubiDots.sendHrStatusMQTT(this.sensorData, null));
	}

	/**
	 * Testing if checkStatus in HRresource works the way it's supposed to
	 * @throws Exception
	 */
	@Test
	public void testCheck() throws Exception{
		SensorData s = new SensorData();
		s.addValue(75);
		this.hrResource.dataStore = s;
		this.hrResource.checkStatus();
		assertEquals("Normal", this.hrResource.status);
	}

	/**
	 * Testing if checkStatus in SpO2resource works the way it's supposed to
	 * @throws Exception
	 */
	@Test
	public void testCheckSPO() throws Exception{
		SensorData s = new SensorData();
		s.addValue(95);
		this.spoResource.dataStore = s;
		this.spoResource.checkStatus();
		assertEquals("Normal", this.hrResource.status);
	}

	/**
	 * Testing to start the server in CoAPServer
	 * @throws MqttException
	 */
	@Test
	public void testServerStarter() throws SocketException
	{
		//Should always return a true
		assertEquals(true, this.coAP.serverStarter());
	}

	/**
	 * Testing the publicMessage method from SMTPClientConnector to check if emails 
	 * can be sent properly and if errors are handled properly
	 * @throws org.apache.commons.configuration.ConfigurationException
	 */
	@Test
	public void testPublishMessage() throws org.apache.commons.configuration.ConfigurationException{
		if (this.SmtpTest.configReader.configFileLoaded == true){
			//If true the code will run locally where the real ConfigFile 
			//Case one where connection will establish and email will be sent
			assertEquals(true,this.SmtpTest.sendMail("TestMail","Test Message"));
			//Case two where connection will fail because of wrong data 
			//and the email will not be sent but method will return a false	
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
			//should return a false because the pipeline doesn't have correct config data
			assertEquals(false,this.SmtpTest.sendMail("TestMail", "Test Message"));
		}
	  }

	
}
