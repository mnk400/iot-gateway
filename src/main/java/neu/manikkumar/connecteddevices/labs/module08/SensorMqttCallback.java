package neu.manikkumar.connecteddevices.labs.module08;

import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import neu.manikkumar.connecteddevices.common.DataUtil;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module08.UbidotsClientConnector;

/**
 * MqttCallback
 */
public class SensorMqttCallback implements MqttCallback{
    /**
     * Class that consists of the callback methods for
     * Sensor MQTT client
     */
    
    private final static Logger LOGGER = Logger.getLogger("MqttCallbackLogger");
    private DataUtil dataUtil = new DataUtil();
    private static int turn = 0;
    

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

        //Creaing a UbidotsClientConnector Object
        UbidotsClientConnector ubiConnect = new UbidotsClientConnector();
        
        //Logging
        LOGGER.info("MQTT:SensorData JSON received: ");
        LOGGER.info("Converting to sensorData instance");

        //Converting the recieved JSON into a sensorData instance
        SensorData temp = this.dataUtil.toSensorDataFromJson(new String(message.getPayload()));

        if(turn == 0){
            ubiConnect.sendTemperaturePayloadMQTT(temp);
            turn = 1;
        }
        else if(turn == 1){
            ubiConnect.sendTemperaturePayload(temp);
            turn = 0;
        }
        
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		/*
        Method called when a message onto a topic is delivered
        */
		LOGGER.info("MQTT:Payload delivered");
	}
}