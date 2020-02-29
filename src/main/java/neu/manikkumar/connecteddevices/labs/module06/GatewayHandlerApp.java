package neu.manikkumar.connecteddevices.labs.module06;
import org.eclipse.paho.client.mqttv3.MqttException;

import neu.manikkumar.connecteddevices.labs.module06.GatewayDataManager;
/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {

    public static final String IPADDRESS = "172.20.10.5";
    public static void main(String[] args) throws MqttException {
        GatewayDataManager dataManager = new GatewayDataManager(IPADDRESS);
        //Enable/Disable the sensorData Listener
        GatewayDataManager.enableSensor = true;
        //Enable/Disable the actuatorData Listener
        GatewayDataManager.enableActuator = false;
        //Enable/Disable redis connection
        GatewayDataManager.enableRedis = false;
        dataManager.run();
    }
    
}