package neu.manikkumar.connecteddevices.project;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.common.DataUtil;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
/**
 * TempSensorDataHandler
 * Class responsible for handling the user input from
 */
public class MemUsageDataHandler extends CoapResource{

    //Get a LOGGER
    private final static Logger LOGGER = Logger.getLogger("CoAPLogger");

    //Create a dataSTore
    private SensorData dataStore = null;

    //DataUtil
    private DataUtil dataUtil;

    //Ubidots
    private UbidotsClientConnector ubidots;
    
	public MemUsageDataHandler() throws MqttException {
        /*
         Constructor
         */
        super("mem");	
        this.dataUtil = new DataUtil();
        this.ubidots = new UbidotsClientConnector(false);
    }
    
    @Override
    public void handleGET(CoapExchange ce){
        /*
         Method to handle GET
         */
        ce.respond(ResponseCode.VALID, "GET WORKED");
    }

    @Override
    public void handlePOST(CoapExchange ce){
        /*
         Method to handle POST
         */
        ce.respond(ResponseCode.VALID, "PUT_REQUEST_SUCCESS");
        //Store in the datastore
        String recv = ce.getRequestText();
        LOGGER.info("Recieved System Memory Message: JSON: " + recv);
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);
        this.ubidots.sendMemPayload(this.dataStore);
    }
    
    @Override
    public void handlePUT(CoapExchange ce){
        /*
         Method to handle PUT
         */
        ce.respond(ResponseCode.VALID, "PUT_REQUEST_SUCCESS");
        //Store in the datastore
        String recv = ce.getRequestText();
        LOGGER.info("Recieved System Memory Message: JSON: " + recv);
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);
        this.ubidots.sendMemPayload(this.dataStore);
    } 

    public SensorData getText(){
        return this.dataStore;
    }
}