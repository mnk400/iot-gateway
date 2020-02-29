package neu.manikkumar.connecteddevices.labs.module06;
import org.eclipse.paho.client.mqttv3.MqttException;

import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module06.MqttClientConnector;
/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {

    public static void main(String[] args) throws MqttException {
        MqttClientConnector mqtt = new MqttClientConnector();
        ActuatorData a = new ActuatorData();
        mqtt.subscribeSensorData();
    }
    
}