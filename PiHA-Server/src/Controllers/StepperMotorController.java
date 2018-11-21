package Controllers;

import CommandObjects.DeviceCommands.StepperMotorCommand;
import Database.Helper;
import DeviceObjects.Device;
import DeviceObjects.StepperMotor;
import Managers.MainManager;
import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.impl.GpioStepperMotorComponent;

import java.util.Hashtable;

/**
 * Implementation of DeviceController for controlling a Stepper Motor. Issues each command to the Motor on a new Thread
 * to allow multiple Motors to run simultaneously. Commented-out lines are commented to facilitate
 * testing outside of a Raspbian environment (In this case, no GPIO pins are available and an exception is thrown when
 * these lines are called). Uncomment before testing on a Pi.
 */
public class StepperMotorController extends DeviceController
{
    private volatile StepperMotor device;      // The device being controlled.
    private GpioController gpio;               // The controller for the device.
    private GpioStepperMotorComponent motor;   // The component.
    private Thread thread;                     // The Thread within which each Motor command is executed.


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

        /* Too little torque */
        //byte[] single_step_sequence = new byte[4];
        //single_step_sequence[0] = (byte) 0b0001;
        //single_step_sequence[1] = (byte) 0b0010;
        //single_step_sequence[2] = (byte) 0b0100;
        //single_step_sequence[3] = (byte) 0b1000;

        byte[] double_step_sequence = new byte[4];
        double_step_sequence[0] = (byte) 0b0011;
        double_step_sequence[1] = (byte) 0b0110;
        double_step_sequence[2] = (byte) 0b1100;
        double_step_sequence[3] = (byte) 0b1001;

        this.motor.setStepInterval(2);
        this.motor.setStepSequence(double_step_sequence);
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
        positionMap.put(StepperMotor.StepperMotorMode.CLOSED_UP, 14);
        positionMap.put(StepperMotor.StepperMotorMode.HALF_UP, 11);
        positionMap.put(StepperMotor.StepperMotorMode.OPEN, 7);
        positionMap.put(StepperMotor.StepperMotorMode.HALF_DOWN, 4);
        positionMap.put(StepperMotor.StepperMotorMode.CLOSED_DOWN, 0);

        // Only issue the command if there are no commands currently being executed.
        int currentPosition = positionMap.get(device.getDeviceMode());

        if(thread == null || !thread.isAlive())
        {
            switch(stepperMotorCommandType)
            {
                case CLOSE_UP:
                    thread = new Thread(() -> motor.rotate(14 - currentPosition));
                    thread.start();
                    device.setDeviceMode(StepperMotor.StepperMotorMode.CLOSED_UP);
                    break;

                case OPEN_HALF_UP:
                    thread = new Thread(() -> {
                        if(currentPosition > 11) motor.rotate(11-currentPosition);
                        else if(currentPosition < 11) motor.rotate(11-currentPosition);
                    });
                    thread.start();
                    device.setDeviceMode(StepperMotor.StepperMotorMode.HALF_UP);
                    break;

                case OPEN:
                    thread = new Thread(() -> {
                        if(currentPosition > 7) motor.rotate(7-currentPosition);
                        else if(currentPosition < 7) motor.rotate(7-currentPosition);
                    });
                    thread.start();
                    device.setDeviceMode(StepperMotor.StepperMotorMode.OPEN);
                    break;

                case OPEN_HALF_DOWN:
                    thread = new Thread(() -> {
                        if(currentPosition > 4) motor.rotate(4-currentPosition);
                        else if(currentPosition < 4) motor.rotate(4+currentPosition);
                    });
                    thread.start();
                    device.setDeviceMode(StepperMotor.StepperMotorMode.HALF_DOWN);
                    break;

                case CLOSE_DOWN:
                    thread = new Thread(() -> motor.rotate(0-currentPosition));
                    thread.start();
                    device.setDeviceMode(StepperMotor.StepperMotorMode.CLOSED_DOWN);
                    break;
            }

            updateDb();
        }

        else
        {
            System.out.println("> [" + MainManager.getDate() + "] "
                    + "Error: " + device.getDeviceName() + " already in use");
        }

        return device.getDeviceMode();
    }

    private void updateDb()
    {
        System.out.println("> [" + MainManager.getDate() + "] "
                + device.getDeviceName() + " on "
                + device.getDevicePins() + " is "
                + device.getDeviceMode());

        Helper.updateDevice(device.getDeviceId(),
                device.getDeviceName(),
                Device.DeviceType.STEP_MOTOR.toString(),
                device.getDeviceStatus().toString(),
                device.getDeviceMode().toString());
    }
}
