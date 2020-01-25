package neu.manikkumar.connecteddevices.labs.module01;

public class GatewayHandlerApp {

	public static void main(String[] args) throws InterruptedException {
		/**
		* Creating a thread for the Module01
		* enableSystemPerformanceAdapter should be enabled for the thread to be run
	    */
		SystemPerformanceAdapter sysapp = new SystemPerformanceAdapter(3,12);
		sysapp.enableSystemPerformanceAdapter = true;
		sysapp.start();
	}

}
