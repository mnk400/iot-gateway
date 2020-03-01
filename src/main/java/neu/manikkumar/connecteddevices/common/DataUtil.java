package neu.manikkumar.connecteddevices.common;
import neu.manikkumar.connecteddevices.common.*;
import com.google.gson.*;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 * DataUtil
 */
public class DataUtil {
    /*
     * Class to handle JSON data,
     * convert from JSON to sensorData or actuatorData instance
     * or convert sensorData or actuatorDat instance to JSON.
     */

    Logger sensorDatalogger = Logger.getLogger("SensorDataUtilLog");  
    Logger actuatorDatalogger = Logger.getLogger("ActuatorDataUtilLog");  
    FileHandler sfh;
    FileHandler afh;

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
        try {  
            // This block configure the logger with handler and formatter  
            FileHandler sfh = new FileHandler("logs/sensorData.log");  
            FileHandler afh = new FileHandler("logs/actuatorData.log");  
    
            sensorDatalogger.addHandler(sfh);
            SimpleFormatter sFormatter = new SimpleFormatter();  
            sfh.setFormatter(sFormatter);  
    
            actuatorDatalogger.addHandler(afh);
            SimpleFormatter aFormatter = new SimpleFormatter();  
            afh.setFormatter(aFormatter);  
    
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }

    public String toJsonFromSensorData(SensorData sensorData){
        /*
         * Convert from JSON to SensorData instance
         */

        //gson allows us to automatically create a JSON from the public or protected
        //variables from a specified object
        String jsonStr = this.gson.toJson(sensorData);
        //Logging and writing to file
        this.writeSensorDataToFile("Converted to JSON from sensorData\n" + pretty(jsonStr));
        return jsonStr;
    }
    // {"currentValue":10.0,"totalCount":1,"totalValue":10.0,"maxValue":10.0,"minValue":10.0,"timestamp":"2020-02-22 02:11:58.904","name":"Not Set"}
    public SensorData toSensorDataFromJson(String jsonStr){
        /*
         * Convert from SensorData instance to JSON
         */
        //Logging and writing to the file
        this.writeSensorDataToFile("Converted to SensorData from JSON\n" + pretty(jsonStr));
        //gson allows us to create a class object directly from an input gson
        //string and a specified classtype 
        SensorData sensorData = gson.fromJson(pretty(jsonStr),SensorData.class);
        return sensorData;
    }  
    
    public boolean writeSensorDataToFile(String logData){
        /*
        Converts SensorData to JSON and writes to the filesystem
        */
        this.sensorDatalogger.info(logData);
        return true;
    }

    public String toJsonFromActuatorData(ActuatorData actuatorData){
        /*
        Convert from JSON to ActuatorData instance
        */
        String jsonStr = this.gson.toJson(actuatorData);
        //Logging and writing to file
        this.writeActuatorDataToFile("Converted to JSON from actuatorData\n" + pretty(jsonStr));
        return jsonStr;
    }

    public ActuatorData toActuatorDataFromJson(String jsonStr){
        /*
        Convert from ActuatorData instance to JSON
        */
        
        //gson allows us to automatically create a JSON from the public or protected
        //variables from a specified object
        this.writeActuatorDataToFile("Converted to ActuatorData from JSON\n" + jsonStr);
        ActuatorData actuatorData = gson.fromJson(pretty(jsonStr),ActuatorData.class);
        System.out.println(actuatorData.getValue());
        return actuatorData;
    }  
    
    public boolean writeActuatorDataToFile(String logData){
        /*
        Converts ActuatorData to JSON and writes to the filesystem
        */
        this.actuatorDatalogger.info(logData);
        return true;
    }

    public static String pretty(String str){
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        //get a JsonParser
        JsonParser jp = new JsonParser();
        //get the parsed jsonString
        JsonElement je = jp.parse(str);
        //get the pretty printed JSON string
        String jsonStrPretty = gsonBuilder.toJson(je);
        return jsonStrPretty;
    }
    
}