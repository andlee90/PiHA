package CommandObjects;

/**
 * Provides an implementation of Command, used to define the specifications for an LedCommand.
 */
public class LedCommand extends Command
{
    public enum LedCommandType
    {
        TOGGLE, BLINK
    }

    private LedCommandType commandType;

    public LedCommand(LedCommandType ct)
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
        this.commandType = (LedCommandType) ct;
    }
}
