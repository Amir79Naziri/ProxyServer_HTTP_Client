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
    private ViewStatus viewStatus;

    /**
     * creates a new Server
     * @param port port
     */
    public Server (int port)
    {
        this.port = port;
        pool = Executors.newCachedThreadPool ();
        viewStatus = new ViewStatus ();
    }

    /**
     * start Server
     */
    public void startServer ()
    {
        viewStatus.setVisible (true);
        try (ServerSocket welcomingConnection = new ServerSocket (port)) {
            System.out.println ("Server Started \nWaiting for Client .....");
            viewStatus.getTextArea ().setText ("Server Started \nWaiting for Client .....");
            int i = 1;
            while (true)
            {
                pool.execute (new ClientHandler (welcomingConnection.accept (),i, viewStatus));
                System.out.println ("Server connected to new Client : Client " + i);
                if (i == 1)
                    viewStatus.getTextArea ().setText ("Server" +
                            " connected to new Client : Client " + i);
                else
                    viewStatus.getTextArea ().append ("\nServer connected" +
                            " to new Client : Client " + i);
                i++;
            }
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }


}
