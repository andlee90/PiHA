package DeviceObjects;

import UserObjects.User;

import java.util.ArrayList;

/**
 * Provides an implementation of Device, used to define the specifications for an Stepper Motor.
 */
public class StepperMotor extends Device
{
    public enum StepperMotorMode
    {
        CLOCKWISE, COUNTER_CLOCKWISE, OFF
    }

    private int deviceId;
    private ArrayList<Integer> devicePins;
    private String deviceName;
    private DeviceStatus deviceStatus;
    private StepperMotorMode deviceMode;
    private User deviceUser;

    public StepperMotor(int id, ArrayList<Integer> pins, String name, DeviceStatus ds, StepperMotorMode dm)
    {
        this.deviceId = id;
        this.devicePins = pins;
        this.deviceName = name;
        this.deviceStatus = ds;
        this.deviceMode = dm;
        this.deviceUser = null;
    }

    @Override
    public int getDeviceId()
    {
        return this.deviceId;
    }

    @Override
    public ArrayList<Integer> getDevicePins()
    {
        return this.devicePins;
    }

    @Override
    public String getDeviceName()
    {
        return this.deviceName;
    }

    @Override
    public DeviceType getDeviceType()
    {
        return DeviceType.STEP_MOTOR;
    }

    @Override
    public DeviceStatus getDeviceStatus()
    {
        return this.deviceStatus;
    }

    @Override
    public Enum getDeviceMode()
    {
        return this.deviceMode;
    }

    @Override
    public User getCurrentDeviceUser()
    {
        return this.deviceUser;
    }

    @Override
    public void setDevicePins(ArrayList<Integer> pins)
    {
        this.devicePins = pins;
    }

    @Override
    public void setDeviceName(String name)
    {
        this.deviceName = name;
    }

    @Override
    public void setDeviceStatus(DeviceStatus status)
    {
        this.deviceStatus = status;
    }

    @Override
    public void setDeviceMode(Enum mode)
    {
        this.deviceMode = (StepperMotorMode) mode;
    }

    @Override
    public void setCurrentDeviceUser(User user)
    {
        this.deviceUser = user;
    }

    /**
     * Convert a string to an StepperMotorMode.
     * @param s the string to be converted.
     * @return the corresponding StepperMotorMode.
     */
    public static StepperMotorMode getStepperMotorModeFromString(String s)
    {
        switch (s)
        {
            case "CLOCKWISE":
                return StepperMotorMode.CLOCKWISE;
            case "COUNTER_CLOCKWISE":
                return StepperMotorMode.COUNTER_CLOCKWISE;
            case "OFF":
                return StepperMotorMode.OFF;
            default:
                return StepperMotorMode.OFF;
        }
    }
}
