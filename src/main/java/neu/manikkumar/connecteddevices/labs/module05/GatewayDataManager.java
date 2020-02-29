package neu.manikkumar.connecteddevices.labs.module05;
import java.util.logging.Logger;

import neu.manikkumar.connecteddevices.common.PersistenceUtil;
import neu.manikkumar.connecteddevices.common.SensorDataListener;

/**
 * GatewayDataManager
 */
public class GatewayDataManager {
    /*
     * Class to parse the read sensorData object
     * and decide if an actuation is needed, 
     * if need be, creates an actuatorData instance 
     * and publish it on database
     */ 
    private final static Logger LOGGER = Logger.getLogger("ManagerLogger");
    public static boolean enable    = false;

    public PersistenceUtil persistenceUtil;
    public SensorDataListener listener;
    public GatewayDataManager(String IP){
        /*
        Constructor
        */
        this.persistenceUtil = new PersistenceUtil(IP);
        this.listener = new SensorDataListener(IP);
    }

    public boolean run(){
        /*
        Method responsible for running PersistenceUtil
        and the listener
        */
        //return false if enable is false
        if(enable == false){
            return false;
        }
        else if(enable == true && this.listener.connected == true && this.persistenceUtil.connected == true){
        //Running the listener, which sends actuatorData instances on the redis
        this.persistenceUtil.registerSensorDataDbmsListener(this.listener);
        }
        else{
            LOGGER.info("Listener did not run");
        }
        return true;
    }
}