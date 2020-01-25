package neu.manikkumar.connecteddevices.labs.module01;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class SystemMemUtilTask {

	MemoryMXBean memBean;
	MemoryUsage heap;
	MemoryUsage nonheap;
	
	/**
	 * Constructor 
	 */
	public SystemMemUtilTask() {
		memBean 	= ManagementFactory.getMemoryMXBean();
		heap 		= memBean.getHeapMemoryUsage();
		nonheap		= memBean.getNonHeapMemoryUsage();
	}
	/**
	 * Method which can return currently used heap and nonheap memory 
	 */
	public float retmem() {
	
		double heapUtil = ((double) this.heap.getUsed() / this.heap.getMax()) * 100;
		System.out.println(heapUtil);
		double nonheapUtil = ((double) this.nonheap.getUsed() / this.nonheap.getMax()) * 100;
		
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
