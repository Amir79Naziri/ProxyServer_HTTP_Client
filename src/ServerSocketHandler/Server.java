package ServerSocketHandler;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class represents the Server
 *
 * @author Amir Naziri
 */
public class Server
{
    private int port;
    private ExecutorService pool;

    /**
     * creates a new Server
     * @param port port
     */
    public Server (int port)
    {
        this.port = port;
        pool = Executors.newCachedThreadPool ();
    }

    /**
     * start Server
     */
    public void startServer ()
    {
        try (ServerSocket welcomingConnection = new ServerSocket (port)) {
            System.out.println ("Server Started \nWaiting for Client .....");
            int i = 1;
            while (true)
            {
                pool.execute (new ClientHandler (welcomingConnection.accept (),i));
                System.out.println ("Server connected to new Client : Client " + i);
                i++;
            }
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }


}
