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
        CLOSED_UP, HALF_UP, OPEN, HALF_DOWN, CLOSED_DOWN
    }

    private int deviceId;
    private ArrayList<Integer> devicePins;
    private String deviceName;
    private DeviceStatus deviceStatus;
    private StepperMotorMode deviceMode;
    private User deviceUser;
    private int hostServerId;

    public StepperMotor(int id, ArrayList<Integer> pins, String name, DeviceStatus ds, StepperMotorMode dm)
    {
        this.deviceId = id;
        this.devicePins = pins;
        this.deviceName = name;
        this.deviceStatus = ds;
        this.deviceMode = dm;
        this.deviceUser = null;
        this.hostServerId = -1;
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
    public int getHostServerId()
    {
        return hostServerId;
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

    @Override
    public void setHostServerId(int id)
    {
        this.hostServerId = id;
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
            case "CLOSED_UP":
                return StepperMotorMode.CLOSED_UP;
            case "HALF_UP":
                return StepperMotorMode.HALF_UP;
            case "OPEN":
                return StepperMotorMode.OPEN;
            case "HALF_DOWN":
                return StepperMotorMode.HALF_DOWN;
            case "CLOSED_DOWN":
                return StepperMotorMode.CLOSED_DOWN;
            default:
                return StepperMotorMode.CLOSED_UP;
        }
    }
}
