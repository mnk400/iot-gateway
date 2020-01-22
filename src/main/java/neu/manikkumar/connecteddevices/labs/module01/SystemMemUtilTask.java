package neu.manikkumar.connecteddevices.labs.module01;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class SystemMemUtilTask {

	public float retmem() {
		MemoryMXBean memBean 	= ManagementFactory.getMemoryMXBean();
		MemoryUsage heap 		= memBean.getHeapMemoryUsage();
		MemoryUsage nonheap		= memBean.getNonHeapMemoryUsage();
		
		double heapUtil = ((double) heap.getUsed() / heap.getMax()) * 100;
		double nonheapUtil = ((double) nonheap.getUsed() / nonheap.getMax()) * 100;
		
		if (nonheapUtil < 0) {
			nonheapUtil = 0.0;
		}
		if (heapUtil < 0) {
			nonheapUtil = 0.0;
		}
		//System.out.println(heap.getUsed());
		//System.out.println(heap.getMax());
		return (float)heapUtil;
	}
}
