package neu.manikkumar.connecteddevices.labs.module08;
import org.eclipse.paho.client.mqttv3.MqttException;
import neu.manikkumar.connecteddevices.labs.module08.GatewayDataManager;

/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {

    public static final String REDIS_IP = "172.20.10.5";
    public static void main(String[] args) throws MqttException {
    
        GatewayDataManager dataManager = new GatewayDataManager();
        //Enable/Disable the sensorData Listener
        GatewayDataManager.enableSensorListener = true;
        //Enable/Disable redis connection
        GatewayDataManager.enableRedis = false;
        //Enable/Disable the ubidots MQTT listener
        GatewayDataManager.enableUbiTempMqtt = true;
        dataManager.run();
     }
    
}