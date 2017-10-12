package DeviceObjects;

import UserObjects.User;

import java.util.ArrayList;

/**
 * Provides an implementation of Device, used to define the specifications for a single-channel Relay Module.
 */
public class RelayModule extends Device
{
    public enum RelayModuleMode
    {
        ON, OFF
    }

    private int deviceId;
    private ArrayList<Integer> devicePins;
    private String deviceName;
    private DeviceStatus deviceStatus;
    private RelayModuleMode deviceMode;
    private User deviceUser;

    public RelayModule(int id, ArrayList<Integer> pins, String name, DeviceStatus ds, RelayModuleMode dm)
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
        return DeviceType.RELAY_MOD;
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
        this.deviceMode = (RelayModuleMode) mode;
    }

    @Override
    public void setCurrentDeviceUser(User user)
    {
        this.deviceUser = user;
    }

    /**
     * Convert a string to an RelayModuleMode.
     * @param s the string to be converted.
     * @return the corresponding RelayModuleMode.
     */
    public static RelayModuleMode getRelayModuleModeFromString(String s)
    {
        switch (s)
        {
            case "ON":
                return RelayModuleMode.ON;
            case "OFF":
                return RelayModuleMode.OFF;
            default:
                return RelayModuleMode.OFF;
        }
    }
}
