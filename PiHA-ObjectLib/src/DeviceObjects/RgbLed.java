package DeviceObjects;

import UserObjects.User;

import java.util.ArrayList;

/**
 * Provides an implementation of Device, used to define the specifications for an Rgb Led.
 */
public class RgbLed extends Device
{
    public enum RgbLedMode
    {
        ON_RED, ON_BLUE, ON_GREEN, ON_MAGENTA, ON_CYAN, ON_YELLOW, ON_WHITE, BLINKING_RED, BLINKING_BLUE,
        BLINKING_GREEN, BLINKING_MAGENTA, BLINKING_CYAN, BLINKING_YELLOW, BLINKING_WHITE, OFF
    }

    private int deviceId;
    private ArrayList<Integer> devicePins;
    private String deviceName;
    private DeviceStatus deviceStatus;
    private RgbLedMode deviceMode;
    private User deviceUser;
    private int hostServerId;

    public RgbLed(int id, ArrayList<Integer> pins, String name, DeviceStatus ds, RgbLedMode dm)
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
        return DeviceType.RGB_LED;
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
        this.deviceMode = (RgbLedMode) mode;
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
     * Convert a string to an RgbLedMode.
     * @param s the string to be converted.
     * @return the corresponding RgbLedMode.
     */
    public static RgbLedMode getRgbLedModeFromString(String s)
    {
        if(s.equals("ON_RED"))
        {
            return RgbLedMode.ON_RED;
        }
        else if(s.equals("ON_BLUE"))
        {
            return RgbLedMode.ON_BLUE;
        }
        else if(s.equals("ON_GREEN"))
        {
            return RgbLedMode.ON_GREEN;
        }
        else if(s.equals("ON_MAGENTA"))
        {
            return RgbLedMode.ON_MAGENTA;
        }
        else if(s.equals("ON_CYAN"))
        {
            return RgbLedMode.ON_CYAN;
        }
        else if(s.equals("ON_YELLOW"))
        {
            return RgbLedMode.ON_YELLOW;
        }
        else if(s.equals("ON_WHITE"))
        {
            return RgbLedMode.ON_WHITE;
        }
        else if(s.equals("BLINKING_RED"))
        {
            return RgbLedMode.BLINKING_RED;
        }
        else if(s.equals("BLINKING_BLUE"))
        {
            return RgbLedMode.BLINKING_BLUE;
        }
        else if(s.equals("BLINKING_GREEN"))
        {
            return RgbLedMode.BLINKING_GREEN;
        }
        else if(s.equals("BLINKING_MAGENTA"))
        {
            return RgbLedMode.BLINKING_MAGENTA;
        }
        else if(s.equals("BLINKING_CYAN"))
        {
            return RgbLedMode.BLINKING_CYAN;
        }
        else if(s.equals("BLINKING_YELLOW"))
        {
            return RgbLedMode.BLINKING_YELLOW;
        }
        else if(s.equals("BLINKING_WHITE"))
        {
            return RgbLedMode.BLINKING_WHITE;
        }
        else
        {
            return RgbLedMode.OFF;
        }
    }
}
