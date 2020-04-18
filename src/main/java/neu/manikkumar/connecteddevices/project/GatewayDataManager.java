package neu.manikkumar.connecteddevices.project;
import neu.manikkumar.connecteddevices.common.PersistenceUtil;
import neu.manikkumar.connecteddevices.common.SensorDataListener;
import neu.manikkumar.connecteddevices.project.UbidotsClientConnector;

import java.net.SocketException;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import neu.manikkumar.connecteddevices.project.SystemPerformanceAdapter;

import java.lang.Thread;
public class GatewayDataManager {
    /**
     * GatewayDataManager
     * Class responsible for running the listener threads that listen for MQTT inputs 
     */
    private final static Logger LOGGER = Logger.getLogger("DataMLogger");

    //Enable settings 
    public static boolean enableCoAP = false;
    public static boolean enableSysPerf = false;
    public static boolean enableMqttListener = false;

    //Ubidots
    UbidotsClientConnector ubidots;

    //Creating a Thread for CoAP Server
    Thread CoAPThread;
    
    //Gateway performance adapter
    public SystemPerformanceAdapter sysInfo;

    /**
     * Constructor
     */
    public GatewayDataManager(){
        
        //SystemInfo
        sysInfo = new SystemPerformanceAdapter(20);

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
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        });
        
        try {
			this.ubidots = new UbidotsClientConnector();
		} catch (MqttException e) {
			e.printStackTrace();
		}
    } 

    /**
     * Method to run the threads
     * @return
     * @throws InterruptedException
     */
    public boolean run() throws InterruptedException{

        //SystemPerformance Thread
        if (enableSysPerf == true){
            //Running the thread
            this.sysInfo.run();
        }

        //Checking if coAP enabled
        if (enableCoAP == true){
            //Running the thread
            this.CoAPThread.start();
        }

        //Checkinf if MQTTListener is enabled
        if (enableMqttListener == true){
            //Running the thread
            this.ubidots.ubidotsMqttListener();
        }

        
        return true;
    }
}