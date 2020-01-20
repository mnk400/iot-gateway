package neu.manikkumar.connecteddevices.labs.module01;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class SystemCpuUtilTask {

	private OperatingSystemMXBean osBean;
	
	SystemCpuUtilTask() {
		this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}
	public float retcpu() {
		return (float)osBean.getSystemLoadAverage();	
	}
}
