import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(7689)) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("The Server is running on IP: " + inetAddress.getHostAddress());


            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");


                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(new Date());

                // Receive the acknowledgment from the client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = in.readLine();
                if ("Received".equals(clientMessage)) {
                    System.out.println("Message from client: " + clientMessage);
                }

                // Terminate connection
                clientSocket.close();
                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}