package neu.manikkumar.connecteddevices.labs.module02;

public class TempEmulatorAdapter extends Thread{

    private int sleeptime = 1000;
    private int looptime = 1;
    public static boolean enableTempEmulatorAdapter = false;
    
    public TempEmulatorAdapter(int sleeptime, int looptime) {
        this.sleeptime = sleeptime*1000;
        this.looptime = looptime;
    }
    
    public void run() {
        TempSensorEmulatorTask emulator = new TempSensorEmulatorTask();
        int i = 0;
        if (enableTempEmulatorAdapter == true){
            while (i<looptime) {
                emulator.generateData();
                try {
				    Thread.sleep(this.sleeptime);
			    } catch (InterruptedException e) {
				    e.printStackTrace();
			    }
            }
        }
    }
}