package CommandObjects.ListCommands;

import CommandObjects.Command;

/**
 * Provides an implementation of Command, used to define the specifications for a UserList command.
 */
public class UserListCommand extends Command
{
    public enum UserListCommandType
    {
        ADD_USER, REMOVE_USER
    }

    private UserListCommandType commandType;

    public UserListCommand(UserListCommandType ct)
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
        this.commandType = (UserListCommandType) ct;
    }
}
