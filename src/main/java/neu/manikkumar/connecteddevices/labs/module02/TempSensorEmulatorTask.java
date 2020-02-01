package neu.manikkumar.connecteddevices.labs.module02;

import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.common.SensorData;

public class TempSensorEmulatorTask{
    /*
     *Class responsible for generating Random temperature data
     *Has a generateData function
     *and overall acts like an emulator that can generate random strings of data and store in SensorData objects
     */
    private final static Logger LOGGER = Logger.getLogger("ConfigUtilLogger");
    private final static int minRand = 0;                                           //Minimum Random Value
    private final static int maxRand = 30;                                          //Maximum Random Value
    private final static int threshold = 5;                                         //Threshold for notification        
    private final static String topic = "IOT - gateway";                            //Email subject
    public SmtpClientConnector smtp;                                                //SMTPclient object
    private SensorData sensor;

    public TempSensorEmulatorTask() {
        /*
         *Constructor
         *which initializes the sensor object and the SMTPCLient object
         */
        this.sensor = new SensorData();
        this.smtp = new SmtpClientConnector();
    }

    public boolean generateData() {
        /*
         *Method to generate new random data.
         *Random data is then pushed to the SensorData class
         *Method also checks if newly produced data values are in the differential range of 5 from the average value
         */
        float temp = (float)(Math.random()*(maxRand - minRand)+1);
        sensor.addValue(temp);
        //Generating string containing all the data points for sending email and logging 
        String msgString = this.generateString();
        LOGGER.info(msgString);
        //checking if current value deviates from the average +- threshold, if yes then send an email   
        if ((temp > sensor.getAverageValue() + threshold) || (temp < sensor.getAverageValue() - threshold) ) { this.sendNotification("Temperature out of bounds" + msgString);}
        //returning true if method worked properly
        return true;
    }

    public boolean sendNotification(String mailMSG) {
        /*
         *Returns an instance of the the class SensorData
         */ 
        LOGGER.info("Sending Mail");
        this.smtp.sendMail("IOT - Gateway", mailMSG);
        return true;
    }
    
    public SensorData getSensorData() {
        /*
         *Simple method to call the SMTP connector to publish a message
         */ 
        return this.sensor;
    }
    
    public String generateString() {
        /*
         *Generate the string to be logged and then passed in the SMTP message
         */ 
        String msgString;
        msgString  = "\nTemperature";
        msgString += "\n\tTime : " + String.valueOf(sensor.timestamp);
        msgString += "\n\tCurrent : " + String.valueOf(sensor.getCurrentValue());
        msgString += "\n\tAverage : " + String.valueOf(sensor.getAverageValue());
        msgString += "\n\tSamples : " + String.valueOf(sensor.getTotal());
        msgString += "\n\tMin : " + String.valueOf(sensor.getMinValue());
        msgString += "\n\tMax : " + String.valueOf(sensor.getCurrentValue());
        return msgString;
    }
}