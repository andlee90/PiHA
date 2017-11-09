package Controllers;

import CommandObjects.DeviceCommands.RelayModuleCommand;
import Database.Helper;
import DeviceObjects.Device;
import DeviceObjects.RelayModule;
import Managers.MainManager;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

/**
 * Implementation of DeviceController for controlling a single Relay Module. Commented-out lines are commented to
 * facilitate testing outside of a Raspbian environment (In this case, no GPIO pins are available and an exception is
 * thrown when these lines are called). Uncomment before testing on a Pi.
 */
public class RelayModuleController extends DeviceController
{
    private volatile RelayModule device;    // The device being controlled.
    private GpioController gpio;            // The controller for the device.
    private GpioPinDigitalOutput pin;       // The pin to which the device in connected.

    RelayModuleController(Device device)
    {
        this.device = (RelayModule) device;

        this.gpio = GpioFactory.getInstance();
        this.pin = this.gpio.provisionDigitalOutputPin(MainManager.getGpioPin(device.getDevicePins().get(0)),
                device.getDeviceName(), PinState.LOW);
        this.pin.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public boolean isAvailable()
    {
        return this.device.getDeviceStatus().equals(Device.DeviceStatus.AVAILABLE);
    }

    @Override
    public Enum issueCommand(Enum commandType) throws InterruptedException
    {
        if (commandType == RelayModuleCommand.RelayModuleCommandType.TOGGLE)
        {
            if (device.getDeviceMode() == RelayModule.RelayModuleMode.ON)
            {
                pin.toggle();
                device.setDeviceMode(RelayModule.RelayModuleMode.OFF);
            }

            else if (device.getDeviceMode() == RelayModule.RelayModuleMode.OFF)
            {
                pin.toggle();
                device.setDeviceMode(RelayModule.RelayModuleMode.ON);
            }

            System.out.println("> [" + MainManager.getDate() + "] "
                    + device.getDeviceName() + " on "
                    + device.getDevicePins().get(0) + " is "
                    + device.getDeviceMode());
        }

        Helper.updateDevice(device.getDeviceId(),
                device.getDeviceName(),
                device.getDeviceType().toString(),
                device.getDeviceStatus().toString(),
                device.getDeviceMode().toString());

        return device.getDeviceMode();
    }
}
