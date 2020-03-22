package neu.manikkumar.connecteddevices.labs.module08;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import neu.manikkumar.connecteddevices.common.PersistenceUtil;
import neu.manikkumar.connecteddevices.common.SensorDataListener;
import neu.manikkumar.connecteddevices.labs.module08.GatewayHandlerApp;

import java.util.logging.Logger;

public class GatewayDataManager {
    /**
     * GatewayDataManager
     * Class responsible for running the listener threads that listen for MQTT inputs 
     */
    private final static Logger LOGGER = Logger.getLogger("DataMLogger");

    //Enable settings 
    public static boolean enableSensorListener = false;
    public static boolean enableRedis = false;
    public static boolean enableUbiTempMqtt = false;

    //Creating a Thread for both the listeners
    Thread sensorListenerThread;
    Thread actuatorListenerThread;

    //Creating instances of objects that'll be needed in the class
    public PersistenceUtil persistenceUtil;
    public SensorDataListener listener;
    public UbidotsClientConnector ubidots;
    

    public GatewayDataManager() throws MqttException{
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

        //Initializing the PersistenceUtil, It's listener and an ubidots object
        this.persistenceUtil = new PersistenceUtil(GatewayHandlerApp.REDIS_IP);
        this.listener = new SensorDataListener(GatewayHandlerApp.REDIS_IP);
        this.ubidots = new UbidotsClientConnector();

    } 

    public boolean run(){
        /**
        * Run method to run the threads 
        */

        //Checking if sensorDataListener for IOT Devcie is enabled
        if (enableSensorListener == true){
            System.out.println("STARTING THREAD");
            this.sensorListenerThread.start();
        }
        else{
            LOGGER.info("SensorListened disabled");
        }

        //Checking if MQTT listener to listen from Ubidots is enabled
        if (enableUbiTempMqtt == true){
            this.ubidots.ListenerThread.start();
        }
        else{
            LOGGER.info("Ubidots MQTT Listener Disabled");   
        }

        //Checking if redis is enabled
        if(enableRedis == true && this.listener.connected == true && this.persistenceUtil.connected == true){
            //Running the listener, which sends actuatorData instances on the redis
            this.persistenceUtil.registerSensorDataDbmsListener(this.listener);
        }
        else{
            LOGGER.info("Redis Listener did not run");
        }

        return true;
    }
}