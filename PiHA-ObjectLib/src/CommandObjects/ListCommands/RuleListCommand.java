package CommandObjects.ListCommands;

import CommandObjects.Command;

/**
 * Provides an implementation of Command, used to define the specifications for a RuleList command.
 */
public class RuleListCommand extends Command
{
    public enum RuleListCommandType
    {
        ADD_RULE, REMOVE_RULE
    }

    private RuleListCommandType commandType;

    public RuleListCommand(RuleListCommandType ct)
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
        this.commandType = (RuleListCommandType) ct;
    }
}