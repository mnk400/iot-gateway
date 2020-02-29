package neu.manikkumar.connecteddevices.labs.module06;

import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

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
		LOGGER.info("MQTT:Message received:\n\t"+ new String(message.getPayload()) );
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		/*
        Method called when a message onto a topic is delivered
        */
		LOGGER.info("MQTT:Payload delivered");
	}
}