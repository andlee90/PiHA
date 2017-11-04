package Controllers;

import CommandObjects.DeviceCommands.LedCommand;
import Database.Helper;
import DeviceObjects.Device;
import DeviceObjects.Led;
import Managers.MainManager;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

/**
 * Implementation of DeviceController for controlling a single LED. Commented-out lines are commented to facilitate
 * testing outside of a Raspbian environment (In this case, no GPIO pins are available and an exception is thrown when
 * these lines are called). Uncomment before testing on a Pi.
 */
public class LedController extends DeviceController
{
    private volatile Led device;            // The device being controlled.
    private GpioController gpio;            // The controller for the device.
    private GpioPinDigitalOutput pin;       // The pin to which the device in connected.

    LedController(Device device)
    {
        this.device = (Led) device;

        gpio = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(MainManager.getGpioPin(device.getDevicePins().get(0)),
                device.getDeviceName(), PinState.LOW);
        pin.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public boolean isAvailable()
    {
        return device.getDeviceStatus().equals(Device.DeviceStatus.AVAILABLE);
    }

    @Override
    public Enum issueCommand(Enum ct) throws InterruptedException
    {
        if (ct == LedCommand.LedCommandType.TOGGLE)
        {
            if (device.getDeviceMode() == Led.LedMode.BLINKING)
            {
                // If the device is blinking, first turn it off
                pin.blink(0);
                pin.setState(PinState.LOW);
                device.setDeviceMode(Led.LedMode.OFF);
            }

            else if (device.getDeviceMode() == Led.LedMode.ON)
            {
                pin.toggle();
                device.setDeviceMode(Led.LedMode.OFF);
            }

            else if (device.getDeviceMode() == Led.LedMode.OFF)
            {
                pin.toggle();
                device.setDeviceMode(Led.LedMode.ON);
            }

            System.out.println("> [" + MainManager.getDate() + "] "
                    + device.getDeviceName() + " on "
                    + device.getDevicePins().get(0) + " is "
                    + device.getDeviceMode());
        }
        else if (ct == LedCommand.LedCommandType.BLINK)
        {
            device.setDeviceMode(Led.LedMode.BLINKING);

            pin.blink(100);

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