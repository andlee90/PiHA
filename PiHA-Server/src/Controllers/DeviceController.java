package Controllers;

/**
 * An abstract template for all device controller classes.
 */
public abstract class DeviceController
{
    /**
     * Notifies the user about whether or not the device is available for use.
     * @return true if the device is available, false if not.
     */
    public abstract boolean isAvailable();

    /**
     * Issues a command to the device.
     * @param commandType the type of command being issued.
     * @return the current state of the device.
     */
    public abstract Enum issueCommand(Enum commandType) throws InterruptedException;
}
