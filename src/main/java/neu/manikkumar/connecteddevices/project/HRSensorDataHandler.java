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
import java.lang.Thread;

/**
 * TempSensorDataHandler
 * Class responsible for handling the user input from 
 */
public class HRSensorDataHandler extends CoapResource{

    //Get a LOGGER
    private final static Logger LOGGER = Logger.getLogger("CoAPLogger");
    public static boolean userCheckThreadStatus = false;

    //Create a dataStore
     SensorData dataStore = null;

    //DataUtil
    private DataUtil dataUtil;

    //Ubidots
    private UbidotsClientConnector ubidots;

    //SMTP var
    private SmtpClientConnector smtpClient;

    //Var to limit sending after 5 readings
    private static int count   = 0;

    //counters required to check if the current
    //heart rate is okay
    private static int abnrmL  = 0;
    private static int abnrmH  = 0;
    private static int abnrmSH = 0;
    private static int nrml    = 0;

    //HR Status
    public String status = "Normal";

    /** 
     * Constructor
     * @throws MqttException
     */
	public HRSensorDataHandler() throws MqttException {
        //Name the resource
        super("heartrate");	
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
        LOGGER.info("Recieved Heart Rate Message: JSON: " + recv);
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
                this.ubidots.sendHrStatusMQTT(this.dataStore, this.status);
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
       ce.respond(ResponseCode.VALID, "PUT_REQUEST_SUCCESS");
       //Store in the datastore
       String recv = ce.getRequestText();
       LOGGER.info("Recieved Heart Rate Message: JSON: " + recv);
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
               this.ubidots.sendHrStatusMQTT(this.dataStore, this.status);
           } catch (MqttException e) {
               e.printStackTrace();
           }
           //Resetting the count af sending once
           count = 0;
       }
    } 

    /** 
     * Method to check the current status of heartbeat
     * @throws Exception
     */
    public void checkStatus() throws Exception{
        //getting the current value from the sensorData object
        float hr = this.dataStore.getCurrentValue();

        //Relatively High Heart Rate when in between 100 and 120
        //Send-out only a soft notification and not ask a user response
        if(hr>100 && hr<120){
            abnrmH++;
            abnrmSH++;
            if(abnrmH > 10){
                LOGGER.info("High Heart Rate");
                this.status = "Relatively High";
                abnrmH = 0;
                abnrmSH = 0;
                //SMTP Client sending an email
                this.smtpClient.sendMail("User Heart Rate Notification", "The user seem to be facing constantly high heart-rate");
            }
        }

        //Abnormally High Heart Rate when over 120
        //Send-out an email and ask user if they're okay
        if(hr>120){
            abnrmH++;
            abnrmSH++;
            if(abnrmSH > 5){
                LOGGER.info("Abnormally High Heart Rate");
                this.status = "Abnormally High";
                abnrmH = 0;
                abnrmSH = 0;
                //checking if another thread asking for user response is spawned or not
                //if yes, then another one isn't spawned
                if(userCheckThreadStatus==false){
                    LOGGER.info("Starting Response Thread");
                    userCheckThreadStatus=true;
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
                    this.smtpClient.sendMail("User Heart Rate Notification", "The user has an abnormally high heart-rate");
                }
            }
        }

        //Abnormally High Heart Rate when less than 50
        //Send-out an email and ask user if they're okay
        if(hr<55){
            abnrmL++;
            if(abnrmL > 5){
                LOGGER.info("Abnormally Low Heart Rate");
                this.status = "Abnormally Low";
                abnrmH = 0;
                abnrmSH = 0;
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
                this.smtpClient.sendMail("User Heart Rate Notification", "The user has an abnormally low heart-rate");
            }
        }
        
        //If the heart rate is normal, send out a reminder every 60 readings, i.e. roughly every 4 minutes
        if(hr>55 && hr<100){
            nrml++;
            if(nrml >= 60){
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