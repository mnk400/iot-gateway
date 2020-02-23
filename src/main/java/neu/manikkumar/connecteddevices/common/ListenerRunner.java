package neu.manikkumar.connecteddevices.common;
import redis.clients.jedis.Jedis;
import java.util.logging.Logger;
/**
 * ListenerRunner
 */
public class ListenerRunner implements Runnable{

    private final static Logger LOGGER = Logger.getLogger("ActuatorDataListenerLogger");

    private SensorDataListener sensorListener;
    private ActuatorDataListener actuatorListener;
    private Jedis redis;
    private String channel;

    private static boolean setSensorListener = false;
    private static boolean setActuatorListener= false;

    public ListenerRunner(SensorDataListener listener, Jedis redis, String channel){
        this.sensorListener = listener;
        this.redis    = redis;
        this.channel  = channel;
        setSensorListener = true;
    }
    public ListenerRunner(ActuatorDataListener listener, Jedis redis, String channel){
        this.actuatorListener = listener;
        this.redis    = redis;
        this.channel  = channel;
        setActuatorListener = true; 
    }

	public void run() {
        // TODO Auto-generated method stub
        if(setSensorListener = true){
            LOGGER.info("Running sensorListener thread");
            this.redis.psubscribe(this.sensorListener, this.channel);
        }
        else if(setActuatorListener = true){
            LOGGER.info("Running actuatorListener thread");
            this.redis.psubscribe(this.actuatorListener, this.channel);
        }    
	}
    
}