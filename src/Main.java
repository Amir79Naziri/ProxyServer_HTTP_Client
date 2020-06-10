import ServerSocketHandler.Server;

public class Main
{
    public static void main (String[] args) {

        Server server = new Server (7474);
        server.startServer ();
    }
}
