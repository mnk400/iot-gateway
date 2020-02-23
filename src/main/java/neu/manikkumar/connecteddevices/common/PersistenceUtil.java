package neu.manikkumar.connecteddevices.common;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import neu.manikkumar.connecteddevices.common.ActuatorDataListener;
import neu.manikkumar.connecteddevices.common.DataUtil;
import java.util.UUID;
/**
 * PersistenceUtil
 */
public class PersistenceUtil {

    /*
     * Classdocs
     */

    //Setting address and port of your redis server
    private static final String redisHost = "squishypi.lan";
    private static final Integer redisPort = 6379;

    //Setting channel strings for each both the channels
    private static final String sensorChannel = "__keyspace@1__:*";
    private static final String actuatorChannel = "__keyspace@0__:*";

    //Creating Jedis variables for accessing redis
    private Jedis redisSensor;
    private Jedis redisActuator;
    private DataUtil dataUtil;

    public PersistenceUtil(){
         /*
         Constructor
         */
        //Initializing the jedis variables
        this.redisSensor   = new Jedis(redisHost, redisPort);
        this.redisActuator = new Jedis(redisHost, redisPort);
        this.dataUtil      = new DataUtil();
        //Selecting their respective databases
        this.redisActuator.select(0);
        this.redisSensor.select(1);
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
        t1.start();
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
        t1.start();
     }

     public void writeSensorDataDbmsListener(SensorData sensorData){
         /*
          Write sensorData to redis
        */
         //Converting the sensorData instance to JSON
         String jsonStr = this.dataUtil.toJsonFromSensorData(sensorData);
         //Generating a UUID and converting into a UUID string
         UUID uuid = UUID.randomUUID();
         String keyStr = "sensorData-" + uuid.toString();  
         //Writing the data on the database
         this.redisSensor.set(keyStr, jsonStr);
     }

     public void writeActuatorDataDbmsListener(ActuatorData actuatorData){
         /*
          Write actuatorData to redis
         */
         //Converting the actuatorData instance to JSON
         String jsonStr = this.dataUtil.toJsonFromActuatorData(actuatorData);
         //Generating a UUID and converting into a UUID string
         UUID uuid = UUID.randomUUID();
         String keyStr = "actuatorData-" + uuid.toString();  
         //Writing the data on the database
         this.redisActuator.set(keyStr, jsonStr);
     }
}