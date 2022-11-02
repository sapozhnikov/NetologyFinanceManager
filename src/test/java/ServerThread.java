import java.io.IOException;

// this class just for tests purposes
public class ServerThread extends Thread{
    private Server server;

    public ServerThread() {
        server = new Server();
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void run() {
        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
