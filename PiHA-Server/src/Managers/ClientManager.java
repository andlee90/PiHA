package Managers;

import CommandObjects.Command;
import Controllers.DeviceController;
import Controllers.DeviceControllerFactory;
import Database.Helper;
import DeviceObjects.Device;
import DeviceObjects.DeviceList;
import UserObjects.User;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

public class ClientManager extends Thread
{
    private int threadId;
    private ClientManager[] clientConnections;

    private User authenticatedUser;
    private String authenticatedUserName;
    private String authenticatedUserRole;
    private String authenticatedUserAddress;

    private Socket socket;
    private ObjectInputStream serverInputStream;
    private ObjectOutputStream serverOutputStream;

    private volatile Device device = null;
    private volatile DeviceController dc = null;

    ClientManager(Socket p, int id, ServerManager sm)
    {
        this.socket = p;
        this.threadId = id;
        this.clientConnections = sm.getClientConnections();

        start();
    }

    @Override
    public void run()
    {
        try
        {
            serverInputStream = new ObjectInputStream(socket.getInputStream());
            serverOutputStream = new ObjectOutputStream(socket.getOutputStream());

            User user = (User) serverInputStream.readObject();

            if (authenticateUser(user))
            {
                authenticatedUserName = authenticatedUser.getUserName();
                authenticatedUserRole = authenticatedUser.getRole();

                int authenticatedUserRoleId = Helper.selectRoleIdByRoleName(authenticatedUser.getRole());
                Hashtable<String, String> rules = Helper.selectRulesByRoleId(authenticatedUserRoleId);

                authenticatedUser.setRules(rules);
                serverOutputStream.writeObject(authenticatedUser);
                authenticatedUserAddress = socket.getRemoteSocketAddress().toString();
                authenticatedUserAddress = authenticatedUserAddress.substring(1, authenticatedUserAddress.indexOf(':'));

                System.out.println("> [" + MainManager.getDate() + "] " + authenticatedUserName + "@"
                        + authenticatedUserAddress + " connected");

                while(!interrupted())
                {
                    try
                    {
                        Object object = serverInputStream.readObject();

                        if (object instanceof DeviceList)
                        {
                            DeviceList devices = getDevices();
                            serverOutputStream.writeObject(devices);
                        }

                        else if (object instanceof Device)
                        {
                            device = (Device) object;

                            Helper.updateDevice(device.getDeviceId(),
                                    device.getDeviceName(),
                                    device.getDeviceType().toString(),
                                    device.getDeviceStatus().toString(),
                                    device.getDeviceMode().toString());

                            dc = DeviceControllerFactory.getDeviceController(device);
                        }

                        else if (object instanceof Command)
                        {
                            Command command = (Command) object;

                            if (dc != null)
                            {
                                Enum deviceMode = dc.issueCommand(command.getCommandType());
                                device.setDeviceMode(deviceMode);

                                // disregard the state of any Device already written to the stream
                                serverOutputStream.reset();
                                serverOutputStream.writeObject(device);
                            }
                        }
                    }
                    catch (EOFException e)
                    {
                        System.out.println("> [" + MainManager.getDate() + "] " + user.getUserName() + "@"
                                + authenticatedUserAddress + " disconnected");
                        interrupt();
                        break;
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException | InterruptedException e)
        {
            interrupt();
        }
    }

    /**
     * Interrupts current thread and performs cleanup.
     */
    public void interrupt()
    {
        try
        {
            clientConnections[threadId] = null; // Clear index

            this.serverInputStream.close();
            this.serverOutputStream.close();
            this.socket.close();

            Thread.currentThread().interrupt(); // Terminate thread
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    /**
     * Authenticates the received user credentials by querying for them in the db.
     * @param u the User containing the credentials to be checked.
     * @return true for an authenticated user, false otherwise.
     */
    private boolean authenticateUser(User u)
    {
        User user = Helper.selectUserByUsernameAndPassword(u.getUserName(), u.getPassword());

        if (user != null && user.getUserId() != 0)
        {
            authenticatedUser = user;
            return true;
        }

        else
        {
            return false;
        }
    }

    /**
     * @return A DeviceList object containing a List of all Devices stored in the db.
     */
    private DeviceList getDevices()
    {
        return Helper.selectAllDevices();
    }

    /**
     * @return This thread's index in the ClientConnections[].
     */
    int getThreadId()
    {
        return threadId;
    }

    /**
     * @return This thread's User.
     */
    String getAuthenticatedUserName()
    {
        return authenticatedUserName;
    }

    /**
     * @return This thread's User's role.
     */
    String getAuthenticatedUserRole()
    {
        return authenticatedUserRole;
    }

    /**
     * @return This thread's User's address.
     */
    String getAuthenticatedUserAddress()
    {
        return authenticatedUserAddress;
    }
}
