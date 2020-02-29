package neu.manikkumar.connecteddevices.labs.module06;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.logging.Logger;

import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.DataUtil;

import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module06.SensorMqttCallback;

public class MqttClientConnector{
    /*
    * MqttClientConnector
    * Responsible for creating clients for sensorData and actuatorData MQTT topics
    * which listens to the the topic and prints out data when recieved something.
    */
    //Static variables needed in this program
    private final static String IP = "tcp://broker.hivemq.com:1883";
    private final static Logger LOGGER = Logger.getLogger("MqttLogger");
    private final static String sensorTopic = "topic/sensor";
    private final static String actuatorTopic = "topic/actuator";

    //Setting up MQTT client
    MqttClient client;
    MqttConnectOptions connOpt = new MqttConnectOptions();
    
    //DataUtil object
    DataUtil dataUtil = new DataUtil();

    public MqttClientConnector() throws MqttException{
        /*
         Constructor
        */
        //Setting up connection for MQTT
        this.connOpt.setCleanSession(true);
        this.client = new MqttClient(IP, MqttClient.generateClientId());   
    }
    
    public boolean subscribeSensorData() throws MqttSecurityException, MqttException{
        /*
         sensorData topic subscriber, this method subscribes to the sensorData topic
        */
        LOGGER.info("MQTT:Subscribing to sensorData topic");
        //Setting the callback methods
        this.client.setCallback( new SensorMqttCallback());
        //Connecting
        this.client.connect(connOpt);
        //Subscribing to the sensorTopic
        this.client.subscribe(sensorTopic, 2);
        return true;
    }

    public boolean subscribeActuatorData() throws MqttSecurityException, MqttException{
        /*
         sensorData topic subscriber, this method subscribes to the sensorData topic
        */
        LOGGER.info("MQTT:Subscribing to actuatorData topic");
        //Setting the callback methods
        this.client.setCallback( new ActuatorMqttCallback());
        //Connecting
        this.client.connect(connOpt);
        //Subscribing to the sensorTopic
        this.client.subscribe(actuatorTopic, 2);
        return true;
    }

    public boolean publishSensorData(SensorData sensorData) throws MqttSecurityException, MqttException{
        /*
         Method to publish a message on a MQTT topic
        */

        //Creating the JSON String
        String jsonStr = dataUtil.toJsonFromSensorData(sensorData);
        //Logging
        LOGGER.info("MQTT:Publishing sensorData JSON");
        //Setting the callback methods
        this.client.setCallback( new SensorMqttCallback());
        this.client.connect(connOpt);
        //Creating a new Message 
        MqttMessage message = new MqttMessage();
        //Setting payload on the message
        message.setPayload(jsonStr.getBytes());
        //Publishing
        this.client.publish(sensorTopic, message);
        this.client.disconnect();
        return true;
    }

    public boolean publishActuatorData(ActuatorData actuatorData) throws MqttSecurityException, MqttException{
        /*
         Method to publish a message on a MQTT topic
        */

        //Creating the JSON String
        String jsonStr = dataUtil.toJsonFromActuatorData(actuatorData);
        //Logging
        LOGGER.info("MQTT:Publishing sensorData JSON");
        //Setting the callback methods
        this.client.setCallback( new SensorMqttCallback());
        this.client.connect(connOpt);
        //Creating a new Message 
        MqttMessage message = new MqttMessage();
        //Setting payload on the message
        message.setPayload(jsonStr.getBytes());
        //Publishing
        this.client.publish(actuatorTopic, message);
        this.client.disconnect();
        return true;
    }
}