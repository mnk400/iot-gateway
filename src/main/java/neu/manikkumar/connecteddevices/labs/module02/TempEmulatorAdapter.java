package neu.manikkumar.connecteddevices.labs.module02;

public class TempEmulatorAdapter extends Thread{

    private int sleeptime = 1000;
    private int looptime = 1;
    public static boolean enableTempEmulatorAdapter = false;
    public static boolean runCheck = false;
    
    public TempEmulatorAdapter(int sleeptime, int looptime) {
        this.sleeptime = sleeptime*1000;
        this.looptime = looptime;

    }
    
    public void run() {
        TempSensorEmulatorTask emulator = new TempSensorEmulatorTask();
        runCheck = false;
        int i = 0;
        if ((enableTempEmulatorAdapter == true) && (this.sleeptime >= 0)){
            while (i<this.looptime) {
  
                emulator.generateData();
                try {
                    Thread.sleep(this.sleeptime);
                    runCheck = true;
			    } catch (InterruptedException e) {
                    e.printStackTrace();
                    runCheck = false;
                }
                i++;
            }
        }
    }
}