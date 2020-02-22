package neu.manikkumar.connecteddevices.common;

/**
 * DataUtil
 */
public class DataUtil {

    /*
     *Class to handle JSON data,
     *convert from JSON to sensorData or actuatorData instance
     *or convert sensorData or actuatorDat instance to JSON.
     */

    DataUtil(){
        /*
        Constructor
        */
    }

    public void toJsonFromSensorData(){
        /*
        Convert from JSON to SensorData instance
        */
    }

    public void toSensorDataFromJson(){
        /*
        Convert from SensorData instance to JSON
        */
    }  
    
    public void writeSensorDataToFile(SensorData SensorData){
        /*
        Converts SensorData to JSON and writes to the filesystem
        */
    }

    public void toJsonFromActuatorData(){
        /*
        Convert from JSON to ActuatorData instance
        */
    }

    public void toActuatorDataFromJson(){
        /*
        Convert from ActuatorData instance to JSON
        */
    }  
    
    public void writeActuatorDataToFile(SensorData SensorData){
        /*
        Converts ActuatorData to JSON and writes to the filesystem
        */
    }
}