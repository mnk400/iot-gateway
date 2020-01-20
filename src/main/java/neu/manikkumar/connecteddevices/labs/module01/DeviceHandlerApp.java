package neu.manikkumar.connecteddevices.labs.module01;

public class DeviceHandlerApp {

	public static void main(String[] args) throws InterruptedException {
		SystemPerformanceAdapter sysapp = new SystemPerformanceAdapter(3,12);
		sysapp.run();
	}

}
