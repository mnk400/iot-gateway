package neu.manikkumar.connecteddevices.common;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Jedis;
import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.common.DataUtil;
/**
 * ActuatorDataListener
 */
public class ActuatorDataListener extends JedisPubSub{

    /*
     * Listener class which listens for ActuatorData Instance and then acts when data is received
     */
    //Logger
    private final static Logger LOGGER = Logger.getLogger("ActuatorDataListenerLogger");
    //DataUtil and a Jedis instance
    DataUtil dataUtil;
    Jedis jUtil;

    public ActuatorDataListener(String host){
        /*
        Constructor
        */
        //Initializing the instances
        this.dataUtil = new DataUtil();
        //Selecting the first database
        this.jUtil = new Jedis(host);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        //Logging when a subscribed 
        LOGGER.info("Subscribed to actuator notification channel");
    }

    //callback function for SensorData
    @Override
    public void onPMessage(String pattern, String channel, String message) {
            /*
            callback function for SensorData, which acts when a new key is added to the database
            */

            //Logging when JSON received
            LOGGER.info("JSON recieved");
            //Splitting the data to get the key
            String key = channel.split(":",-1)[1];
            //Using the key to get the JSON instance
            String jsonStr = this.jUtil.get(key);
            //Using the JSON to convert the received into a sensorData instance
            System.out.println(jsonStr);
    }
        
}