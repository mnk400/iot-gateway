package neu.manikkumar.connecteddevices.labs.module05;
import neu.manikkumar.connecteddevices.labs.module05.GatewayDataManager;


/**
 * GatewayHandlerApp
 */
public class GatewayHandlerApp {
    public static void main(String[] args) throws Exception {
        GatewayDataManager dataManager = new GatewayDataManager();
        GatewayDataManager.enable = true;
        dataManager.run(); 
	}
}