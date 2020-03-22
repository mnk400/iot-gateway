/**
 * 
 */
package neu.manikkumar.connecteddevices.labs;

import static org.junit.Assert.assertEquals;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.manikkumar.connecteddevices.labs.module06.MqttClientConnector;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module08.GatewayDataManager;
import neu.manikkumar.connecteddevices.labs.module08.UbidotsClientConnector;

public class Module08Test
{
	// setup methods
	GatewayDataManager dataHandler;
	MqttClientConnector mqtt;
	ActuatorData actuatorData;
	SensorData sensorData;
	UbidotsClientConnector ubiDots;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		//Setting up resources
		this.dataHandler = new GatewayDataManager();
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
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		this.dataHandler = null;
		this.mqtt = null;
	}
	
	// test methods
	
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
	 */
	@Test
	public void testRun() throws MqttSecurityException, MqttException
	{
		//Should always return a true
		assertEquals(true, this.dataHandler.run());
	}
	
	/**
	 * Testing the Run in the GatewayDataManager class
	 */
	@Test
	public void testSendTemperatruePayload()
	{
		//Should always return a true
		assertEquals(true, this.ubiDots.sendTemperaturePayload(this.sensorData));
	}

	/**
	 * Testing the Run in the GatewayDataManager class
	 * @throws MqttException
	 */
	@Test
	public void testSendTemperatruePayloadMQTT() throws MqttException
	{
		//Should always return a true
		assertEquals(true, this.ubiDots.sendTemperaturePayloadMQTT(this.sensorData));
	}
	
}
