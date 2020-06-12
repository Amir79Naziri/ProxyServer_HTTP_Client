package ServerSocketHandler;


import ConnectionHandler.Executor;
import Storage.RequestsStorage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * this class represents server's client Handler
 *
 * @author Amir Naziri
 */
public class ClientHandler implements Runnable
{

    private Socket connection;
    private int code; // code of client
    private RequestsStorage requestsStorage;

    /**
     * creates new Client Handler
     * @param connection connection
     * @param code code
     */
    public ClientHandler (Socket connection, int code)
    {
        this.connection = connection;
        this.code = code;
        requestsStorage = null;
    }

    @Override
    public void run () {

        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            in = receiveData (connection.getInputStream ());
            Executor executor = new Executor (requestsStorage.getClientRequests ());
            new Thread (executor).start ();
            while (!executor.getPool ().isTerminated ())
            {
                Thread.sleep (500);
            }
            out = sendData (connection.getOutputStream ());
        } catch (ClassNotFoundException e)
        {
            System.err.println ("Some Thing went Wrong while reading from Client");
        } catch (SocketException e)
        {
            e.printStackTrace ();
            System.err.println ("ClientRequest " + code + " 's connection Terminated");
        } catch (InterruptedException | IOException e)
        {
            System.err.println (e.getMessage ());
        } finally
        {
            try {
                if (in != null)
                    in.close ();
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing ServerInputStream");
            }
            try {

                if (out != null)
                    out.close ();
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing ServerOutputStream");
            }
            try {
                connection.close ();
                System.out.println ("Client " + code + " closed");
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing client " + code +
                        "connection");
            }
        }
    }

    /**
     * receive data from client
     * @param serverInputStream serverInputStream
     * @return inputStream
     * @throws IOException IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    private ObjectInputStream receiveData (InputStream serverInputStream) throws IOException,
            ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream (serverInputStream);
        requestsStorage = (RequestsStorage)in.readObject ();
        System.out.println ("<- data received from client " + code);
        return in;
    }

    /**
     * send data to client
     * @param serverOutputStream serverOutputStream
     * @return outputStream
     * @throws IOException IOException
     */
    private ObjectOutputStream sendData (OutputStream serverOutputStream) throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream (serverOutputStream);
        out.writeObject (requestsStorage);
        System.out.println ("-> data sent to client " + code);
        return out;
    }

}
