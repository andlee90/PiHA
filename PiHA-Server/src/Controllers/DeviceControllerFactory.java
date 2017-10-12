package Controllers;

import DeviceObjects.*;

import java.util.Hashtable;
import java.util.Map;

/**
 * Factory for producing a single instance of a device's controller object. Controller objects are created only once for
 * a particular device and are stored in a HashTable. They are then reused when needed later on.
 */
public class DeviceControllerFactory
{
    private volatile static Hashtable<DeviceController, Integer> controllerTable = new Hashtable<>();

    private static DeviceController createLedController(Device device)
    {
        LedController ledController = new LedController(device);
        controllerTable.put(ledController, device.getDeviceId());

        return ledController;
    }

    private static DeviceController createRgbLedController(Device device)
    {
        RgbLedController rgbLedController = new RgbLedController(device);
        controllerTable.put(rgbLedController, device.getDeviceId());

        return rgbLedController;
    }

    private static DeviceController createRelayModuleController(Device device)
    {
        RelayModuleController relayModuleController = new RelayModuleController(device);
        controllerTable.put(relayModuleController, device.getDeviceId());

        return relayModuleController;
    }

    private static DeviceController createStepperMotorController(Device device)
    {
        StepperMotorController stepperMotorController = new StepperMotorController(device);
        controllerTable.put(stepperMotorController, device.getDeviceId());

        return stepperMotorController;
    }

    public static synchronized DeviceController getDeviceController(Device device)
    {
        DeviceController dc = null;

        if(controllerTable.contains(device.getDeviceId()))
        {
            for (Map.Entry entry : controllerTable.entrySet())
            {
                if (entry.getValue().equals(device.getDeviceId()))
                {
                    dc = (DeviceController) entry.getKey();
                }
            }
            return dc;
        }

        else if(device instanceof Led)
        {
            return createLedController(device);
        }
        else if(device instanceof RgbLed)
        {
            return createRgbLedController(device);
        }
        else if(device instanceof RelayModule)
        {
            return createRelayModuleController(device);
        }
        else if(device instanceof StepperMotor)
        {
            return createStepperMotorController(device);
        }
        else
        {
            return null;
        }
    }
}
