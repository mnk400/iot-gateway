package neu.manikkumar.connecteddevices.labs.module08;

import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import neu.manikkumar.connecteddevices.labs.module08.UbidotsClientConnector;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.labs.module08.MqttClientConnector;
/**
 * MqttCallback
 */
public class ActuatorMqttCallback implements MqttCallback{

    private final static Logger LOGGER = Logger.getLogger("MqttCallbackLogger");

    public void connectionLost(Throwable cause) {
        /*
        Method called when the the connection disconnects
        */
		LOGGER.info("MQTT:Connection to sensorTopic lost");
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		/*
        Method called when the a message is received
		*/

		//Reading message recieved
		String tempStr = new String(message.getPayload());
		String value = tempStr.substring(9,12);
		LOGGER.info("MQTT:Message received from ubidots:\n\t" + value );

		LOGGER.info("Creating actuatorData instance");
		//Creating actuotorData instance
		ActuatorData tempData = new ActuatorData();
		tempData.setValue(value);
		tempData.setName("Ubidots Recv Data");
		tempData.setCommand("rint");
		
		//Sending actuatorData to IOT-Device
		MqttClientConnector mqtt = new MqttClientConnector();
		mqtt.publishActuatorData(tempData);

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		/*
        Method called when a message onto a topic is delivered
        */
		LOGGER.info("MQTT:Payload delivered");
	}
}