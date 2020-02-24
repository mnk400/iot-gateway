/**
 * 
 */
package neu.manikkumar.connecteddevices.common;
import neu.manikkumar.connecteddevices.common.ActuatorData;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActuatorDataTest
{
	// setup methods
	ActuatorData actuatorData;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		/*
		Setting up resources
		*/
		//Init actuatorData instance
		this.actuatorData = new ActuatorData();
		actuatorData.setCommand("TESTCOMMAND");
		actuatorData.setName("TESTNAME");
		actuatorData.setValue("TESTVALUE");
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
		this.actuatorData = null;
	}
	
	// test methods
	
	@Test
	public void testGetCommand()
	{
		/*
		  Check if getComamnd is working as intended
		  Should return the "TESTCOMMAND"
		*/
		assertEquals("TESTCOMMAND", this.actuatorData.getCommand());
	}

	@Test
	public void testSetCommand()
	{
		/*
		  Check if setComamnd is working as intended
		  sets command and then gets it to confirm if it's right.
		*/
		this.actuatorData.setCommand("TESTTESTCOMMAND");
		assertEquals("TESTTESTCOMMAND", this.actuatorData.getCommand());
	}

	@Test
	public void testGetName()
	{
		/*
		  Check if getName is working as intended
		  Should return the "TESTNAME"
		*/
		assertEquals("TESTNAME", this.actuatorData.getName());
	}

	@Test
	public void testSetName()
	{
		/*
		  Check if setComamnd is working as intended
		  sets command and then gets it to confirm if it's right.
		*/
		this.actuatorData.setName("TESTTESTNAME");
		assertEquals("TESTTESTNAME", this.actuatorData.getName());
	}

	@Test
	public void testGetValue()
	{
		/*
		  Check if getName is working as intended
		  Should return the "TESTVALUE"
		*/
		assertEquals("TESTVALUE", this.actuatorData.getValue());
	}

	@Test
	public void testSetValue()
	{
		/*
		  Check if setComamnd is working as intended
		  sets command and then gets it to confirm if it's right.
		*/
		this.actuatorData.setValue(6768);
		assertEquals(6768, this.actuatorData.getValue());
	}

	
}
