package CommandObjects.ListCommands;

import CommandObjects.Command;

/**
 * Provides an implementation of Command, used to define the specifications for a RoleList command.
 */
public class RoleListCommand extends Command
{
    public enum RoleListCommandType
    {
        ADD_ROLE, REMOVE_ROLE
    }

    private RoleListCommandType commandType;

    public RoleListCommand(RoleListCommandType ct)
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
        this.commandType = (RoleListCommandType) ct;
    }
}
