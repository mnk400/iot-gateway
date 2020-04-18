package neu.manikkumar.connecteddevices.project;

import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.project.MqttClientConnector;
import neu.manikkumar.connecteddevices.project.ResponseChecker;
import java.lang.Thread;

/**
 * MqttCallback
 */
public class UserResponseCallback implements MqttCallback{

    private final static Logger LOGGER = Logger.getLogger("MqttCallbackLogger");
	UbidotsClientConnector ubidots;

	public UserResponseCallback (){
		ubidots = new UbidotsClientConnector(false);
	}
    public void connectionLost(Throwable cause) {
        /*
        Method called when the the connection disconnects
		*/
		
		LOGGER.info("MQTT:Connection to actuatorTopic lost");
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		/*
        Method called when the a message is received
		*/

		//Reading message recieved
		String tempStr = new String(message.getPayload());
		String value = tempStr.substring(9,13);
		LOGGER.info("MQTT:Message received from ubidots:" + value );

		//If the user reponse checker button is hit on the ubidots this activates
		//if the value is 1.0, trigger a user checker
		if(value.equals(" 1.0")){
            LOGGER.info("Checking if the user is okay");
			//spawn a reponseChecker thread
			Thread responseThread = new Thread(new Runnable(){
                ResponseChecker response = new ResponseChecker();
                public void run() {
                    //Creating actuotorData instance
                    ActuatorData tempData = new ActuatorData();
                    tempData.setValue("manualCheck");
                    tempData.setName("Ubidots Triggered Check");
                    tempData.setCommand("userCheck");
                    try {
						response.checkResponse(tempData,true);
					} catch (MqttSecurityException e) {
						e.printStackTrace();
					} catch (MqttException e) {
						e.printStackTrace();
					}   
                }
            });
            responseThread.start();
			
		}
		if(value.equals(" 0.0")){
			LOGGER.info("Check Over");
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		/*
        Method called when a message onto a topic is delivered
		*/
		
		LOGGER.info("MQTT:Payload delivered");
	}
}