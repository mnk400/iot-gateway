package neu.manikkumar.connecteddevices.project;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import com.sun.management.OperatingSystemMXBean;

import org.eclipse.paho.client.mqttv3.MqttException;

import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import java.lang.Thread;

public class SystemCpuUtilTask {

	private OperatingSystemMXBean osBean;
	int interval;
	UbidotsClientConnector ubidots;

	private final static Logger LOGGER = Logger.getLogger("MqttLogger");
	/**
	 * Constructor 
	 * @throws MqttException
	 */
	public SystemCpuUtilTask(int intervalTime){
		this.interval = intervalTime;
		this.ubidots = new UbidotsClientConnector(false);
	}
	/**
	 * Method which returns the current average system load.
	 * @throws InterruptedException
	 */
	public float retcpu() throws InterruptedException {
		while(true){
			this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			SensorData sensorData = new SensorData();
			sensorData.addValue((float)osBean.getSystemLoadAverage());
			LOGGER.info("Sending Gateway CPU Details to ubidots");
			this.ubidots.sendGateCpuPayload(sensorData);
			Thread.sleep(interval*1000);
		}	
	}
}
