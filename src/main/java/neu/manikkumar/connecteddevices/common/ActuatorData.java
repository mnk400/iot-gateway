package neu.manikkumar.connecteddevices.common;

/**
 * ActuatorData
 */
public class ActuatorData {

     protected String command;
     protected String name;
     protected Object value;

     public ActuatorData(){
        /*
         * Constructor
         * Initialize the variables as 'Not Set' and 0.0.
         */
        this.command = "Not Set";
        this.name = "Not Set";
        this.value = null;
     }
    
    public String getCommand(){
        /*
        Function to return the current set command for the actuator.
        */
        return this.command;
    }

    public String getName(){
        /*
        Function the name of current ActuatorData instance if set.
        */
        return this.name;
    }

    public Object getValue(){
        /*
        Function to return the current set value of the instance.
        */
        return this.value;
    }    

    public boolean setCommand(String input_command){
        /*
        Function to set or change the current set command for the actuator.
        */
        this.command = input_command;
        return true;
    }    

    public boolean setName(String name_param){
        /*
        Function to set the name of the instance.
        */
        this.name = name_param;
        return true;
    }    
    
    public Boolean setValue(Object value_param){
        /*
        Function to set the value of the actuator.
        */
        this.value = value_param;
        return true;
    }    
    
}
