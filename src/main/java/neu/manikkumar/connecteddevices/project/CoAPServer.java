package neu.manikkumar.connecteddevices.project;
import java.net.SocketException;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * CoAPServer
 * Class responsible for creating and running the CoAP server
 */
public class CoAPServer extends CoapServer {

    //CoAPServer object
    CoapServer coapServer;

    //Resources we'll be listening for
    HRSensorDataHandler hrResourceHandler;
    SpO2SensorDataHandler spo2ResourceHandler;
    CpuUsageDataHandler cpuResourceHandler;
    MemUsageDataHandler memResourceHandler;
    UserResponseHandler userResponseHandler;

    public CoAPServer() throws SocketException, MqttException {
        /*
         Constructor
         */

        this.coapServer = new CoapServer();

        //Creaing resources
        this.hrResourceHandler = new HRSensorDataHandler();
        this.spo2ResourceHandler = new SpO2SensorDataHandler();
        this.cpuResourceHandler = new CpuUsageDataHandler();
        this.memResourceHandler = new MemUsageDataHandler();
        this.userResponseHandler = new UserResponseHandler();
        
        //Adding resources to the server
        this.coapServer.add(this.hrResourceHandler);
        this.coapServer.add(this.spo2ResourceHandler);
        this.coapServer.add(this.cpuResourceHandler);
        this.coapServer.add(this.memResourceHandler);
        this.coapServer.add(this.userResponseHandler);
    }

    public Boolean serverStarter() throws SocketException{
        //Starting the server
        try{
        coapServer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    
}