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
 */
public class HRSensorDataHandler extends CoapResource{
    /**
     * TempSensorDataHandler
     * Class responsible for handling the user input from 
     */

    //Get a LOGGER
    private final static Logger LOGGER = Logger.getLogger("CoAPLogger");
    public static boolean userCheckThreadStatus = false;
    //Create a dataSTore
    private SensorData dataStore = null;
    //DataUtil
    private DataUtil dataUtil;
    //Ubidots
    private UbidotsClientConnector ubidots;
    //SMTP var
    private SmtpClientConnector smtpClient;
    //Var to limit sending after 5 readings
    static int count   = 0;
    static int abnrmL  = 0;
    static int abnrmH  = 0;
    static int abnrmSH = 0;
    static int nrml    = 0;
    //HR Status
    private String status = "Normal";

	public HRSensorDataHandler() throws MqttException {
        /*
         Constructor
         */
        super("heartrate");	
        this.dataUtil = new DataUtil();
        this.ubidots = new UbidotsClientConnector();
        this.smtpClient = new SmtpClientConnector();
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
        //Store in the datastore
        String recv = ce.getRequestText();
        LOGGER.info("Recieved Heart Rate Message: JSON: " + recv);
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);

        try {
			this.checkStatus();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        count++;
        if(count == 5){
            try {
                this.ubidots.sendHrStatusMQTT(this.dataStore, this.status);
            } catch (MqttException e) {

                e.printStackTrace();
		}
        count = 0;
        }
        
    }
    
    @Override
    public void handlePUT(CoapExchange ce){
        /*
         Method to handle PUT
         */
        ce.respond(ResponseCode.VALID, "PUT_REQUEST_SUCCESS");
        //Store in the datastore
        String recv = ce.getRequestText();
        LOGGER.info("Recieved Heart Rate Message: JSON: " + recv);
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);
        
        try {
			this.checkStatus();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        count++;
        if(count == 5){
            try {
                this.ubidots.sendHrStatusMQTT(this.dataStore, this.status);
            } catch (MqttException e) {

                e.printStackTrace();
		}
        count = 0;
        }
	
    } 

    public void checkStatus() throws Exception{
        float hr = this.dataStore.getCurrentValue();

        if(hr>100 && hr<120){
            abnrmH++;
            abnrmSH++;
            if(abnrmH > 10){
                LOGGER.info("High Heart Rate");
                this.status = "Relatively High";
                abnrmH = 0;
                abnrmSH = 0;
                this.smtpClient.sendMail("User Heart Rate Notification", "The user seem to be facing constantly high heart-rate");
            }
        }

        if(hr>120){
            abnrmH++;
            abnrmSH++;
            if(abnrmSH > 5){
                LOGGER.info("Abnormally High Heart Rate");
                this.status = "Abnormally High";
                abnrmH = 0;
                abnrmSH = 0;
                if(userCheckThreadStatus==false){
                    LOGGER.info("Starting Response Thread");
                    userCheckThreadStatus=true;
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

        if(hr<55){
            abnrmL++;
            if(abnrmL > 5){
                LOGGER.info("Abnormally Low Heart Rate");
                this.status = "Abnormally Low";
                abnrmH = 0;
                abnrmSH = 0;
                if(userCheckThreadStatus==false){
                    userCheckThreadStatus=true;
                    LOGGER.info("Starting Response Thread");
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
        
        if(hr>55 && hr<100){
            nrml++;
            if(nrml >= 15){
                this.status = "Normal";
                nrml = 0;
            }
        }
    }
    public SensorData getText(){
        return this.dataStore;
    }
}