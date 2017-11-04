package RuleObjects;

import java.io.Serializable;

/**
 * Holds all info relating to a specific rule and serializes it for transport across a socket.
 */
public class Rule implements Serializable
{
    private int id;
    private String roleName;
    private String permissionName;
    private String deviceName;

    public Rule(int id, String r, String p, String d)
    {
        this.id = id;
        this.roleName = r;
        this.permissionName = p;
        this.deviceName = d;
    }

    public int getRuleId()
    {
        return this.id;
    }

    public String getRoleName()
    {
        return this.roleName;
    }

    public String getPermissionName()
    {
        return this.permissionName;
    }

    public String getDeviceName()
    {
        return this.deviceName;
    }

    public void setRoleName(String r)
    {
        this.roleName = r;
    }

    public void setPermissionName(String p)
    {
        this.permissionName = p;
    }

    public void setDeviceName(String d)
    {
        this.deviceName = d;
    }
}
