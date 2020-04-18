package neu.manikkumar.connecteddevices.project;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;

import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;
import java.lang.Thread;

/** 
 * Threaded class that sends out the current system memory being used
 * by the IOT-Gateway
 */
public class SystemMemUtilTask {

	//Using memoryMXBean to get the memory details
	private MemoryMXBean memBean;
	private MemoryUsage heap;
	private MemoryUsage nonheap;

	//Ubidots Client
	private UbidotsClientConnector ubidots;

	//Sleep timer
	private int interval;

	//SensorData Object
	private SensorData sensorData;
	
	//Logger
	private final static Logger LOGGER = Logger.getLogger("MqttLogger");

	/**
	 * Constructor 
	 * @throws MqttException
	 */
	public SystemMemUtilTask(int intervalTime){
		this.interval = intervalTime;
		this.ubidots = new UbidotsClientConnector(false);
		this.sensorData = new SensorData();
	}

	/**
	 * Method which can return currently used heap and nonheap memory 
	 * @throws InterruptedException
	 */
	public float retmem() throws InterruptedException {
		while(true){
			//Reading the memory from the memoryBean
			this.memBean 	= ManagementFactory.getMemoryMXBean();
			this.heap 		= memBean.getHeapMemoryUsage();
			this.nonheap		= memBean.getNonHeapMemoryUsage();
			//Calculating heap and nonHeap usage
			double heapUtil = ((double) this.heap.getUsed() / this.heap.getMax()) * 100;
			double nonheapUtil = ((double) this.nonheap.getUsed() / this.nonheap.getMax()) * 100;
			
			if (nonheapUtil < 0) {
				nonheapUtil = 0.0;
			}
			if (heapUtil < 0) {
				nonheapUtil = 0.0;
			}
			//adding to sensorData
			this.sensorData.addValue((float)heapUtil * 10);
			LOGGER.info("Sending Gateway Memory Details to ubidots");
			
			//Sending to ubidots
			this.ubidots.sendGateMemPayload(sensorData);
			//sleeping for specified time interval in seconds
			Thread.sleep(interval*1000);
		}
	}
}
