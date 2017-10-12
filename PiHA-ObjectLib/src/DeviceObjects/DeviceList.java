package DeviceObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds a serialized list of Device objects for transport across a socket.
 */
public class DeviceList implements Serializable
{
    private ArrayList<Device> devices;

    public DeviceList()
    {
        this.devices = new ArrayList<>();
    }

    public ArrayList<Device> getDevices()
    {
        return devices;
    }

    public void setDevices(ArrayList<Device> d)
    {
        devices = d;
    }

    public void addDevice(Device d)
    {
        devices.add(d);
    }

    public Device getDeviceById(int id)
    {
        return devices.get(id);
    }
}
