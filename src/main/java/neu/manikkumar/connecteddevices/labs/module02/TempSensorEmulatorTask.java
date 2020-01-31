package neu.manikkumar.connecteddevices.labs.module02;

import java.util.logging.Logger;
import neu.manikkumar.connecteddevices.labs.common.SensorData;

public class TempSensorEmulatorTask{
    private final static Logger LOGGER = Logger.getLogger("ConfigUtilLogger");
    private final static int minRand = 0;
    private final static int maxRand = 30;
    private final static int threshold = 5;
    private final static String topic = "IOT - gateway";

    private SmtpClientConnector smtp;

    private SensorData sensor;
    public TempSensorEmulatorTask() {
        this.sensor = new SensorData();
        this.smtp = new SmtpClientConnector();
    }

    public boolean generateData() {
        float temp = (float)(Math.random()*(maxRand - minRand)+1);
        sensor.addValue(temp);
        String msgString = this.generateString();
        LOGGER.info(msgString);
        if ((temp > sensor.getAverageValue() + threshold) || (temp < sensor.getAverageValue() - threshold) ) { this.sendNotification("Temperature out of bounds" + msgString);}
        return true;
    }

    public void sendNotification(String mailMSG) {
        LOGGER.info("Sending Mail");
        this.smtp.sendMail("IOT - Gateway", mailMSG);
    }
    
    public SensorData getSensorData() {
        return this.sensor;
    }
    
    public String generateString() {
        String msgString;
        msgString  = "\nTemperature";
        //msgString += "\n\tTime : " + self.sense_d.timestamp
        msgString += "\n\tCurrent : " + String.valueOf(sensor.getCurrentValue());
        msgString += "\n\tAverage : " + String.valueOf(sensor.getAverageValue());
        msgString += "\n\tSamples : " + String.valueOf(sensor.getTotal());
        msgString += "\n\tMin : " + String.valueOf(sensor.getMinValue());
        msgString += "\n\tMax : " + String.valueOf(sensor.getCurrentValue());
        return msgString;
    }
}