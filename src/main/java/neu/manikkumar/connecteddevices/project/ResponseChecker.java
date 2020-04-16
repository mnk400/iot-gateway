package neu.manikkumar.connecteddevices.project;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import java.lang.Thread;
import java.util.logging.Logger;

public class ResponseChecker {

    private final static Logger LOGGER = Logger.getLogger("MqttLogger");
    
    MqttClientConnector mqtt;
    UbidotsClientConnector ubidots;

    public ResponseChecker() throws MqttException{
        this.mqtt = new MqttClientConnector();
        this.ubidots = new UbidotsClientConnector(false);
    }

    public void checkResponse(ActuatorData actuatorData, boolean toggleUbi) throws MqttSecurityException, MqttException{
        this.mqtt.publishActuatorData(actuatorData);
        int i=0;
        while(true){   
            i++;
            
            if(UserResponseHandler.sharedDataStore.equals("User Ok")){
                LOGGER.info("User response received");
                if(toggleUbi==true){
                this.ubidots.setStatusZero();
                }
                else{
                    HRSensorDataHandler.userCheckThreadStatus=false;
                    SpO2SensorDataHandler.userCheckThreadStatus=false;
                }
                UserResponseHandler.sharedDataStore="";
                break;
            }

            if(i>30){
                LOGGER.info("User response not received, sending notifications.");
                if(toggleUbi==true){
                    this.ubidots.setStatusZero();
                }
                else{
                    HRSensorDataHandler.userCheckThreadStatus=false;
                    SpO2SensorDataHandler.userCheckThreadStatus=false;
                }
                break;
            }

            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
            }
        }
        
    }
}