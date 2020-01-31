package neu.manikkumar.connecteddevices.labs.common;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.configuration.*;
import java.util.logging.Logger;

public class ConfigUtil{
    private final static Logger LOGGER = Logger.getLogger("ConfigUtilLogger");
    private static String defaultConfigPath = "config/ConnectedDevicesConfig.props";
    private boolean configFileLoaded = false;
    private HierarchicalINIConfiguration parser;
    private String configFile;

    public ConfigUtil() {
        this.configFile = defaultConfigPath;
        try {
			this.loadConfigData();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
    }

    public String getValue(String section, String key) {
        if (this.configFileLoaded = true){        
            String strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if ( strRead != null) {
                return strRead;
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

    public int getIntegerValue(String section, String key) {
        if (this.configFileLoaded = true){        
            String strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if ( strRead != null) {
                return Integer.parseInt(strRead);
            }
            else {
                LOGGER.info("Invalid Key/Value par");
                return 0;
            }
        }
        else{
            LOGGER.info("File not loaded");
            return 0;
        }
    }

    public boolean getBooleanValue(String section, String key) {
        if (this.configFileLoaded = true){        
            String strRead = String.valueOf(this.parser.getSection(section).getString(key));
            if (strRead != null) {
                if (strRead == "True") {return true;}
                else {return false;}
            }   
            else {
                LOGGER.info("Invalid Key/Value par");
                return false;
            }
        }
        else{
            LOGGER.info("File not loaded");
            return false;
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
            return true;
        }
        return false;
    }

}