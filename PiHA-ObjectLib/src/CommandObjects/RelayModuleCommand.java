package CommandObjects;

/**
 * Provides an implementation of Command, used to define the specifications for a RelayModuleCommand.
 */
public class RelayModuleCommand extends Command
{
    public enum RelayModuleCommandType
    {
        TOGGLE
    }

    private RelayModuleCommand.RelayModuleCommandType commandType;

    public RelayModuleCommand(RelayModuleCommand.RelayModuleCommandType ct)
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
        this.commandType = (RelayModuleCommandType) ct;
    }
}
