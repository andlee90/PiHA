package Controllers;

import DeviceObjects.Device;

/**
 * Implementation of DeviceController for controlling a Stepper Motor. Commented-out lines are commented to facilitate
 * testing outside of a Raspbian environment (In this case, no GPIO pins are available and an exception is thrown when
 * these lines are called). Uncomment before testing on a Pi.
 */
public class StepperMotorController extends DeviceController
{
    StepperMotorController(Device device)
    {

    }

    @Override
    public boolean isAvailable()
    {
        return false;
    }

    @Override
    public Enum issueCommand(Enum commandType) throws InterruptedException
    {
        return null;
    }
}