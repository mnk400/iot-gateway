package neu.manikkumar.connecteddevices.project;

import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.project.MqttClientConnector;
import neu.manikkumar.connecteddevices.project.ResponseChecker;

/**
 * MqttCallback for actuatorTopic
 */
public class ActuatorMqttCallback implements MqttCallback{

    private final static Logger LOGGER = Logger.getLogger("MqttCallbackLogger");
	UbidotsClientConnector ubidots;

	/**
	 * Constructor
	 */
	public ActuatorMqttCallback(){
		ubidots = new UbidotsClientConnector(false);
	}
	
	/**
	 * Method called when the the connection disconnects
	 */
    public void connectionLost(Throwable cause) {
		
		LOGGER.info("MQTT:Connection to actuatorTopic lost");
	}

	/** 
     * Method called when the a message is received
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {

		//Reading message recieved
		String tempStr = new String(message.getPayload());
		String value = tempStr.substring(9,13);
		LOGGER.info("MQTT:Message received from ubidots:" + tempStr );

	}
	/**
	 * Method called when a message onto a topic is delivered
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {

		LOGGER.info("MQTT:Payload delivered");
	}
}