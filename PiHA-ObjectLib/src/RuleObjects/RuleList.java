package RuleObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains a serialized list of Rule objects for transport across a socket.
 */
public class RuleList implements Serializable
{
    private int serverId;
    private ArrayList<Rule> rules;

    public RuleList()
    {
        this.serverId = -1;
        this.rules = new ArrayList<>();
    }

    public int getServerId()
    {
        return this.serverId;
    }

    public ArrayList<Rule> getRules()
    {
        return rules;
    }

    public void setServerId(int id)
    {
        this.serverId = id;
    }

    public void setRules(ArrayList<Rule> r)
    {
        rules = r;
    }

    public void addRule(Rule r)
    {
        rules.add(r);
    }

    public Rule getRuleById(int id)
    {
        return rules.get(id);
    }
}
