package neu.manikkumar.connecteddevices.labs.module06;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import neu.manikkumar.connecteddevices.common.PersistenceUtil;
import neu.manikkumar.connecteddevices.common.SensorDataListener;

import java.util.logging.Logger;

public class GatewayDataManager {
    /**
     * GatewayDataManager
     * Class responsible for running the listener threads that listen for MQTT inputs 
     */
    private final static Logger LOGGER = Logger.getLogger("DataMLogger");
    //Enable settings 
    public static boolean enableSensor = false;
    public static boolean enableActuator = false;
    public static Boolean enableRedis = false;
    //Creating a Thread for both the listeners
    Thread sensorListenerThread;
    Thread actuatorListenerThread;

    public PersistenceUtil persistenceUtil;
    public SensorDataListener listener;
    

    public GatewayDataManager(String IP) throws MqttException{
        /*
         Constructor
         */

        //Initializing the sensorListenerThread
        this.sensorListenerThread = new Thread( new Runnable(){
            MqttClientConnector mqtt = new MqttClientConnector();
            public void run() {
                try {
					mqtt.subscribeSensorData();
				} catch (MqttSecurityException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
            }
        });

         //Initializing the actuatorListenerThread
        this.actuatorListenerThread = new Thread( new Runnable(){
            MqttClientConnector mqtt = new MqttClientConnector();
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

        this.persistenceUtil = new PersistenceUtil(IP);
        this.listener = new SensorDataListener(IP);

    } 

    public boolean run(){
        /**
        * Run method to run the threads 
        */
        if (enableSensor == true){
        this.sensorListenerThread.start();
        }
        else if (enableSensor == true){
            this.actuatorListenerThread.start();
        }
        else{
            LOGGER.info("No listener enabled");
        }

        if(enableRedis == true && this.listener.connected == true && this.persistenceUtil.connected == true){
        //Running the listener, which sends actuatorData instances on the redis
        this.persistenceUtil.registerSensorDataDbmsListener(this.listener);
        }
        else{
            LOGGER.info("Listener did not run");
        }

        return true;
    }
}