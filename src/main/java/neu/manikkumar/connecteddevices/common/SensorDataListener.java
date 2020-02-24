package neu.manikkumar.connecteddevices.common;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Jedis;
import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.common.ConfigUtil;
/**
 * SensorDataListener
 */

public class SensorDataListener extends JedisPubSub{

    /*
     * Listener class which extends JedisPubSub and creates overridden callback methods
     * and listens for SensorData Instance and then acts when data is received
     */
    //Logger
    private final static Logger LOGGER = Logger.getLogger("SensorDataListenerLogger");
    //DataUtil and a Jedis instance
    private DataUtil dataUtil;
    private Jedis jUtil;
    //Variables to decide weather to create new actuatorData or not
    private float wOld = -99.99f;
    private float wNew = 0.00f;
    //Nominal temp
    private float nominal;
    //PersistenceUtil instance
    public PersistenceUtil pUtil;
    public ActuatorData actuatorData;
    public boolean connected = false;
    public ConfigUtil config;
    public SensorDataListener(String host){
        /*
        Constructor
        */
        
        //Initializing the instances
        this.dataUtil = new DataUtil();
        try {
            this.jUtil = new Jedis(host);
            //Selecting the first database
            this.jUtil.select(1);
            this.connected = true;
        } catch (Exception e) {
            LOGGER.info("Caught an exception with jedis: SensorDataListener");
            this.connected = false;
        }


        this.config = new ConfigUtil();
        this.nominal = config.getIntegerValue("device", "nominalTemp");
        //Initializing PersistenceUtil 
        this.pUtil = new PersistenceUtil();
        this.actuatorData = new ActuatorData();
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        //Logging when a subscribed 
        LOGGER.info("Subscribed to Sensor Notification Channel");
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        /*
        Overriden callback function for SensorData, which acts when a new key is added to the database
        */

        //Logging when JSON received
        LOGGER.info("sensorData JSON received");
        //Splitting the data to get the key
        String key = channel.split(":",-1)[1];
        //Using the key to get the JSON instance
        String jsonStr = this.jUtil.get(key);
        //Using the JSON to convert the received into a sensorData instance
        SensorData sensorData = this.dataUtil.toSensorDataFromJson(jsonStr);
        //Create an actuatorData Instance
        ActuatorData actuatorData = this.createActuatorData(sensorData);
        //Writing the actuatorData on redis
        this.pUtil.writeActuatorDataDbmsListener(actuatorData);
        LOGGER.info("ActuatorData written on redis");
    }

    public ActuatorData createActuatorData(SensorData sensorData){
        /*
        Method to create an actuatorData instance
        */
        LOGGER.info("Creating actuatorData from sensorData");
        Float s = sensorData.getCurrentValue();
        
        actuatorData.setName(sensorData.getName());
        if(s < this.nominal - 3){
            //If less then nominal Increase temp
            actuatorData.setCommand("Increase");
            actuatorData.setValue("UPARROW");
        }
        else if(s > this.nominal + 3){
            //If greater then nominal then Decrease temp
            actuatorData.setCommand("Decrease");
            actuatorData.setValue("DOWNARROW");
        }
        else if(s< this.nominal -3 && s>this.nominal+3){
            //If nominal then keep it stable
            actuatorData.setCommand("Stable");
            actuatorData.setValue("TICK");
        }
        else{
            LOGGER.info("ERROR:Unknown Value");
        }
        return actuatorData;
    }

}