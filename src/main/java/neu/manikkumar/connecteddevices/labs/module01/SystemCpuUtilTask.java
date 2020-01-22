package neu.manikkumar.connecteddevices.labs.module01;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class SystemCpuUtilTask {

	private OperatingSystemMXBean osBean;
	
	public SystemCpuUtilTask() {
		this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}
	public float retcpu() {
		return (float)osBean.getSystemLoadAverage();	
	}
}
