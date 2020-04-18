package neu.manikkumar.connecteddevices.project;
import java.net.SocketException;

import org.eclipse.paho.client.mqttv3.MqttException;
import neu.manikkumar.connecteddevices.project.GatewayDataManager;
import neu.manikkumar.connecteddevices.project.CoAPServer;

/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {

    
    public static void main(String[] args) throws SocketException, InterruptedException {
        
        GatewayDataManager dataManager = new GatewayDataManager();
        //Enable/Disable the CoAP Server
        GatewayDataManager.enableCoAP = true;
        GatewayDataManager.enableSysPerf = true;
        GatewayDataManager.enableMqttListener = true;
        dataManager.run();
    }
    
}