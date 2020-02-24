/**
 * 
 */
package neu.manikkumar.connecteddevices.labs;

import static org.junit.Assert.assertEquals;
 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.manikkumar.connecteddevices.labs.module05.GatewayDataManager;
public class Module05Test
{
	// setup methods
	
	/**
	 * @throws java.lang.Exception
	 */
	GatewayDataManager dataManager;
	@Before
	public void setUp() throws Exception
	{
		/*
        Setting up resources
		*/
		//Init dataManager
		this.dataManager = new GatewayDataManager();
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
		this.dataManager = null;
	}
	
	// test methods
	
	@Test
	public void testRun()
	{
		/*
        Testing if the run function runs as intended 
		*/
		if(this.dataManager.listener.config.configFileLoaded = true){
			this.dataManager.enable = false;
			//This should return a false
			assertEquals(false, this.dataManager.run());
		}
	}
	
}
