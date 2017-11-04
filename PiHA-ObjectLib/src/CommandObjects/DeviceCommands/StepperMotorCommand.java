package CommandObjects.DeviceCommands;

import CommandObjects.Command;

/**
 * Provides an implementation of Command, used to define the specifications for a StepperMotorCommand.
 */
public class StepperMotorCommand extends Command
{
    public enum StepperMotorCommandType
    {
        CW_10P, CW_20P, CW_30P, CW_40P, CW_50P, CW_60P, CW_70P, CW_80P, CW_90P, CW_100P, CCW_10P, CCW_20P, CCW_30P,
        CCW_40P, CCW_50P, CCW_60P, CCW_70P, CCW_80P, CCW_90P, CCW_100P
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
