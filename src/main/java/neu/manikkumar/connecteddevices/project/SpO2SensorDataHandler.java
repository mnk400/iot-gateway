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
 */
public class SpO2SensorDataHandler extends CoapResource{
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
    static int count=0;

    static int nrml = 0;
    static int lowR = 0;
    static int lowV = 0;

    private String status = "Normal";

	public SpO2SensorDataHandler() throws MqttException {
        /*
         Constructor
         */
        super("spo");	
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
        LOGGER.info("Recieved SPO2 Message: JSON: " + recv);
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);

        try {
			this.checkStatus();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
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
        LOGGER.info("Recieved SPO2 Message: JSON: " + recv);
        this.dataStore = this.dataUtil.toSensorDataFromJson(recv);

        try {
			this.checkStatus();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        count++;
        if(count == 5){
            try {
                this.ubidots.sendSpoStatusMQTT(this.dataStore, this.status);
            } catch (MqttException e) {

                e.printStackTrace();
		}
        count = 0;
        }
    } 

    public void checkStatus() throws Exception{
        float spo = this.dataStore.getCurrentValue();

        if(spo>80 && spo<90){
            lowR++;
            lowV++;
            if(lowR > 7){
                LOGGER.info("Low SPO2");
                this.status = "Relatively Low";
                lowR = 0;
                lowV = 0;
                this.smtpClient.sendMail("Blood Oxygen Notification", "The user seems to have relatively low blood oxygen levels.");
            }
        }

        if(spo<80){
            lowR++;
            lowV++;
            if(lowV > 5){
                LOGGER.info("Extremely Low SPO2");
                this.status = "Extremely Low";
                lowR = 0;
                lowV = 0;
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
                this.smtpClient.sendMail("Blood Oxygen Notification", "The user seems to have extremely low blood oxygen levels.");
            }
        }
        if(spo>90){
            nrml++;
            nrml++;
            if(nrml >= 17){
                this.status = "Normal";
                nrml = 0;
            }
        }

    }

    public SensorData getText(){
        return this.dataStore;
    }
}