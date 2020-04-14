package neu.manikkumar.connecteddevices.project;
import java.net.SocketException;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;

import neu.manikkumar.connecteddevices.project.TempSensorDataHandler;

/**
 * CoAPServer
 */
public class CoAPServer extends CoapServer {
    /**
     * CoAPServer
     * Class responsible for creating and running the CoAP server
     */
    CoapServer coapServer;
    HRSensorDataHandler hrResourceHandler;
    SpO2SensorDataHandler spo2ResourceHandler;

    public CoAPServer() throws SocketException {
        /*
         Constructor
         */

        this.coapServer = new CoapServer();
        //Adding resource to the server
        this.hrResourceHandler = new HRSensorDataHandler();
        this.spo2ResourceHandler = new SpO2SensorDataHandler();
        this.coapServer.add(this.hrResourceHandler);
        this.coapServer.add(this.spo2ResourceHandler);
    }

    public Boolean serverStarter() throws SocketException{
        //Starting the server
        coapServer.start();
        return true;
    }

    
}