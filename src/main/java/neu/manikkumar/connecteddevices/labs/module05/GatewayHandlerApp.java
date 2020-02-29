package neu.manikkumar.connecteddevices.labs.module05;
import neu.manikkumar.connecteddevices.labs.module05.GatewayDataManager;


/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {

    public static final String IPADDRESS = "172.20.10.5";
    public static void main(String[] args) throws Exception {
        /*
        Module05 main funciton
        Set enable = True to enable listener
        */
        GatewayDataManager dataManager = new GatewayDataManager(IPADDRESS);
        GatewayDataManager.enable = true;
        dataManager.run(); 
	}
}