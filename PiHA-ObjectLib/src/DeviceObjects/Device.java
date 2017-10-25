package DeviceObjects;

import UserObjects.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Provides an abstract/generic template for Device objects.
 */
public abstract class Device implements Serializable
{
    public enum DeviceType
    {
        LED, RGB_LED, RELAY_MOD, STEP_MOTOR
    }

    public enum DeviceStatus
    {
        AVAILABLE, IN_USE
    }

    public abstract int getDeviceId();
    public abstract ArrayList<Integer> getDevicePins();
    public abstract String getDeviceName();
    public abstract DeviceType getDeviceType();
    public abstract DeviceStatus getDeviceStatus();
    public abstract Enum getDeviceMode();
    public abstract User getCurrentDeviceUser();
    public abstract int getHostServerId();

    public abstract void setDevicePins(ArrayList<Integer> pins);
    public abstract void setDeviceName(String name);
    public abstract void setDeviceStatus(DeviceStatus status);
    public abstract void setDeviceMode(Enum mode);
    public abstract void setCurrentDeviceUser(User user);
    public abstract void setHostServerId(int id);

    /**
     * Convert a device type string to a DeviceType.
     * @param s the string to be converted.
     * @return the corresponding DeviceType.
     */
    public static DeviceStatus getDeviceStatusFromString(String s)
    {
        DeviceStatus ds = null;

        if (s.equals("AVAILABLE"))
        {
            ds = DeviceStatus.AVAILABLE;
        }
        else if (s.equals("IN_USE"))
        {
            ds = DeviceStatus.IN_USE;
        }
        return ds;
    }
}
