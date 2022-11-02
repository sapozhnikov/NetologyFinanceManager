import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.fail;

public class ServerTest {
    private static Thread serverThread;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @BeforeAll
    static void ServerInit() throws InterruptedException {
        serverThread = new ServerThread();
        serverThread.setPriority(java.lang.Thread.NORM_PRIORITY + 1);
        serverThread.start();
        Thread.sleep(100); //I know, I know, no time to fix
    }

    @BeforeEach
    void ServerStart() throws IOException {
        socket = new Socket("localhost", 8989);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.setSoTimeout(1000);
    }

    @Test
    void DataAccept() {
        System.out.println("sending test packet");
        out.println("{\"title\": \"булка\", \"date\": \"2022.02.08\", \"sum\": 200}");
        try {
            System.out.println("client received: " + in.readLine());
        } catch (SocketTimeoutException e) {
            fail("No response from server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterEach
    void ConnectionsClose() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @AfterAll
    static void ServerClose() throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 8989);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        socket.setSoTimeout(1000);

        System.out.println("sending killswitch");
        out.println(Constants.killSwitch);
        serverThread.join();
    }
}
