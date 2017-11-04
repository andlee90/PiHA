package UserObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains a serialized list of User objects for transport across a socket.
 */
public class UserList implements Serializable
{
    private int serverId;
    private ArrayList<User> users;

    public UserList()
    {
        this.serverId = -1;
        this.users = new ArrayList<>();
    }

    public int getServerId()
    {
        return this.serverId;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public void setServerId(int id)
    {
        this.serverId = id;
    }

    public void setUsers(ArrayList<User> u)
    {
        users = u;
    }

    public void addUser(User u)
    {
        users.add(u);
    }

    public User getUserById(int id)
    {
        return users.get(id);
    }
}
