package Controllers;

import CommandObjects.DeviceCommands.RgbLedCommand;
import Database.Helper;
import DeviceObjects.Device;
import DeviceObjects.RgbLed;
import Managers.MainManager;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

/**
 * Implementation of DeviceController for controlling a single RGBLED. Commented-out lines are commented to facilitate
 * testing outside of a Raspbian environment (In this case, no GPIO pins are available and an exception is thrown when
 * these lines are called). Uncomment before testing on a Pi.
 */
public class RgbLedController extends DeviceController
{
    private volatile RgbLed device;            // The device being controlled.
    private GpioController gpio;               // The controller for the device.
    private GpioPinDigitalOutput redPin;       // The first pin to which the device in connected.
    private GpioPinDigitalOutput greenPin;     // The second pin to which the device in connected.
    private GpioPinDigitalOutput bluePin;      // The third pin to which the device in connected.


    RgbLedController(Device device)
    {
        this.device = (RgbLed) device;

        int r = this.device.getDevicePins().get(0);
        int g = this.device.getDevicePins().get(1);
        int b = this.device.getDevicePins().get(2);

        gpio = GpioFactory.getInstance();

        redPin = gpio.provisionDigitalOutputPin(MainManager.getGpioPin(r), this.device.getDeviceName(), PinState.LOW);
        redPin.setShutdownOptions(true, PinState.LOW);

        greenPin = gpio.provisionDigitalOutputPin(MainManager.getGpioPin(g), this.device.getDeviceName(), PinState.LOW);
        greenPin.setShutdownOptions(true, PinState.LOW);

        bluePin = gpio.provisionDigitalOutputPin(MainManager.getGpioPin(b), this.device.getDeviceName(), PinState.LOW);
        bluePin.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public boolean isAvailable()
    {
        return device.getDeviceStatus().equals(Device.DeviceStatus.AVAILABLE);
    }

    @Override
    public Enum issueCommand(Enum ct)
    {
        RgbLedCommand.RgbLedCommandType rgbLedCommandType = (RgbLedCommand.RgbLedCommandType)ct;

        switch (rgbLedCommandType)
        {
            case TOGGLE_RED:
                if (isBlinking((RgbLed.RgbLedMode)device.getDeviceMode())) {
                    // If the device is blinking, first turn it off
                    redPin.blink(0);
                    redPin.setState(PinState.LOW);
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } else if(device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                    redPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.ON_RED);
                } else {
                    redPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } break;

            case TOGGLE_GREEN:
                if (isBlinking((RgbLed.RgbLedMode)device.getDeviceMode())) {
                    // If the device is blinking, first turn it off
                    greenPin.blink(0);
                    greenPin.setState(PinState.LOW);
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } else if(device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                    greenPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.ON_GREEN);
                } else {
                    greenPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } break;

            case TOGGLE_BLUE:
                if (isBlinking((RgbLed.RgbLedMode)device.getDeviceMode())) {
                    // If the device is blinking, first turn it off
                    bluePin.blink(0);
                    bluePin.setState(PinState.LOW);
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } else if(device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                    bluePin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.ON_BLUE);
                } else {
                    bluePin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } break;

            case TOGGLE_MAGENTA:
                if (isBlinking((RgbLed.RgbLedMode)device.getDeviceMode())) {
                    // If the device is blinking, first turn it off
                    bluePin.blink(0);
                    redPin.blink(0);
                    bluePin.setState(PinState.LOW);
                    redPin.setState(PinState.LOW);
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } else if(device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                    bluePin.toggle();
                    redPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.ON_MAGENTA);
                } else {
                    bluePin.toggle();
                    redPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } break;

            case TOGGLE_YELLOW:
                if (isBlinking((RgbLed.RgbLedMode)device.getDeviceMode())) {
                    // If the device is blinking, first turn it off
                    redPin.blink(0);
                    greenPin.blink(0);
                    redPin.setState(PinState.LOW);
                    greenPin.setState(PinState.LOW);
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } else if(device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                    redPin.toggle();
                    greenPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.ON_YELLOW);
                } else {
                    redPin.toggle();
                    greenPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } break;

            case TOGGLE_CYAN:
                if (isBlinking((RgbLed.RgbLedMode)device.getDeviceMode())) {
                    // If the device is blinking, first turn it off
                    bluePin.blink(0);
                    greenPin.blink(0);
                    bluePin.setState(PinState.LOW);
                    greenPin.setState(PinState.LOW);
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } else if(device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                    bluePin.toggle();
                    greenPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.ON_CYAN);
                } else {
                    bluePin.toggle();
                    greenPin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } break;

            case TOGGLE_WHITE:
                if (isBlinking((RgbLed.RgbLedMode)device.getDeviceMode())) {
                    // If the device is blinking, first turn it off
                    redPin.blink(0);
                    greenPin.blink(0);
                    bluePin.blink(0);
                    redPin.setState(PinState.LOW);
                    greenPin.setState(PinState.LOW);
                    bluePin.setState(PinState.LOW);
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } else if(device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                    redPin.toggle();
                    greenPin.toggle();
                    bluePin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.ON_WHITE);
                } else {
                    redPin.toggle();
                    greenPin.toggle();
                    bluePin.toggle();
                    device.setDeviceMode(RgbLed.RgbLedMode.OFF);
                } break;

            case BLINK_RED:
                redPin.blink(100);
                device.setDeviceMode(RgbLed.RgbLedMode.BLINKING_RED);
                break;

            case BLINK_GREEN:
                greenPin.blink(100);
                device.setDeviceMode(RgbLed.RgbLedMode.BLINKING_GREEN);
                break;

            case BLINK_BLUE:
                bluePin.blink(100);
                device.setDeviceMode(RgbLed.RgbLedMode.BLINKING_BLUE);
                break;

            case BLINK_MAGENTA:
                redPin.blink(100);
                bluePin.blink(100);
                device.setDeviceMode(RgbLed.RgbLedMode.BLINKING_MAGENTA);
                break;

            case BLINK_YELLOW:
                redPin.blink(100);
                greenPin.blink(100);
                device.setDeviceMode(RgbLed.RgbLedMode.BLINKING_YELLOW);
                break;

            case BLINK_CYAN:
                greenPin.blink(100);
                bluePin.blink(100);
                device.setDeviceMode(RgbLed.RgbLedMode.BLINKING_CYAN);
                break;

            case BLINK_WHITE:
                redPin.blink(100);
                greenPin.blink(100);
                bluePin.blink(100);
                device.setDeviceMode(RgbLed.RgbLedMode.BLINKING_WHITE);
                break;
        }

        System.out.println("> [" + MainManager.getDate() + "] "
                + device.getDeviceName() + " on "
                + device.getDevicePins() + " is "
                + device.getDeviceMode());

        Helper.updateDevice(device.getDeviceId(),
                device.getDeviceName(),
                Device.DeviceType.RGB_LED.toString(),
                device.getDeviceStatus().toString(),
                device.getDeviceMode().toString());

        return device.getDeviceMode();
    }

    private boolean isBlinking(RgbLed.RgbLedMode m)
    {
        String mString = m.toString();
        if(mString.length() > 8 && mString.substring(0,8).equals("BLINKING"))
        {
            return true;
        }
        else return false;
    }
}