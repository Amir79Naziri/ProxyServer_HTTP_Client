package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    private int port;
    private ExecutorService pool;


    public Server (int port)
    {
        this.port = port;
        pool = Executors.newCachedThreadPool ();
    }


    public void startServer ()
    {
        try (ServerSocket welcomingConnection = new ServerSocket (port)){
            int i = 1;
            while (true)
            {
                pool.execute (new ClientHandler (welcomingConnection.accept (),i));
                System.out.println ("Server connected to Client " + i);
                i++;
            }
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }


}
