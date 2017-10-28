package DeviceObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds a serialized list of Device objects for transport across a socket.
 */
public class DeviceList implements Serializable
{
    private int serverId;
    private ArrayList<Device> devices;

    public DeviceList()
    {
        this.serverId = -1;
        this.devices = new ArrayList<>();
    }

    public int getServerId()
    {
        return this.serverId;
    }

    public ArrayList<Device> getDevices()
    {
        return devices;
    }

    public void setServerId(int id)
    {
        this.serverId = id;
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
