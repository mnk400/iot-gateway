package neu.manikkumar.connecteddevices.labs.module01;

import java.util.logging.Logger;

public class SystemPerformanceAdapter extends Thread {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private SystemCpuUtilTask cpu_stats;
	private SystemMemUtilTask mem_stats;
	private boolean run_sucess = false; 
	
	private int sleeptime = 2;
	private int loopcount = 10;

	public boolean enableSystemPerformanceAdapter = false;
	
	/**
	 * Getter method to return a success var which is only set if the thread ran
	 */
	public boolean checkSuccess(){
		return this.run_sucess;
	}

	/**
	 * Constructor
	 */
	public SystemPerformanceAdapter(int param,int loop_param) {
		LOGGER.info("Initializing SystemPerformanceAdapter Thread");
		this.cpu_stats = new SystemCpuUtilTask();	
		this.mem_stats = new SystemMemUtilTask();
		this.sleeptime = param*1000;
		this.loopcount = loop_param;
		
	}

	/**
	 * Run method which runs the thread, retrieves the values from the UtilTasks and logs them
	 */
	public void run() {
		int i=0;
		float cpu_val;
		float mem_val;
		if( this.enableSystemPerformanceAdapter == true ){
			while(i < loopcount) {
				i++;
				cpu_val = cpu_stats.retcpu();
				mem_val = mem_stats.retmem();
			
				LOGGER.info("CPU Average Load:" + cpu_val);
				LOGGER.info("Heap Memory:" + mem_val);
				try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.run_sucess = true;
		}
		else {
			this.run_sucess = false;
		}
	}
}
