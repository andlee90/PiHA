package CommandObjects.DeviceCommands;

import CommandObjects.Command;

/**
 * Provides an implementation of Command, used to define the specifications for a StepperMotorCommand.
 */
public class StepperMotorCommand extends Command
{
    public enum StepperMotorCommandType
    {
        CLOSE_UP, OPEN_HALF_UP, OPEN, OPEN_HALF_DOWN, CLOSE_DOWN
    }

    private StepperMotorCommandType commandType;

    public StepperMotorCommand(StepperMotorCommandType ct)
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
        this.commandType = (StepperMotorCommandType) ct;
    }
}
