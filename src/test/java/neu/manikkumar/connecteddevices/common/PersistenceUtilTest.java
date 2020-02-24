package neu.manikkumar.connecteddevices.common;

import neu.manikkumar.connecteddevices.common.PersistenceUtil;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.SensorData;

import static org.junit.Assert.assertEquals;
import java.io.*; 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * PersistenceUtilTest
 */
public class PersistenceUtilTest {
    /**
     * Test class for PersistenceUtilfunctionality 
     */

    /**
	 * @throws java.lang.Exception
	 */
    //Instances needed in this testfile
    PersistenceUtil pUtil;
    ActuatorData actuatorData;
    SensorData sensorData;
    //
    Boolean pipeAvoid = false;

	@Before
	public void setUp() throws Exception
	{
        /*
        Setting up resources required
        */

        //Creating instances of PersistenceUtil and actuatorData
        this.pUtil = new PersistenceUtil();
        //Adding data to actuatorData
        this.actuatorData = new ActuatorData();
        this.actuatorData.setName("TestActuator");
		this.actuatorData.setCommand("TestCommand");
        this.actuatorData.setValue(0.0);
        //SensorData instance filled with data
		this.sensorData = new SensorData();
		this.sensorData.setName("TestSensor");
        this.sensorData.addValue(10);
        //Setting a variable to avoid running in pipeline based on if config exists of not
        File f = new File("config/ConnectedDevicesConfig.props");
        if(f.exists()){
            this.pipeAvoid = false;
        }
        else{
            this.pipeAvoid = true;
        }
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
        /*
        Getting rid of resources
        */
    }
    
    @Test
    public void testWriteActuatorDataDbmsListener()
    {
        /*
        Testing if actuatorData written to redis properly
        */    
        //Should return a True if works
        //Only accepts an actuatorData instance
        if (this.pipeAvoid == false){
        assertEquals(true, pUtil.writeActuatorDataDbmsListener(this.actuatorData));
        }
    }

    @Test
    public void testWriteSensorDataDbmsListener()
    {
        /*
        Testing if sensorData written to redis properly
        */    
        //Should return a True if works
        //Only accepts a sensorData instance
        if (this.pipeAvoid == false){
        assertEquals(true, pUtil.writeSensorDataDbmsListener(this.sensorData));
        } 
    }
    
}