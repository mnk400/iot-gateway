package neu.manikkumar.connecteddevices.project;
import java.net.SocketException;

import org.eclipse.paho.client.mqttv3.MqttException;
import neu.manikkumar.connecteddevices.project.GatewayDataManager;
import neu.manikkumar.connecteddevices.project.CoAPServer;

/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {

    public static final String IPADDRESS = "bubblegum.lan";
    public static void main(String[] args) throws SocketException, InterruptedException {
        
        GatewayDataManager dataManager = new GatewayDataManager(IPADDRESS);
        //Enable/Disable the CoAP Server
        GatewayDataManager.enableCoAP = true;
        //Enable/Disable redis connection
        GatewayDataManager.enableRedis = false;
        dataManager.run();
    }
    
}