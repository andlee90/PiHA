package Managers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Creates a server socket on a new thread and listens for incoming client connections.
 * For each new client connection up to MAX_CLIENTS, a new thread is created for the management
 * of that client's requests.
 */
public class ServerManager extends Thread
{
    final private int PORT_NUMBER;
    final private int MAX_CLIENTS;
    final private ServerSocket SERVER_SOCKET;

    private volatile static ClientManager[] clientConnections; // Holds all established client threads

    ServerManager(int port, int max) throws IOException
    {
        this.PORT_NUMBER = port;
        this.MAX_CLIENTS = max;
        this.SERVER_SOCKET = new ServerSocket(PORT_NUMBER);

        clientConnections = new ClientManager[MAX_CLIENTS];
    }

    @Override
    public void run()
    {
        System.out.println("> [" + MainManager.getDate() + "] Starting new server... type command 'help' for usage");
        System.out.println("> [" + MainManager.getDate() + "] Listening on port: " + PORT_NUMBER);
        System.out.println("> [" + MainManager.getDate() + "] Maximum allowed client connections: " + MAX_CLIENTS);

        Socket socket = null;

        while (!interrupted())
        {
            try
            {
                socket = this.SERVER_SOCKET.accept(); // Waits for incoming client
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            assignClientToThread(socket);
        }
        try
        {
            for(ClientManager cm : clientConnections)
            {
                cm.interrupt(); // Interrupt all child threads if this thread is interrupted
            }
            SERVER_SOCKET.setReuseAddress(true);
            SERVER_SOCKET.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Assigns each new client connection to it's own manager until MAX_CLIENTS is reached.
     */
    private void assignClientToThread(Socket socket)
    {
        for (int i = 0; i <= MAX_CLIENTS; i++)
        {
            if(i == MAX_CLIENTS)
            {
                System.out.println("> [" + MainManager.getDate() + "] WARNING: Maximum client connections reached");
                break;
            }
            else if(clientConnections[i] == null)
            {
                clientConnections[i] = new ClientManager(socket, i, this);
                break;
            }
        }
    }

    /**
     * @return an array of currently established connections.
     */
    ClientManager[] getClientConnections()
    {
        return clientConnections;
    }
}
