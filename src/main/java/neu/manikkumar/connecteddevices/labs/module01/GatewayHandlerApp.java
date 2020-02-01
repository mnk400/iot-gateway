package neu.manikkumar.connecteddevices.labs.module01;

import neu.manikkumar.connecteddevices.labs.module02.TempEmulatorAdapter;

public class GatewayHandlerApp {

	public static void main(String[] args) throws Exception {
		/**
		* Creating a thread for the Module01
		* enableSystemPerformanceAdapter should be enabled for the thread to be run
	    */
		SystemPerformanceAdapter sysapp = new SystemPerformanceAdapter(3,12);
		sysapp.enableSystemPerformanceAdapter = false;
		sysapp.start();

		/**
		* Creating a thread for the Module02
		* enableTempEmulatorAdapter should be enabled for the thread to be run
		*/
		TempEmulatorAdapter tempAdapter = new TempEmulatorAdapter(2,10);
		TempEmulatorAdapter.enableTempEmulatorAdapter = true;
		tempAdapter.start();

	}

}
