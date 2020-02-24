package neu.manikkumar.connecteddevices.common;
import redis.clients.jedis.Jedis;
import java.util.logging.Logger;

public class ListenerRunner implements Runnable{
    /*
    Class which implements runnable and is responsible 
    for running the the listener threads.
    */
    private final static Logger LOGGER = Logger.getLogger("ActuatorDataListenerLogger");

    //Init the listeners
    private SensorDataListener sensorListener;
    private ActuatorDataListener actuatorListener;
    //Init jedis and a chanel string
    private Jedis redis;
    private String channel;

    //enable variables for the thread, to enable which listener to run
    private static boolean setSensorListener = false;
    private static boolean setActuatorListener= false;


    public ListenerRunner(SensorDataListener listener, Jedis redis, String channel){
        /*
        Constructor 1 for accepting a sensorData instance
        */
        this.sensorListener = listener;
        this.redis    = redis;
        this.channel  = channel;
        setSensorListener = true;
    }
    public ListenerRunner(ActuatorDataListener listener, Jedis redis, String channel){
        /*
        Constructor 2 for accepting an actuatorData instance 
        */
        this.actuatorListener = listener;
        this.redis    = redis;
        this.channel  = channel;
        setActuatorListener = true; 
    }

	public void run() {
        /*
        run method to run the thread based on which listener is enabled
        one instance can have only one of the following enabled 
        at a single time
        */
        if(setSensorListener = true){
            LOGGER.info("Running sensorListener thread");
            //Listen for sensorData JSONs
            this.redis.psubscribe(this.sensorListener, this.channel);
        }
        else if(setActuatorListener = true){
            LOGGER.info("Running actuatorListener thread");
            //Listen for actuatorData JSONs
            this.redis.psubscribe(this.actuatorListener, this.channel);
        }    
	}
    
}