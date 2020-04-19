package neu.manikkumar.connecteddevices.project;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.DataUtil;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
/**
 * TempSensorDataHandler
 * Class responsible for handling the user input from 
 */
public class SpO2SensorDataHandler extends CoapResource{
    //Get a LOGGER
    private final static Logger LOGGER = Logger.getLogger("CoAPLogger");
    public static boolean userCheckThreadStatus = false;

    //Create a dataSTore
    public SensorData dataStore = null;

    //DataUtil
    private DataUtil dataUtil;

    //Ubidots
    private UbidotsClientConnector ubidots;

    //SMTP var
    private SmtpClientConnector smtpClient;

    //Var to limit sending after 5 readings
    static int count=0;

    //counters required to check if the current
    //SPO2 is okay
    static int nrml = 0;
    static int lowR = 0;
    static int lowV = 0;

    public String status = "Normal";

    /** 
     * Constructor
     * @throws MqttException
     */
	public SpO2SensorDataHandler() throws MqttException {
        //Name the resource
        super("spo");	
        //Get a dataUtil object for JSON conversions
        this.dataUtil = new DataUtil();
        //Var to deal with ubidots
        this.ubidots = new UbidotsClientConnector();
        //SMTP client to send E-mail
        this.smtpClient = new SmtpClientConnector();
    }

    /** 
     * Method to handle GET
     */
    @Override
    public void handleGET(CoapExchange ce){
        //Send generic response that GET request worked
        ce.respond(ResponseCode.VALID, "GET WORKED");
    }

    /** 
     * Method to handle Post
     */
    @Override
    public void handlePOST(CoapExchange ce){
        //send a response
        ce.respond(ResponseCode.VALID, "POST_REQUEST_SUCCESS");
        //Store in the datastore
        String recv = ce.getRequestText();
        LOGGER.info("Recieved SPO2 Message: JSON: " + recv);
        //Converting to JSON
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);

        //Calling the checkStatus method to check if the current heartRate is fine
        try {
			this.checkStatus();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        //maintaining a counter so we send only one out of 5 readings to ubidots
        count++;
        if(count == 5){
            try {
                //sending the data to ubidots using MQTT and the current status
                this.ubidots.sendSpoStatusMQTT(this.dataStore, this.status);
            } catch (MqttException e) {
                e.printStackTrace();
        }
        //Resetting the count af sending once
        count = 0;
        }
    }
    
    /** 
     * Method to handle Put
     */
    @Override
    public void handlePUT(CoapExchange ce){
        //send a response
        ce.respond(ResponseCode.VALID, "POST_REQUEST_SUCCESS");
        //Store in the datastore
        String recv = ce.getRequestText();
        LOGGER.info("Recieved SPO2 Message: JSON: " + recv);
        //Converting to JSON
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);

        //Calling the checkStatus method to check if the current heartRate is fine
        try {
			this.checkStatus();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        //maintaining a counter so we send only one out of 5 readings to ubidots
        count++;
        if(count == 5){
            try {
                //sending the data to ubidots using MQTT and the current status
                this.ubidots.sendSpoStatusMQTT(this.dataStore, this.status);
            } catch (MqttException e) {
                e.printStackTrace();
        }
        //Resetting the count af sending once
        count = 0;
        }
    } 

    /** 
     * Method to check the current status of SPO2
     * @throws Exception
     */
    public void checkStatus() throws Exception{
        //getting the current value from the sensorData object
        float spo = this.dataStore.getCurrentValue();

        //Relatively low blood oxygen levels in between 80 and 90
        //Send-out only a soft notification and not ask a user response
        if(spo>80 && spo<90){
            lowR++;
            lowV++;
            if(lowR > 7){
                LOGGER.info("Low SPO2");
                this.status = "Relatively Low";
                lowR = 0;
                lowV = 0;
                //SMTP Client
                this.smtpClient.sendMail("Blood Oxygen Notification", "The user seems to have relatively low blood oxygen levels.");
            }
        }
        
        //Extremely low blood oxygen levels when less than 90
        //Send-out an email and ask for user response
        if(spo<80){
            lowR++;
            lowV++;
            if(lowV > 5){
                LOGGER.info("Extremely Low SPO2");
                this.status = "Extremely Low";
                lowR = 0;
                lowV = 0;
                //checking if another thread asking for user response is spawned or not
                //if yes, then another one isn't spawned
                if(userCheckThreadStatus==false){
                    userCheckThreadStatus=true;
                    LOGGER.info("Starting Response Thread");
                    //if no then spawn a new thread asking for user response
                    //thread exits when the user responds
                    Thread responseThread = new Thread(new Runnable(){
                        ResponseChecker response = new ResponseChecker();
                        public void run() {
                            //Creating actuotorData instance
                            ActuatorData tempData = new ActuatorData();
                            tempData.setValue("autoCheck");
                            tempData.setName("Ubidots Triggered Check");
                            tempData.setCommand("userCheck");
                            try {
                                response.checkResponse(tempData,false);
                            } catch (MqttSecurityException e) {
                                e.printStackTrace();
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }   
                        }
                    });
                    responseThread.start();
                }
                this.smtpClient.sendMail("Blood Oxygen Notification", "The user seems to have extremely low blood oxygen levels.");
            }
        }
        //If the SPO2 is normal, send out a reminder every 60 readings, i.e. roughly every 4 minute
        if(spo>90){
            nrml++;
            nrml++;
            if(nrml >= 17){
                this.status = "Normal";
                nrml = 0;
            }
        }

    }
    
    /** 
     * Method that returns the current dataStore object
     */
    public SensorData getText(){
        return this.dataStore;
    }
}