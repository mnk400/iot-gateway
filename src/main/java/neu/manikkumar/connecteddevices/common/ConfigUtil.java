package neu.manikkumar.connecteddevices.common;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.configuration.*;
import java.util.logging.Logger;

public class ConfigUtil{
    private final static Logger LOGGER = Logger.getLogger("ConfigUtilLogger");
    private static String defaultConfigPath = "config/ConnectedDevicesConfig.props";
    public boolean configFileLoaded = false;
    private HierarchicalINIConfiguration parser;
    private String configFile;
    private String strRead;

    public ConfigUtil() {
        this.configFile = defaultConfigPath;
        try {
			this.loadConfigData();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
    }

    public ConfigUtil(String testValidCfgFile) {
        this.configFile = testValidCfgFile;
        try {
			this.loadConfigData();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public String getValue(String section, String key) {
        if (this.configFileLoaded = true){        
            this.strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if ( this.strRead != null) {
                return this.strRead;
            }
            else {
                this.strRead = null;
                LOGGER.info("Invalid Key/Value par");
                return this.strRead;
            }
        }
        else{
            LOGGER.info("File not loaded");
            return this.strRead;
        }    
    }

    public Integer getIntegerValue(String section, String key) {
        if (this.configFileLoaded = true){        
            this.strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if ( this.strRead != null) {
                try {
                    return Integer.parseInt(strRead);
                } catch (Exception e) {
                    return null;
                }
            }
            else {
                LOGGER.info("Invalid Key/Value par");
                return null;
            }
        }
        else{
            LOGGER.info("File not loaded");
            return null;
        }
    }

    public Boolean getBooleanValue(String section, String key) {
        if (this.configFileLoaded = true){        
            this.strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if (this.strRead != null) {
                if (strRead.equals("True")) {
                    return true;
                }
                else if (strRead.equals("Fase")){
                    return false;
                }     
                else {
                    return null;
                }
            }   
            else {
                LOGGER.info("Invalid Key/Value par");
                return null;
            }
        }
        else{
            LOGGER.info("File not loaded");
            return null;
        }
    }

    public boolean hasConfigData() {
        if(this.configFileLoaded) {
            Set<String> sections = this.parser.getSections();
            if(sections.size() != 0){
                for(String section: sections){
                    Iterator itr = this.parser.getSection(section).getKeys();

                    if (itr.hasNext()!=false) {
                        return true;
                    }
                }
                LOGGER.info("No Data in config file");
                return false;
            }

        }
        LOGGER.info("Config File not Loaded");
        return false;
    }

    public boolean loadConfigData() throws ConfigurationException {
        File configFile = new File(this.configFile).getAbsoluteFile();
        if (configFile.exists()){
            this.parser = new HierarchicalINIConfiguration(this.configFile);
            this.configFileLoaded = true;
            LOGGER.info("Config File loaded");
            return true;
        }
        LOGGER.info("Config File could not be loaded");
        return false;
    }

}