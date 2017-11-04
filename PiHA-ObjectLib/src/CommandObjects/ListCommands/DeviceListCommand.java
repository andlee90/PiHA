package CommandObjects.ListCommands;

import CommandObjects.Command;

/**
 * Provides an implementation of Command, used to define the specifications for a DeviceList command.
 */
public class DeviceListCommand extends Command
{
    public enum DeviceListCommandType
    {
        ADD_DEVICE, REMOVE_DEVICE
    }

    private DeviceListCommandType commandType;

    public DeviceListCommand(DeviceListCommandType ct)
    {
        this.commandType = ct;
    }

    @Override
    public Enum getCommandType()
    {
        return this.commandType;
    }

    @Override
    public void setCommandType(Enum ct)
    {
        this.commandType = (DeviceListCommandType)ct;
    }
}
