package neu.manikkumar.connecteddevices.project;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;

import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import java.lang.Thread;

public class SystemMemUtilTask {

	MemoryMXBean memBean;
	MemoryUsage heap;
	MemoryUsage nonheap;
	UbidotsClientConnector ubidots;
	int interval;
	
	private final static Logger LOGGER = Logger.getLogger("MqttLogger");
	/**
	 * Constructor 
	 * @throws MqttException
	 */
	public SystemMemUtilTask(int intervalTime){
		this.interval = intervalTime;
		this.ubidots = new UbidotsClientConnector(false);
	}

	/**
	 * Method which can return currently used heap and nonheap memory 
	 * @throws InterruptedException
	 */
	public float retmem() throws InterruptedException {
		while(true){
			this.memBean 	= ManagementFactory.getMemoryMXBean();
			this.heap 		= memBean.getHeapMemoryUsage();
			this.nonheap		= memBean.getNonHeapMemoryUsage();
			double heapUtil = ((double) this.heap.getUsed() / this.heap.getMax()) * 100;
			double nonheapUtil = ((double) this.nonheap.getUsed() / this.nonheap.getMax()) * 100;
			
			if (nonheapUtil < 0) {
				nonheapUtil = 0.0;
			}
			if (heapUtil < 0) {
				nonheapUtil = 0.0;
			}

			SensorData sensorData = new SensorData();
			sensorData.addValue((float)heapUtil * 10);
			LOGGER.info("Sending Gateway Memory Details to ubidots");
			this.ubidots.sendGateMemPayload(sensorData);
			Thread.sleep(interval*1000);
		}
	}
}
