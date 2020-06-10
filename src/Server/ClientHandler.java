package Server;

import ConnectionHandler.Executor;
import Storage.RequestsStorage;
import com.sun.jdi.ClassNotLoadedException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class ClientHandler implements Runnable
{

    private Socket connection;
    private int code;
    private RequestsStorage requestsStorage;

    public ClientHandler (Socket connection, int code)
    {
        this.connection = connection;
        this.code = code;
        requestsStorage = null;
    }

    @Override
    public void run () {

        try {
            receiveData (connection.getInputStream ());
            if (requestsStorage == null)
                throw new ClassNotLoadedException ("Data couldn't load");
            Executor executor = new Executor (requestsStorage.getClientRequests ());
            executor.run ();
            sendData (connection.getOutputStream ());
        } catch (ClassNotLoadedException e)
        {
            System.err.println (e.getMessage ());
        } catch (SocketException e)
        {
            System.err.println ("Client " + code + " 's connection Terminated");
        } catch (IOException e)
        {
            e.printStackTrace ();
        } finally
        {
            try {
                connection.close ();
            } catch (IOException e)
            {
                e.printStackTrace ();
            }
        }
    }


    private void receiveData (InputStream serverInputStream) throws IOException
    {
        try (ObjectInputStream in = new ObjectInputStream (serverInputStream)) {
            requestsStorage = (RequestsStorage)in.readObject ();
            System.out.println ("<- data received from client " + code);
        } catch (ClassNotFoundException e)
        {
            System.err.println ("Some Thing went Wrong while reading from Client");
        }
    }

    private void sendData (OutputStream serverOutputStream) throws IOException
    {
        try (ObjectOutputStream out = new ObjectOutputStream (serverOutputStream)) {
            out.writeObject (requestsStorage);
            System.out.println ("-> data sent to client " + code);
        }
    }

}
