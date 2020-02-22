package neu.manikkumar.connecteddevices.labs.module05;
import neu.manikkumar.connecteddevices.common.DataUtil;
import neu.manikkumar.connecteddevices.common.SensorData;
import neu.manikkumar.connecteddevices.common.ActuatorData;
/**
 * GatewayHandlerApp
 */

public class GatewayHandlerApp {
    public static void main(String[] args) throws Exception {
		
        DataUtil data = new DataUtil();
        SensorData sense = new SensorData();
        sense.addValue(10);
        String str1 = data.toJsonFromSensorData(sense);
        System.out.println(str1);

        ActuatorData actuator = new ActuatorData();
        actuator.setCommand("Test");
        actuator.setName("LOLOLOL");
        actuator.setValue(69696);
        String str2 = data.toJsonFromActuatorData(actuator);
        data.toActuatorDataFromJson(str2);
        System.out.println(str2);
	}
}