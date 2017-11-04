package RoleObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains a serialized list of Role objects for transport across a socket.
 */
public class RoleList implements Serializable
{
    private int serverId;
    private ArrayList<Role> roles;

    public RoleList()
    {
        this.serverId = -1;
        this.roles = new ArrayList<>();
    }

    public int getServerId()
    {
        return this.serverId;
    }

    public ArrayList<Role> getRoles()
    {
        return roles;
    }

    public void setServerId(int id)
    {
        this.serverId = id;
    }

    public void setRoles(ArrayList<Role> r)
    {
        roles = r;
    }

    public void addRole(Role r)
    {
        roles.add(r);
    }

    public Role getRoleById(int id)
    {
        return roles.get(id);
    }
}
