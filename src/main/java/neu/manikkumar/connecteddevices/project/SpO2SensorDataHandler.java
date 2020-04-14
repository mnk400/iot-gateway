package neu.manikkumar.connecteddevices.project;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.common.DataUtil;
/**
 * TempSensorDataHandler
 */
public class SpO2SensorDataHandler extends CoapResource{
    /**
     * TempSensorDataHandler
     * Class responsible for handling the user input from 
     */

    //Get a LOGGER
    private final static Logger LOGGER = Logger.getLogger("CoAPLogger");
    //Create a dataSTore
    private SensorData dataStore = null;
    //DataUtil
    private DataUtil dataUtil;
    
	public SpO2SensorDataHandler(String name) {
        /*
         Constructor
         */
        super(name);	
        this.dataUtil = new DataUtil();
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
        ce.respond(ResponseCode.VALID, "POST_REQUEST_SUCCESS");
        LOGGER.info("Recieved Message: " + ce.getRequestText());
    }
    
    @Override
    public void handlePUT(CoapExchange ce){
        /*
         Method to handle PUT
         */
        ce.respond(ResponseCode.VALID, "PUT_REQUEST_SUCCESS");
        //Store in the datastore
        String recv = ce.getRequestText();
        LOGGER.info("Recieved CoAP Message: JSON: " + recv);
        LOGGER.info("Converting to SensorData");
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);
        LOGGER.info("Converting SensorData back to JSON: " + this.dataUtil.toJsonFromSensorData(this.getText()));
    } 

    public SensorData getText(){
        return this.dataStore;
    }
}