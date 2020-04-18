package neu.manikkumar.connecteddevices.project;
import neu.manikkumar.connecteddevices.common.ConfigUtil;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.project.MqttClientConnector;
import com.labbenchstudios.iot.common.CertManagementUtil;
import java.util.logging.Logger;
import com.ubidots.*;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/**
 * UbidotsClientConnector
 * Class that talks to Ubidots using Ubidots API
 */
public class UbidotsClientConnector {
    //Logger
    private final static Logger LOGGER = Logger.getLogger("MqttLogger");

    //ConfigUtil for reading configFiles
    private ConfigUtil cUtil;

    //MqttClient
    private static MqttClientConnector mqtt;
    
    //Ubidots apiClient
    private ApiClient apiClient;

    //Variables to send data to on ubidots
    private Variable cpuVar;
    private Variable memVar;
    private Variable gateCpuVar;
    private Variable gateMemVar;
    private Variable hrVar;
    private Variable spoVar;
    private Variable statusVar;

    //APIKEY
    private String apiKey;

    //VariableIDs on ubidots
    private String cpuVarID;
    private String memVarID;
    private String gateCpuID;
    private String gateMemID;
    private String statusVarID;

    //Payload
    private float payload;

    //Cert Management
    CertManagementUtil cert = CertManagementUtil.getInstance();

    //Thread for the MQTT listener on ubidots
    Thread ListenerThread;
    
    /**
     * Constructor that initializes the MQTTClient too
     * @throws MqttException
     */
    public UbidotsClientConnector() throws MqttException{

        //Initializing configUtil
        cUtil = new ConfigUtil();

        //Retrieving the variable IDs
        this.cpuVarID = cUtil.getValue("ubidots.cloud", "cpuVarId");
        this.memVarID = cUtil.getValue("ubidots.cloud", "memVarId");
        this.gateCpuID = cUtil.getValue("ubidots.cloud", "gateCpuId");
        this.gateMemID = cUtil.getValue("ubidots.cloud", "gateMemId");
        this.statusVarID = cUtil.getValue("ubidots.cloud", "responseId");

        try{
            //Retrieving the API Key
            this.apiKey = cUtil.getValue("ubidots.cloud", "apiKey");

            //Initializing the API client
            this.apiClient = new ApiClient(this.apiKey);

            //Ubidots Variable
            this.cpuVar  = apiClient.getVariable(this.cpuVarID);
            this.memVar  = apiClient.getVariable(this.memVarID);
            this.gateCpuVar = apiClient.getVariable(this.gateCpuID);
            this.gateMemVar = apiClient.getVariable(this.gateMemID);
            this.statusVar = apiClient.getVariable(this.statusVarID);

            //Initializing Mqtt Client
            mqtt = new MqttClientConnector(cUtil.getValue("ubidots.cloud", "mqttIp"));
            mqtt.setSensorTopic(cUtil.getValue("ubidots.cloud", "test"));
            mqtt.setUserCheckerTopic(cUtil.getValue("ubidots.cloud", "userChecker"));
            mqtt.setHrStatusTopic(cUtil.getValue("ubidots.cloud", "hrStatus"));
            mqtt.setSpoTopic(cUtil.getValue("ubidots.cloud", "spoStatus"));
            mqtt.connOpt.setSocketFactory(cert.loadCertificate(cUtil.getValue("ubidots.cloud", "certFile")));
            mqtt.connOpt.setUserName(cUtil.getValue("ubidots.cloud", "authToken"));
        } catch (Exception e){
            e.printStackTrace();
        }

        
    } 
    
    /**
     * Constructor that doesn't initialize the MQTTClient
     * @param noMqtt
     */
    public UbidotsClientConnector(boolean noMqtt){
        
        //Initializing configUtil
        cUtil = new ConfigUtil();

        //Retrieving the variable IDs
        this.cpuVarID = cUtil.getValue("ubidots.cloud", "cpuVarId");
        this.memVarID = cUtil.getValue("ubidots.cloud", "memVarId");
        this.gateCpuID = cUtil.getValue("ubidots.cloud", "gateCpuId");
        this.gateMemID = cUtil.getValue("ubidots.cloud", "gateMemId");
        this.statusVarID = cUtil.getValue("ubidots.cloud", "responseId");

        try{
            //Retrieving the API Key
            this.apiKey = cUtil.getValue("ubidots.cloud", "apiKey");

            //Initializing the API client
            this.apiClient = new ApiClient(this.apiKey);

            //Ubidots Variable
            this.cpuVar  = apiClient.getVariable(this.cpuVarID);
            this.memVar  = apiClient.getVariable(this.memVarID);
            this.gateCpuVar = apiClient.getVariable(this.gateCpuID);
            this.gateMemVar = apiClient.getVariable(this.gateMemID);
            this.statusVar = apiClient.getVariable(this.statusVarID);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method responsible for sending the iot-device CPU payload
     * to ubidots
     * @param sensorData
     * @return
     */
    public boolean sendCPUPayload(SensorData sensorData){

        try {
                //Setting payload from sensorData
                this.payload = sensorData.getCurrentValue();
                //Sending to ubidots
                cpuVar.saveValue(this.payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Sent using API");
        return true;

    }

    /**
     * Method responsible for sending the iot-device Memory payload
     * to ubidots
     * @param sensorData
     * @return
     */
    public boolean sendMemPayload(SensorData sensorData){

        try {
                //Setting payload from sensorData
                this.payload = sensorData.getCurrentValue();
                //Sending to ubidots
                memVar.saveValue(this.payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Sent using API");
        return true;

    }

    /**
     * MqttListener to listen to variable value changes on ubidots
     */
    public void ubidotsMqttListener(){
        //Creating a thread for the MQTT Listener
        ListenerThread = new Thread( new Runnable(){
            public void run() {
                try {
					mqtt.subscribeUserChecker();
				} catch (MqttSecurityException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
            }
        });
        ListenerThread.start();
    }

    /**
     * Method responsible for sending the iot-gateway CPU usage payload
     * to ubidots
     * @param sensorData
     * @return
     */
    public boolean sendGateCpuPayload(SensorData sensorData){

        try {
                //Setting payload from sensorData
                this.payload = sensorData.getCurrentValue();
                //Sending to ubidots
                gateCpuVar.saveValue(this.payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Sent using API");
        return true;

    }
    /**
     * Method responsible for sending the iot-gatway memory usage payload
     * to ubidots
     * @param sensorData
     * @return
     */
    public boolean sendGateMemPayload(SensorData sensorData){

        try {
                //Setting payload from sensorData
                this.payload = sensorData.getCurrentValue();
                //Sending to ubidots
                gateMemVar.saveValue(this.payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Sent using API");
        return true;

    }

    /**
     * Method responsible for setting status of the switch
     * back to zero
     * @return
     */
    public boolean setStatusZero(){

        try {

                //Sending to ubidots
                this.statusVar.saveValue(0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Status Variable Reset");
        return true;

    }

    /**
     * Method responsible for sending the heartrate payload
     * to ubidots
     * @param sensorData
     * @return
     */
    public boolean sendHrPayload(SensorData sensorData){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

        try {
                //Setting payload from sensorData
                this.payload = sensorData.getCurrentValue();
                //Sending to ubidots
                this.hrVar.saveValue(this.payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Sent using API");
        return true;

    }
    /**
     * Method responsible for sending the SPO2 payload
     * to ubidots
     * @param sensorData
     * @return
     */
    public boolean sendSpoPayload(SensorData sensorData){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

        try {
                //Setting payload from sensorData
                this.payload = sensorData.getCurrentValue();
                //Sending to ubidots
                this.spoVar.saveValue(this.payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Sent using API");
        return true;

    }

    /**
     * Method responsible for sending the heartrate and current status 
     * to ubidots using MQTT in JSON
     * @param sensorData
     * @param statusStr
     * @return
     */
    public boolean sendHrStatusMQTT(SensorData statusVar, String statusStr) throws MqttException{
        /**
         * Method responsible for sending the payload
         * to ubidots
         */
        Float status = statusVar.getCurrentValue();
        //Creaing the JSON string
        String str = "{\"value\":" + status +", \"context\": {\"status\": \""+statusStr+"\"}}"; 
        //Sending to ubidots
        try{
            mqtt.publishHrStatus(str);
            LOGGER.info(str);
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return true;
    }

    /**
     * Method responsible for sending the SPO2 and current status
     * to ubidots using MQTT in a JSON
     * @param sensorData
     * @param statusStr
     * @return
     */
    public boolean sendSpoStatusMQTT(SensorData statusVar, String statusStr) throws MqttException{

        Float status = statusVar.getCurrentValue();
        //Creating the JSON string
        String str = "{\"value\":" + status +", \"context\": {\"status\": \""+statusStr+"\"}}"; 
        //Sending to ubidots
        try{
            mqtt.publishSpoStatus(str);
            LOGGER.info(str);
        } catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }


}