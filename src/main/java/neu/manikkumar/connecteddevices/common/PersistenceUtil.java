package neu.manikkumar.connecteddevices.common;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import neu.manikkumar.connecteddevices.common.ActuatorDataListener;
import neu.manikkumar.connecteddevices.common.DataUtil;
import java.util.UUID;
import java.util.logging.Logger;
/**
 * PersistenceUtil
 */
public class PersistenceUtil {

    /*
     * Classdocs
     */
    //Logger
    private final static Logger LOGGER = Logger.getLogger("SensorDataListenerLogger");
    public static final Boolean enableThreads = true;
    //Setting address and port of your redis server
    private static final Integer redisPort = 6379;

    //Setting channel strings for each both the channels
    private static final String sensorChannel = "__keyspace@1__:*";
    private static final String actuatorChannel = "__keyspace@0__:*";

    //Creating Jedis variables for accessing redis
    private Jedis redisSensor;
    private Jedis redisActuator;
    private DataUtil dataUtil;

    public boolean connected = false;
    public PersistenceUtil(String redisIP){
         /*
         Constructor
         */
        //Initializing the jedis variables
        try {
          this.redisSensor   = new Jedis(redisIP, redisPort);
          this.redisActuator = new Jedis(redisIP, redisPort);
          //Selecting their respective databases
          this.redisActuator.select(0);
          this.redisSensor.select(1);
          this.connected = true;
        } catch (Exception e) {
          LOGGER.info("Caught an exception with jedis: pUtil");
          this.connected = false;
        }

        this.dataUtil = new DataUtil(); 
     }
     
     public void registerActuatorDataDbmsListener(ActuatorDataListener listener){
        /*
          Register ActuatorListener to redis, creates a runnable instance, passes an instance of
          ActuatorDataListener, jedis and the serverChannel to it.
        */

        //Creating an object of the threadRunner which will run our thread for the listener
        ListenerRunner threadRunner = new ListenerRunner(listener, this.redisActuator, actuatorChannel);
        //Running the thread
        Thread t1 = new Thread(threadRunner);
        if (enableThreads == true){
          t1.start();
        }
     }

	public void registerSensorDataDbmsListener(SensorDataListener listener){
         /*
          Register SensorListener to redis, creates a runnable instance, passes an instance of
          SensorDataListener, jedis and the serverChannel to it.
        */

        //Creating an object of the threadRunner which will run our thread for the listener
        ListenerRunner threadRunner = new ListenerRunner(listener, this.redisSensor, sensorChannel);
        //Running the thread
        Thread t1 = new Thread(threadRunner);
        if (enableThreads == true){
          t1.start();
        }
     }

     public boolean writeSensorDataDbmsListener(SensorData sensorData){
         /*
          Write sensorData to redis
        */
        try {
          //Converting the sensorData instance to JSON
          String jsonStr = this.dataUtil.toJsonFromSensorData(sensorData);
          //Generating a UUID and converting into a UUID string
          UUID uuid = UUID.randomUUID();
          String keyStr = "sensorData-" + uuid.toString();  
          //Writing the data on the database
          this.redisSensor.set(keyStr, jsonStr);

          return true;
        } catch (Exception e) {
          System.out.println(e);
          return false;
        }
     }

     public boolean writeActuatorDataDbmsListener(ActuatorData actuatorData){
         /*
          Write actuatorData to redis
         */
        try {
          //Converting the actuatorData instance to JSON
          String jsonStr = this.dataUtil.toJsonFromActuatorData(actuatorData);
          //Generating a UUID and converting into a UUID string
          UUID uuid = UUID.randomUUID();
          String keyStr = "actuatorData-" + uuid.toString();  
          //Writing the data on the database
          this.redisActuator.set(keyStr, jsonStr);

          return true;
        } catch (Exception e) {
          System.out.println(e);
          return false;
        }
         
     }
}