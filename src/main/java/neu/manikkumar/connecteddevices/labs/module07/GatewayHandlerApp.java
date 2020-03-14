package neu.manikkumar.connecteddevices.labs.module07;
import java.net.SocketException;

import org.eclipse.paho.client.mqttv3.MqttException;
import neu.manikkumar.connecteddevices.labs.module07.GatewayDataManager;
import neu.manikkumar.connecteddevices.labs.module07.CoAPServer;

/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {

    public static final String IPADDRESS = "bubblegum.lan";
    public static void main(String[] args) throws SocketException {
        
        GatewayDataManager dataManager = new GatewayDataManager(IPADDRESS);
        //Enable/Disable the CoAP Server
        GatewayDataManager.enableCoAP = true;
        //Enable/Disable redis connection
        GatewayDataManager.enableRedis = false;
        dataManager.run();
    }
    
}