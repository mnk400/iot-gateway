package neu.manikkumar.connecteddevices.labs.module02;

public class TempEmulatorAdapter extends Thread{
    /*
     *This class is responsible for running the temperature generation emulator
     *Has inputs like the sleeptimer and looplimit so we can control the running of iterations
     */

    //Creating a set of class variables like the sleeptime and looptime and assigning them default values
    private int sleeptime = 1000;
    private int looptime = 1;
    //static enableTempEmulatorAdapter to run the thread and generate Data
    public static boolean enableTempEmulatorAdapter = false;    
    //static variable to check if run() ran
    public static boolean runCheck = false;
    
    public TempEmulatorAdapter(int sleeptime, int looptime) {
        /*
         * Constructor
         * which sets the sleep timer for the thread, and a looplimit if needed.
         */
        this.sleeptime = sleeptime*1000;
        this.looptime = looptime;

    }

    public TempEmulatorAdapter(){
        /*
         * Empty overridden Constructor to load the default files
         */
    }
    
    public void run() {
        /*
         *This method runs the emulation if enableTempEmulatorAdapter is set to True.
         *Method calls the "generateData" method from the tempEmulatorAdapter which is
         *responsible for emulator a temperature Data generator.
         */
        TempSensorEmulatorTask emulator = new TempSensorEmulatorTask();
        runCheck = false; //initialize runCheck to false in case it was true previously
        int i = 0;
        //  run if sleeptime is greator than 0 and enable is true
        if ((enableTempEmulatorAdapter == true) && (this.sleeptime >= 0)){
            while (i<this.looptime) {
                // call generateData
                emulator.generateData();
                try {
                    Thread.sleep(this.sleeptime);
                    //set runcheck to true if the program works fine
                    runCheck = true;
			    } catch (InterruptedException e) {
                    //set runCheck to false if something goes wrong
                    e.printStackTrace();
                    runCheck = false;
                }
                i++;
            }
        }
    }
}