package neu.manikkumar.connecteddevices.project;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import java.lang.Thread;
import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.project.SmtpClientConnector;

/**
 * Class to check for a response from the user when
 * can be executed manually or automatically
 */
public class ResponseChecker {

    //logger
    private final static Logger LOGGER = Logger.getLogger("MqttLogger");
    
    //MQTTClient
    MqttClientConnector mqtt;

    //UbidotsClient
    UbidotsClientConnector ubidots;

    //SMTPClient
    SmtpClientConnector smtp;

    /**
     * Constructor
     * @throws MqttException
     */
    public ResponseChecker() throws MqttException{
        this.mqtt = new MqttClientConnector();
        this.ubidots = new UbidotsClientConnector(false);
        this.smtp = new SmtpClientConnector();
    }

    /**
     * Method to send out an MQTT message to the IOT-device's listener
     * to ask for response if the user is okay, listens for a response back via coAP
     * afterwards
     * @param actuatorData
     * @param toggleUbi
     * @throws MqttSecurityException
     * @throws MqttException
     */
    public boolean checkResponse(ActuatorData actuatorData, boolean toggleUbi) throws MqttSecurityException, MqttException{
        //Send out the message asking for a response back
        this.mqtt.publishActuatorData(actuatorData);
        int i=0;
        while(true){   
            i++;
            //Checking back for response "User ok" if the user clicks the button
            if(UserResponseHandler.sharedDataStore.equals("User Ok")){
                LOGGER.info("User response received");
                if(toggleUbi==true){
                //resest the variable on ubidots if trigger through ubidots
                this.ubidots.setStatusZero();
                }
                else{
                    //Signify the thread is no longer running and another one can be spawned
                    HRSensorDataHandler.userCheckThreadStatus=false;
                    SpO2SensorDataHandler.userCheckThreadStatus=false;
                }
                UserResponseHandler.sharedDataStore="";
                break;
            }

            //If the response is not received for a considerable amount of time
            if(i>30){
                LOGGER.info("User response not received, sending notifications.");
                smtp.sendMail("User response not recieved", "User response is not recieved after the user was asked to respond on their device. Consider checking up on them.");
                if(toggleUbi==true){
                    //resest the variable on ubidots if trigger through ubidots
                    this.ubidots.setStatusZero();
                }
                else{
                    //Signify the thread is no longer running and another one can be spawned
                    HRSensorDataHandler.userCheckThreadStatus=false;
                    SpO2SensorDataHandler.userCheckThreadStatus=false;
                }
                break;
            }

            //Sleeping, so we will only check every 1 second
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
            }
        }
        return true;
    }
}