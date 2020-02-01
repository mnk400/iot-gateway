package neu.manikkumar.connecteddevices.common;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.configuration.*;
import java.util.logging.Logger;

public class ConfigUtil{
    /*
     * This class is responsible for reading and parsing the configuration files.
     * Apache.commons.confiruration is used to parse the files.
     */
    private final static Logger LOGGER = Logger.getLogger("ConfigUtilLogger");                   // A logger object
    private static String defaultConfigPath = "config/ConnectedDevicesConfig.props";             // Static string defining the default path to the configuration file
    public boolean configFileLoaded = false;                                                     // Public variable to check if the configuration file has been loaded
    private HierarchicalINIConfiguration parser;                                                 // The parses object responsible for reading from the file
    private String configFile;                                                                   // path to the the configuration path
    private String strRead;                                                                      // Class variable strRead in which a value is read from the config file. 
                                                                                                 // Is a class variable only because that helps with debugging without adding too many checkpoints.

    public ConfigUtil() {
        /*
         * Constructor
         * Responsible for calling the load function to load configuration data from the file.
         * This constructor loads the default specified path as the config file.
         */
        this.configFile = defaultConfigPath;
        try {
			this.loadConfigData();
		} catch (ConfigurationException e) {
            //Trying to catch any exception that could occur with file configurations
			e.printStackTrace();                 
		}
    }

    public ConfigUtil(String testValidCfgFile) {
        /*
         * Overridden Constructor
         * This constructor allows you to override the default constructors and load a different config file
         */
        this.configFile = testValidCfgFile;
        try {
			this.loadConfigData();
		} catch (ConfigurationException e) {
            //Trying to catch any exception that could occur with file configurations
			e.printStackTrace();
		}
	}

	public String getValue(String section, String key) {
        /*
         * GetValue method
         * Responsible for returning String values from the configuration file
         * Returns the retrieved value if form of a String
         */
        if (this.configFileLoaded = true){        
            // parser.getSection helps us retrieve the key for the specified section
            // String.valueOf calculates the string value of retrieved data
            this.strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if ( this.strRead != null) {
                // Returns the retrieved String
                return this.strRead;
            }
            else {
                // Logs if asked for a wrong key/value pair and then returns a null
                LOGGER.info("Invalid Key/Value par");
                return null;
            }
        }
        else{
            // Returns a null if the file is not loaded. 
            LOGGER.info("File not loaded");
            return null;
        }    
    }

    public Integer getIntegerValue(String section, String key) {
         /*
         * GetIntegerValue method
         * Responsible for returning Integer values from the configuration file
         * Returns the retrieved value if it is a Integer, otherwise returns a null
         */
        if (this.configFileLoaded = true){        
            // parser.getSection helps us retrieve the key for the specified section
            // String.valueOf calculates the string value of retrieved data
            this.strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if ( this.strRead != null) {
                try {
                    // Checks if the retrieved value can be converted to an Integer else will return a null
                    return Integer.parseInt(strRead);
                } catch (Exception e) {
                    // Returns null if the value can not be converted to an Integer.
                    return null;
                }
            }
            else {
                // Logs if asked for a wrong key/value pair and then returns a null
                LOGGER.info("Invalid Key/Value par");
                return null;
            }
        }
        else{
            // Returns a null if the file is not loaded.
            LOGGER.info("File not loaded");
            return null;
        }
    }

    public Boolean getBooleanValue(String section, String key) {
        /*
         * GetBooleanValue method
         * Responsible for returning Boolean values from the configuration file
         * Returns the retrieved value if it is a Boolean, otherwise returns a null
         */
        if (this.configFileLoaded = true){   
            // parser.getSection helps us retrieve the key for the specified section
            // String.valueOf calculates the string value of retrieved data     
            this.strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if (this.strRead != null) {
                // Checks if the retrieved value can be converted to a Boolean else will return a null
                if (strRead.equals("True")) {
                    //return true if the read data was true
                    return true;
                }
                else if (strRead.equals("Fase")){
                    //return false if the read data was false
                    return false;
                }     
                else {
                    //return null if the data could not be converted into a boolean
                    return null;
                }
            }   
            else {
                // Logs if asked for a wrong key/value pair and then returns a null
                LOGGER.info("Invalid Key/Value par");
                return null;
            }
        }
        else{
            // Returns a null if the file is not loaded.
            LOGGER.info("File not loaded");
            return null;
        }
    }

    public boolean hasConfigData() {
        /*
         * HasConfigData
         * Method checks if the loaded file has any kind of data in it
         * If yes, returns a true, else false.
         */
        if(this.configFileLoaded) {
            // Converting to a set so we can have unique section values accross the file.
            // Decreases complexity by reducing loop passes
            Set<String> sections = this.parser.getSections();
            if(sections.size() != 0){
                for(String section: sections){
                    // Creating an iterator to iterate through the keys in every section
                    Iterator itr = this.parser.getSection(section).getKeys();
                    // Checking if any unique value exists by checking if ANY value exists at all
                    // Returning a true if it does
                    if (itr.hasNext()!=false) {
                        return true;
                    }
                }
                // Returning a false if there is no Data in config file
                LOGGER.info("No Data in config file");
                return false;
            }

        }
        // Returning a false if no config file is loaded.
        LOGGER.info("Config File not Loaded");
        return false;
    }

    public boolean loadConfigData() throws ConfigurationException {
        /*
         * LoadConfigData
         * This method is responsible for loading the configuration file into the parser
         */

        // checking if the file exists using java.io.file
        File configFile = new File(this.configFile).getAbsoluteFile();
        if (configFile.exists()){
            // Loading the file into the parser if it exists then logging and then returning a true
            this.parser = new HierarchicalINIConfiguration(this.configFile);
            this.configFileLoaded = true;
            LOGGER.info("Config File loaded");
            return true;
        }
        // Returning a false if the file doesn't exists and logging the event
        LOGGER.info("Config File could not be loaded");
        return false;
    }

}