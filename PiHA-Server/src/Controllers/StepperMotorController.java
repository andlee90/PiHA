package Controllers;

import CommandObjects.DeviceCommands.StepperMotorCommand;
import Database.Helper;
import DeviceObjects.Device;
import DeviceObjects.StepperMotor;
import Managers.MainManager;
import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.impl.GpioStepperMotorComponent;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Implementation of DeviceController for controlling a Stepper Motor. Commented-out lines are commented to facilitate
 * testing outside of a Raspbian environment (In this case, no GPIO pins are available and an exception is thrown when
 * these lines are called). Uncomment before testing on a Pi.
 */
public class StepperMotorController extends DeviceController
{
    private volatile StepperMotor device;      // The device being controlled.
    private GpioController gpio;               // The controller for the device.
    private GpioStepperMotorComponent motor;    // The component.


    StepperMotorController(Device device)
    {
        this.device = (StepperMotor) device;

        int a = this.device.getDevicePins().get(0);
        int b = this.device.getDevicePins().get(1);
        int c = this.device.getDevicePins().get(2);
        int d = this.device.getDevicePins().get(3);

        this.gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput[] pins = {
                this.gpio.provisionDigitalOutputPin(MainManager.getGpioPin(a), PinState.LOW),
                this.gpio.provisionDigitalOutputPin(MainManager.getGpioPin(b), PinState.LOW),
                this.gpio.provisionDigitalOutputPin(MainManager.getGpioPin(c), PinState.LOW),
                this.gpio.provisionDigitalOutputPin(MainManager.getGpioPin(d), PinState.LOW)};

        this.gpio.setShutdownOptions(true, PinState.LOW, pins);
        this.motor = new GpioStepperMotorComponent(pins);

        byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;

        this.motor.setStepInterval(2);
        this.motor.setStepSequence(single_step_sequence);
        this.motor.setStepsPerRevolution(2038);
    }

    @Override
    public boolean isAvailable()
    {
        return this.device.getDeviceStatus().equals(Device.DeviceStatus.AVAILABLE);
    }


    @Override
    public Enum issueCommand(Enum ct) throws InterruptedException
    {
        StepperMotorCommand.StepperMotorCommandType stepperMotorCommandType =
                (StepperMotorCommand.StepperMotorCommandType) ct;

        Hashtable<StepperMotor.StepperMotorMode, Integer> positionMap = new Hashtable<>();
        positionMap.put(StepperMotor.StepperMotorMode.CLOSED_UP, 0);
        positionMap.put(StepperMotor.StepperMotorMode.HALF_UP, 4);
        positionMap.put(StepperMotor.StepperMotorMode.OPEN, 7);
        positionMap.put(StepperMotor.StepperMotorMode.HALF_DOWN, 11);
        positionMap.put(StepperMotor.StepperMotorMode.CLOSED_DOWN, 14);

        int currentPosition = positionMap.get(device.getDeviceMode());

        switch(stepperMotorCommandType)
        {
            case CLOSE_UP:
                motor.rotate(0-currentPosition);
                device.setDeviceMode(StepperMotor.StepperMotorMode.CLOSED_UP);
                break;

            case OPEN_HALF_UP:
                if(currentPosition > 4) motor.rotate(4-currentPosition);
                else if(currentPosition < 4) motor.rotate(4+currentPosition);
                device.setDeviceMode(StepperMotor.StepperMotorMode.HALF_UP);
                break;

            case OPEN:
                if(currentPosition > 7) motor.rotate(7-currentPosition);
                else if(currentPosition < 7) motor.rotate(7-currentPosition);
                device.setDeviceMode(StepperMotor.StepperMotorMode.OPEN);
                break;

            case OPEN_HALF_DOWN:
                if(currentPosition > 11) motor.rotate(11-currentPosition);
                else if(currentPosition < 11) motor.rotate(11-currentPosition);
                device.setDeviceMode(StepperMotor.StepperMotorMode.HALF_DOWN);
                break;

            case CLOSE_DOWN:
                motor.rotate(14-currentPosition);
                device.setDeviceMode(StepperMotor.StepperMotorMode.CLOSED_DOWN);
                break;
        }

        System.out.println("> [" + MainManager.getDate() + "] "
                + device.getDeviceName() + " on "
                + device.getDevicePins() + " is "
                + device.getDeviceMode());

        Helper.updateDevice(device.getDeviceId(),
                device.getDeviceName(),
                Device.DeviceType.STEP_MOTOR.toString(),
                device.getDeviceStatus().toString(),
                device.getDeviceMode().toString());

        return device.getDeviceMode();
    }
}
