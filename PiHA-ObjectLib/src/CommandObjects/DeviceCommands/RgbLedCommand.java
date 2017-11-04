package CommandObjects.DeviceCommands;

import CommandObjects.Command;

/**
 * Provides an implementation of Command, used to define the specifications for an RgbLedCommand.
 */
public class RgbLedCommand extends Command
{
    public enum RgbLedCommandType
    {
        TOGGLE_RED, TOGGLE_GREEN, TOGGLE_BLUE, TOGGLE_MAGENTA, TOGGLE_YELLOW, TOGGLE_CYAN, TOGGLE_WHITE, BLINK_RED,
        BLINK_GREEN, BLINK_BLUE, BLINK_MAGENTA, BLINK_YELLOW, BLINK_CYAN, BLINK_WHITE, SPECTRUM_CYCLE
    }

    private RgbLedCommandType commandType;

    public RgbLedCommand(RgbLedCommandType ct)
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
        this.commandType = (RgbLedCommandType) ct;
    }
}
