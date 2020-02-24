package neu.manikkumar.connecteddevices.common;

import neu.manikkumar.connecteddevices.common.PersistenceUtil;
import neu.manikkumar.connecteddevices.common.ActuatorData;
import neu.manikkumar.connecteddevices.common.SensorData;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ActuatorDataListenerTest
 */
public class SensorDataListenerTest {

    /**
	 * @throws java.lang.Exception
	 */
    SensorData sensorData;
    ActuatorData actuatorData;
    SensorDataListener listener;
	@Before
	public void setUp() throws Exception
	{
        /*
        Setting up resources
        */
        //Init
        this.listener = new SensorDataListener("squishypi.lan");
        //setting up sensorData
        this.sensorData = new SensorData();
		this.sensorData.setName("TestSensor");
        this.sensorData.addValue(10);
        //Expected actuatorData
        this.actuatorData = new ActuatorData();
        this.actuatorData.setCommand("Increase");
        this.actuatorData.setName("TestSensor");
        this.actuatorData.setValue("UPARROW");
    }

    @After
    public void tearDown() throws Exception
    {
        /*
        Getting rid of resources
        */
        this.listener = null;
        this.sensorData = null;
        this.actuatorData = null;
    }

    @Test
    public void testCreateActuatorData()
    {
        /*
        Testing if testCreateAactuatorData creates the correct actuatorData when given some sensorData
        */
        if(this.listener.pUtil.connected = true){
            ActuatorData tempActuatorData = this.listener.createActuatorData(this.sensorData);
            //Following fields must match between the defined actuatorData and the one we get
            assertEquals(this.actuatorData.getCommand(),tempActuatorData.getCommand());
            assertEquals(this.actuatorData.getValue(),tempActuatorData.getValue());
            assertEquals(this.actuatorData.getName(),tempActuatorData.getName());
        }
    }

}    