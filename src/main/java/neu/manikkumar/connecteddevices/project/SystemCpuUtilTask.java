package neu.manikkumar.connecteddevices.project;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import com.sun.management.OperatingSystemMXBean;

import org.eclipse.paho.client.mqttv3.MqttException;

import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import java.lang.Thread;

/** 
 * Threaded class that sends out the current system CPU being used
 * by the IOT-Gateway
 */
public class SystemCpuUtilTask {

	//Using operatingSystemBean for getting the CPU usage
	private OperatingSystemMXBean osBean;

	//Interval for sleeping 
	private int interval;
	
	//Ubidots client
	private UbidotsClientConnector ubidots;

	//SensorData Object
	private SensorData sensorData;

	//Logger
	private final static Logger LOGGER = Logger.getLogger("MqttLogger");
	
	/**
	 * Constructor 
	 */
	public SystemCpuUtilTask(int intervalTime){
		this.interval = intervalTime;
		this.ubidots = new UbidotsClientConnector(false);
		this.sensorData = new SensorData();
	}

	/**
	 * Method which returns the current average system load.
	 * @throws InterruptedException
	 */
	public float retcpu() throws InterruptedException {
		while(true){
			//Get current CPU USage
			this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

			//Add to the sensoData
			sensorData.addValue((float)osBean.getSystemLoadAverage());
			LOGGER.info("Sending Gateway CPU Details to ubidots");
			
			//Send to ubidots
			this.ubidots.sendGateCpuPayload(sensorData);
			//Sleep for specified interval in seconds
			Thread.sleep(interval*1000);
		}	
	}
}
