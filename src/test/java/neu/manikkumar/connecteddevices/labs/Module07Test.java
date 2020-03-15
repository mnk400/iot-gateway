/**
 * 
 */
package neu.manikkumar.connecteddevices.labs;

import static org.junit.Assert.assertEquals;

import java.net.SocketException;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.manikkumar.connecteddevices.labs.module07.CoAPClientConnector;
import neu.manikkumar.connecteddevices.labs.module07.CoAPServer;
import neu.manikkumar.connecteddevices.labs.module07.TempSensorDataHandler;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module07.GatewayDataManager;
import neu.manikkumar.connecteddevices.labs.module07.GatewayHandlerApp;
public class Module07Test
{
	// setup methods
	GatewayDataManager dataHandler;
	CoAPServer coAP;
	TempSensorDataHandler tempHandler;
	ActuatorData actuatorData;
	SensorData sensorData;
	CoAPClientConnector coAPClient;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		//Setting up resources
		this.dataHandler = new GatewayDataManager(GatewayHandlerApp.IPADDRESS);
		this.coAP = new CoAPServer();
		this.tempHandler = new TempSensorDataHandler("Temp");
		//CoAPClientConnector
		this.coAPClient = new CoAPClientConnector("coap://coap.me:5683/test/test/test/test");
        //Adding data to actuatorData
        this.actuatorData = new ActuatorData();
        this.actuatorData.setName("TestActuator");
		this.actuatorData.setCommand("TestCommand");
        this.actuatorData.setValue(0.0);
        //SensorData instance filled with data
		this.sensorData = new SensorData();
		this.sensorData.setName("TestSensor");
        this.sensorData.addValue(10);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	
	@After
	public void tearDown() throws Exception
	{
		this.dataHandler = null;
		this.coAP = null;
	}
	
	// test methods
	
	/**
	 * Testing the testServerStarter in CoAPServer
	 * @throws SocketException
	 */
	@Test
	public void testServerStarter() throws SocketException
	{
		//Should always return a true
		assertEquals(true, this.coAP.serverStarter());
	}

	/**
	 * Testing the DataGET in CoAPClientConnector
	 */
	@Test
	public void testDataGET()
	{
		//Should always return a true
		assertEquals(true, this.coAPClient.dataGET());
	}

	/**
	 * Testing the DataPUT in CoAPClientConnector
	 */
	@Test
	public void testDataPUT()
	{
		//Should always return a true
		assertEquals(true, this.coAPClient.dataPUT("TEST"));
	}

	/**
	 * Testing the DataPOST in CoAPClientConnector
	 */
	@Test
	public void testDataPOST()
	{
		//Should always return a true
		assertEquals(true, this.coAPClient.dataPOST("TEST"));
	}

	/**
	 * Testing the PING in CoAPClientConnector
	 */
	@Test
	public void testDataPING()
	{
		//Should always return a true
		assertEquals(true, this.coAPClient.ping());
	}

	/**
	 * Testing the DataDELETE in CoAPClientConnector
	 */
	@Test
	public void testDataDELETE()
	{
		//Should always return a true
		assertEquals(true, this.coAPClient.dataDELETE());
	}


	/**
	 * Testing the getText in TempSensorDataHandler
	 */
	@Test
	public void testGetText()
	{
		//Should always return a true
		assertEquals(true, this.coAPClient.dataGET());
	}
	

	/**
	 * Testing the Run in the GatewayDataManager class
	 * @throws InterruptedException
	 */
	@Test
	public void testRun() throws InterruptedException
	{
		//Should always return a true
		assertEquals(true, this.dataHandler.run());
	}
	
}
