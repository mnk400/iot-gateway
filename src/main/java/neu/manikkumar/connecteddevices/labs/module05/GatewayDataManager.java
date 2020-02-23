package neu.manikkumar.connecteddevices.labs.module05;
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
    public static boolean sendEmail = false;
    public static boolean enable    = false;

    private PersistenceUtil persistenceUtil;
    private SensorDataListener listener;
    public GatewayDataManager(){
        /*
        Constructor
        */
        this.persistenceUtil = new PersistenceUtil();
        this.listener = new SensorDataListener("squishypi.lan");
    }

    public boolean run(){
        /*
        Method responsible for running PersistenceUtil
        and the listener
        */
        if(enable == false){
            return false;
        }
        else{
        this.persistenceUtil.registerSensorDataDbmsListener(this.listener);
        }
        return true;
    }
}