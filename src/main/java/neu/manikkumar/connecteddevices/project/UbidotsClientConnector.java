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
 */
public class UbidotsClientConnector {
    /**
     * Class that talks to Ubidots using Ubidots API
     */

    //Logger
    private final static Logger LOGGER = Logger.getLogger("MqttLogger");


     //Setting up variables required
    private ConfigUtil cUtil;
    private static MqttClientConnector mqtt;
    private ApiClient apiClient;
;
    private Variable cpuVar;
    private Variable memVar;
    private Variable gateCpuVar;
    private Variable gateMemVar;
    private Variable hrVar;
    private Variable spoVar;
    private Variable statusVar;

    private String apiKey;

    private String cpuVarID;
    private String memVarID;
    private String gateCpuID;
    private String gateMemID;
    private String statusVarID;

    private float payload;

    //Cert Management
    CertManagementUtil cert = CertManagementUtil.getInstance();
    Thread ListenerThread;
    
    public UbidotsClientConnector() throws MqttException{
        /**
         * Constructor
         */
        
        //Initializing configUtil
        cUtil = new ConfigUtil();

        //Retrieving the API Key
        this.apiKey = cUtil.getValue("ubidots.cloud", "apiKey");

        //Initializing the API client
        this.apiClient = new ApiClient(this.apiKey);

        //Retrieving the variable IDs
        this.cpuVarID = cUtil.getValue("ubidots.cloud", "cpuVarId");
        this.memVarID = cUtil.getValue("ubidots.cloud", "memVarId");
        this.gateCpuID = cUtil.getValue("ubidots.cloud", "gateCpuId");
        this.gateMemID = cUtil.getValue("ubidots.cloud", "gateMemId");
        this.statusVarID = cUtil.getValue("ubidots.cloud", "responseId");

        try{
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
    
    public UbidotsClientConnector(boolean noMqtt){
        /**
         * Constructor
         */
        
        //Initializing configUtil
        cUtil = new ConfigUtil();

        //Retrieving the API Key
        this.apiKey = cUtil.getValue("ubidots.cloud", "apiKey");

        //Initializing the API client
        this.apiClient = new ApiClient(this.apiKey);

        //Retrieving the variable IDs
        this.cpuVarID = cUtil.getValue("ubidots.cloud", "cpuVarId");
        this.memVarID = cUtil.getValue("ubidots.cloud", "memVarId");
        this.gateCpuID = cUtil.getValue("ubidots.cloud", "gateCpuId");
        this.gateMemID = cUtil.getValue("ubidots.cloud", "gateMemId");
        this.statusVarID = cUtil.getValue("ubidots.cloud", "responseId");

        try{
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

    public boolean sendCPUPayload(SensorData sensorData){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

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

    public boolean sendMemPayload(SensorData sensorData){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

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

    public boolean sendGateCpuPayload(SensorData sensorData){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

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

    public boolean sendGateMemPayload(SensorData sensorData){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

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

    public boolean setStatusZero(){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

        try {

                //Sending to ubidots
                this.statusVar.saveValue(0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Status Variable Reset");
        return true;

    }

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

    public boolean sendHrStatusMQTT(SensorData statusVar, String statusStr) throws MqttException{
        /**
         * Method responsible for sending the payload
         * to ubidots
         */
        Float status = statusVar.getCurrentValue();
        String str = "{\"value\":" + status +", \"context\": {\"status\": \""+statusStr+"\"}}"; 
        //String str = "{\"value\":10, \"context\": {\"machine\": \"1st floor\"}}";
        //Sending to ubidots
        try{
            mqtt.publishHrStatus(str);
            LOGGER.info(str);
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return true;
    }

    public boolean sendSpoStatusMQTT(SensorData statusVar, String statusStr) throws MqttException{
        /**
         * Method responsible for sending the payload
         * to ubidots
         */
        Float status = statusVar.getCurrentValue();
        String str = "{\"value\":" + status +", \"context\": {\"status\": \""+statusStr+"\"}}"; 
        //String str = "{\"value\":10, \"context\": {\"machine\": \"1st floor\"}}";
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