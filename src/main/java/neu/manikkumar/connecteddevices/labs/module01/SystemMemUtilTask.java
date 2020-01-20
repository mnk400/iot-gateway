package neu.manikkumar.connecteddevices.labs.module01;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class SystemMemUtilTask {

	public float retmem() {
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = memBean.getHeapMemoryUsage();
		MemoryUsage nonheap = memBean.getNonHeapMemoryUsage();
		MemoryUsage memUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		
		double heapUtil = ((double) heap.getUsed() / heap.getMax()) * 100;
		double nonheapUtil = ((double) nonheap.getUsed() / heap.getMax()) * 100;
		//System.out.println(heap.getUsed());
		//System.out.println(heap.getMax());
		return (float)heapUtil;
	}
}
