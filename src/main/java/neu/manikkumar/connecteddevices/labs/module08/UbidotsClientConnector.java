package neu.manikkumar.connecteddevices.labs.module08;
import neu.manikkumar.connecteddevices.common.ConfigUtil;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.labs.module08.MqttClientConnector;
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
    private Variable tempVar;
    private String apiKey;
    private String varID;
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

        //Retrieving the variable ID
        this.varID = cUtil.getValue("ubidots.cloud", "tempSensorId");

        try{
            //Ubidots Variable
            this.tempVar = apiClient.getVariable(this.varID);

            //Initializing Mqtt Client
            mqtt = new MqttClientConnector(cUtil.getValue("ubidots.cloud", "mqttIp"));
            mqtt.setSensorTopic(cUtil.getValue("ubidots.cloud", "tempSensorTopic"));
            mqtt.setActuatorTopic(cUtil.getValue("ubidots.cloud", "tempActuaterTopic"));
            mqtt.connOpt.setSocketFactory(cert.loadCertificate(cUtil.getValue("ubidots.cloud", "certFile")));
            mqtt.connOpt.setUserName(cUtil.getValue("ubidots.cloud", "authToken"));
        } catch (Exception e){
            e.printStackTrace();
        }
        
        

        //Creating a thread for the MQTT Listener
        ListenerThread = new Thread( new Runnable(){
            public void run() {
                try {
                    
					mqtt.subscribeActuatorData();
				} catch (MqttSecurityException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
            }
        });
    } 

    public boolean sendTemperaturePayload(SensorData sensorData){
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

        try {
                //Setting payload from sensorData
                this.payload = sensorData.getCurrentValue();
                //Sending to ubidots
                tempVar.saveValue(this.payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOGGER.info("UBIDOTS: Sent using API");
        return true;

    }

    public boolean sendTemperaturePayloadMQTT(SensorData sensorData) throws MqttException{
        /**
         * Method responsible for sending the payload
         * to ubidots
         */

        //Setting payload from sensorData
        this.payload = sensorData.getCurrentValue();

        //Sending to ubidots
        try{
            mqtt.publishNum(String.valueOf(payload));
            LOGGER.info("UBIDOTS: Sent using MQTT");
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return true;

    }

}