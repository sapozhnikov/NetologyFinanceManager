import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server(){

    }

    public void run() throws IOException {
        //server starting
        Statistics statistics = new Statistics();
        Gson gson = new Gson();
        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);) {
                    String stringFromSocket = serverIn.readLine(); //server in
                    System.out.println("server received: " + stringFromSocket);
                    if (stringFromSocket.equals(Constants.killSwitch)) {
                        break;
                    }

                    Purchase purchase = gson.fromJson(stringFromSocket, Purchase.class);
                    Report report = statistics.CommitPurchase(purchase);
                    if (report != null) {
                        serverOut.println(gson.toJson(report)); //server out
                    }
                    else {
                        serverOut.println("{}"); //server out
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }
}
