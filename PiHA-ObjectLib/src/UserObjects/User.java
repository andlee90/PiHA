package UserObjects;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Holds all info relating to a specifc user and serializes it for transport across a socket.
 */
public class User implements Serializable
{
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Hashtable<String, String> rules = new Hashtable<>();

    public User(int id, String un, String p, String e, String fn, String ln, String r)
    {
        this.userId = id;
        this.userName = un;
        this.password = p;
        this.email = e;
        this.firstName = fn;
        this.lastName = ln;
        this.role = r;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getRole()
    {
        return this.role;
    }

    public Hashtable<String, String> getRules()
    {
        return this.rules;
    }

    public void setEmail(String e)
    {
        this.email = e;
    }

    public void setFirstName(String fn)
    {
        this.firstName = fn;
    }

    public void setLastName(String ln)
    {
        this.lastName = ln;
    }

    public void setRole(String r)
    {
        this.role = r;
    }

    public void setRules(Hashtable<String, String> r)
    {
        this.rules = r;
    }
}