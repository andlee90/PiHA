package DeviceObjects;

import UserObjects.User;

import java.util.ArrayList;

/**
 * Provides an implementation of Device, used to define the specifications for an Led.
 */
public class Led extends Device
{
    public enum LedMode
    {
        ON, OFF, BLINKING
    }

    private int deviceId;
    private ArrayList<Integer> devicePins;
    private String deviceName;
    private DeviceStatus deviceStatus;
    private LedMode deviceMode;
    private User deviceUser;
    private int hostServerId;

    public Led(int id, ArrayList<Integer> pins, String name, DeviceStatus ds, LedMode dm)
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
        return DeviceType.LED;
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
        this.deviceMode = (LedMode) mode;
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
     * Convert a string to an LedMode.
     * @param s the string to be converted.
     * @return the corresponding LedMode.
     */
    public static LedMode getLedModeFromString(String s)
    {
        LedMode ls = null;

        if (s.equals("ON"))
        {
            ls = LedMode.ON;
        }
        else if (s.equals("OFF"))
        {
            ls = LedMode.OFF;
        }
        else if (s.equals("BLINKING"))
        {
            ls = LedMode.BLINKING;
        }
        return ls;
    }
}
