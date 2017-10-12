package CommandObjects;

import java.io.Serializable;

/**
 * Provides an abstract/generic template for Command objects.
 */
public abstract class Command implements Serializable
{
    public abstract Enum getCommandType();
    public abstract void setCommandType(Enum ct);
}
