package neu.manikkumar.connecteddevices.project;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;

/**
 * CoAPClientConnector
 */
public class CoAPClientConnector {
    /*
     * Client class to connect to a CoAP client
     */
    //Get a logger
    private final static Logger LOGGER = Logger.getLogger("DataMLogger");
    //Initialize the CoAPClient
    private CoapClient coAPClient;

    public CoAPClientConnector(String url){
        /*
          Constructor
         */ 
        //try {
            //Creating a response variable and logging it
        this.coAPClient = new CoapClient(url);
        //} catch (Exception e) {
         //   LOGGER.info("ERROR: CLIENT CANNOT CONNECT");
        //}
        
    }

    public boolean dataGET(){
        /*
          Method to get Data from the server
         */ 
        try {
            //Creating a response variable and logging it
            CoapResponse resc = this.coAPClient.get();
            //LOGGER.info(resc.getResponseText());
        } catch (Exception e) {
            LOGGER.info("ERROR: Cannot Get");
            return true;
        }

        return true;
    }

    public boolean dataDELETE(){
        /*
          Method to delete data on server
         */ 
        try {
            //Creating a response variable and logging it
            CoapResponse resc = this.coAPClient.delete();
            //LOGGER.info(resc.getResponseText());
        } catch (Exception e) {
            LOGGER.info("ERROR: Cannot Delete");
        }
        
        return true;
    }

    public boolean dataPUT(String msgStr){
        /*
          Method to put data on server
         */ 
        try {
            //Creating a response variable and logging it
            CoapResponse resc = this.coAPClient.put(msgStr, 0);
            LOGGER.info(resc.getResponseText());
        } catch (Exception e) {
            LOGGER.info("ERROR: Cannot Put");
        }
        
        return true;
    }

    public boolean dataPOST(String msgStr){
        /*
          Method to post data on server
         */ 
        try {
            //Creating a response variable and logging it
            CoapResponse resc = this.coAPClient.post(msgStr, 0);
            LOGGER.info(resc.getResponseText());
        } catch (Exception e) {
            LOGGER.info("ERROR: Cannot Post");
        }
        
        return true;
    }

    public boolean ping(){
        //try {
            boolean resc = this.coAPClient.ping();
        //} catch (Exception e) {
        //    LOGGER.info("ERROR: Cannot Post");
        //}
        
        return true;
    }
}