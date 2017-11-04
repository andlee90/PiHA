package RoleObjects;

import java.io.Serializable;

/**
 * Holds all info relating to a specific role and serializes it for transport across a socket.
 */
public class Role implements Serializable
{
    private int id;
    private String name;
    private int priority;

    public Role(int id, String n, int p)
    {
        this.id = id;
        this.name = n;
        this.priority = p;
    }

    public int getRoleId()
    {
        return this.id;
    }

    public String getRoleName()
    {
        return this.name;
    }

    public int getRolePriority()
    {
        return this.priority;
    }

    public void setName(String n)
    {
        this.name = n;
    }

    public void setPriority(int p)
    {
        this.priority = p;
    }
}
