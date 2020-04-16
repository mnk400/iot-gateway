package neu.manikkumar.connecteddevices.project;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.joda.time.Interval;

import neu.manikkumar.connecteddevices.project.SystemCpuUtilTask;
import neu.manikkumar.connecteddevices.project.SystemMemUtilTask;
import java.lang.Thread;

public class SystemPerformanceAdapter {
    Thread cpu;
    Thread mem;

    static int intervalTime;

    public SystemPerformanceAdapter(int interval){
       intervalTime = interval;
    }

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

        this.cpu.start();
        this.mem.start();
    }    
    
}