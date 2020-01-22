package neu.manikkumar.connecteddevices.labs.module01;

import java.util.logging.Logger;

public class SystemPerformanceAdapter extends Thread {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private SystemCpuUtilTask cpu_stats;
	private SystemMemUtilTask mem_stats;
	
	private int sleeptime;
	private int loopcount;
	
	public SystemPerformanceAdapter(int param,int loop_param) {
		this.cpu_stats = new SystemCpuUtilTask();	
		this.mem_stats = new SystemMemUtilTask();
		this.sleeptime = param*1000;
		this.loopcount = loop_param;
		
	}

	public void run() {
		int i=0;
		float cpu_val;
		float mem_val;
		while(i < loopcount) {
			i++;
			cpu_val = cpu_stats.retcpu();
			mem_val = mem_stats.retmem();
			
			LOGGER.info("CPU Average Load:" + cpu_val);
			LOGGER.info("Heap Memory:" + mem_val);
			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
