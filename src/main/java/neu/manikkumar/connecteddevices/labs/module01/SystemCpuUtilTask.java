package neu.manikkumar.connecteddevices.labs.module01;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class SystemCpuUtilTask {

	private OperatingSystemMXBean osBean;
	
	/**
	 * Constructor 
	 */
	public SystemCpuUtilTask() {
		this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}
	/**
	 * Method which returns the current average system load.
	 */
	public float retcpu() {
		return (float)osBean.getSystemLoadAverage();	
	}
}
