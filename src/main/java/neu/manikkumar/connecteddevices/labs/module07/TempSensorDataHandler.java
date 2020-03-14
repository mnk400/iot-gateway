package neu.manikkumar.connecteddevices.labs.module07;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import java.util.logging.Logger;

/**
 * TempSensorDataHandler
 */
public class TempSensorDataHandler extends CoapResource{
    /**
     * TempSensorDataHandler
     * Class responsible for handling the user input from 
     */

    //Get a LOGGER
    private final static Logger LOGGER = Logger.getLogger("CoAPLogger");
    //Create a dataSTore
    private String dataStore = null;

	public TempSensorDataHandler(String name) {
        /*
         Constructor
         */
		super(name);	
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
        LOGGER.info("Recieved JSON: " + ce.getRequestText());
    }
    
    @Override
    public void handlePUT(CoapExchange ce){
        /*
         Method to handle PUT
         */
        ce.respond(ResponseCode.VALID, "PUT_REQUEST_SUCCESS");
        //Store in the datastore
        dataStore = ce.getRequestText();
        LOGGER.info("Recieved JSON: " + dataStore);
    } 

    public String getText(){
        //Return datastore
        return this.dataStore;
    }
}