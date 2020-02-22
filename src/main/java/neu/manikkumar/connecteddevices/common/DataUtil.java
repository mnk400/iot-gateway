package neu.manikkumar.connecteddevices.common;
import neu.manikkumar.connecteddevices.common.*;
import com.google.gson.*;
/**
 * DataUtil
 */
public class DataUtil {

    /*
     * Class to handle JSON data,
     * convert from JSON to sensorData or actuatorData instance
     * or convert sensorData or actuatorDat instance to JSON.
     */
    //Creating a GsonBuilder object
    private GsonBuilder builder = new GsonBuilder();
    //Defining a gson object
    private Gson gson;

    public DataUtil(){
        /*
         * Constructor
         */

        //Initializing gson object
        this.gson = this.builder.create();
    }

    public String toJsonFromSensorData(SensorData sensorData){
        /*
         * Convert from JSON to SensorData instance
         */

        //gson allows us to automatically create a JSON from the public or protected
        //variables from a specified object
        String jsonStr = this.gson.toJson(sensorData);
        return jsonStr;
    }
    // {"currentValue":10.0,"totalCount":1,"totalValue":10.0,"maxValue":10.0,"minValue":10.0,"timestamp":"2020-02-22 02:11:58.904","name":"Not Set"}
    public SensorData toSensorDataFromJson(String jsonStr){
        /*
         * Convert from SensorData instance to JSON
         */

        //gson allows us to create a class object directly from an input gson
        //string and a specified classtype
        SensorData sensorData = gson.fromJson(jsonStr,SensorData.class);
        return sensorData;
    }  
    
    public void writeSensorDataToFile(){
        /*
        Converts SensorData to JSON and writes to the filesystem
        */
    }

    public String toJsonFromActuatorData(ActuatorData actuatorData){
        /*
        Convert from JSON to ActuatorData instance
        */
        String jsonStr = this.gson.toJson(actuatorData);
        return jsonStr;
    }

    public ActuatorData toActuatorDataFromJson(String jsonStr){
        /*
        Convert from ActuatorData instance to JSON
        */
        ActuatorData actuatorData = gson.fromJson(jsonStr,ActuatorData.class);
        System.out.println(actuatorData.getValue());
        return actuatorData;
    }  
    
    public void writeActuatorDataToFile(){
        /*
        Converts ActuatorData to JSON and writes to the filesystem
        */
    }
    
}