package neu.manikkumar.connecteddevices.labs.module07;
import neu.manikkumar.connecteddevices.common.PersistenceUtil;
import neu.manikkumar.connecteddevices.common.SensorDataListener;

import java.net.SocketException;
import java.util.logging.Logger;

public class GatewayDataManager {
    /**
     * GatewayDataManager
     * Class responsible for running the listener threads that listen for MQTT inputs 
     */
    private final static Logger LOGGER = Logger.getLogger("DataMLogger");

    //Enable settings 
    public static boolean enableCoAP = false;
    public static Boolean enableRedis = false;

    //Creating a Thread for CoAP Server
    Thread CoAPThread;

    public PersistenceUtil persistenceUtil;
    public SensorDataListener listener;
    

    public GatewayDataManager(String IP){
        /*
         Constructor
         */

        //Initializing the CoAPServer Thread
        this.CoAPThread = new Thread( new Runnable(){
			public void run() {
                //Initing the server variable
				CoAPServer server;
				try {
                    server = new CoAPServer();
                    //Running server
                    server.serverStarter();
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}

        });

        this.persistenceUtil = new PersistenceUtil(IP);
        this.listener = new SensorDataListener(IP);

    } 

    public boolean run(){
        /**
        * Run method to run the threads 
        */

        //Checking if coAP enabled
        if (enableCoAP == true){
            //Running the thread
            this.CoAPThread.start();
        }
        else{
            LOGGER.info("CoAPServer");
        }

        //Checking if Redis is enabled
        if(enableRedis == true && this.listener.connected == true && this.persistenceUtil.connected == true){
            //Running the listener, which sends actuatorData instances on the redis
            this.persistenceUtil.registerSensorDataDbmsListener(this.listener);
        }
        else{
            LOGGER.info("Listener did not run");
        }

        return true;
    }
}