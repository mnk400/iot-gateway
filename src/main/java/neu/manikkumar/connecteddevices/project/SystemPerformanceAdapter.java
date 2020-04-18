package neu.manikkumar.connecteddevices.project;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.joda.time.Interval;

import neu.manikkumar.connecteddevices.project.SystemCpuUtilTask;
import neu.manikkumar.connecteddevices.project.SystemMemUtilTask;
import java.lang.Thread;

/**
 * SystemPerformanceAdapter
 * class responsible for running two threads 
 * for reading system information and sending it to ubidots
 */
public class SystemPerformanceAdapter {
    
    //Creaiting the two threads
    Thread cpu;
    Thread mem;

    //Sleep timer
    static int intervalTime;

    /**
     * Constructor
     * @param interval
     */
    public SystemPerformanceAdapter(int interval){
       intervalTime = interval;
    }

    /**
     * Run method to run the two threads
     */
    public void run(){
        this.cpu =new Thread( new Runnable(){
            SystemCpuUtilTask cpu;
			public void run() {
                try {
                    cpu = new SystemCpuUtilTask(intervalTime);
                    cpu.retcpu();
				} catch (Exception e) {

					e.printStackTrace();
				}
                
            }
        });

        this.mem =new Thread( new Runnable(){
            SystemMemUtilTask mem;
			public void run() {
                try {
                    mem = new SystemMemUtilTask(intervalTime);
                    mem.retmem();
				} catch (Exception e) {

					e.printStackTrace();
				}
                
            }
        });
        //Starting the two threads
        this.cpu.start();
        this.mem.start();
    }    
    
}