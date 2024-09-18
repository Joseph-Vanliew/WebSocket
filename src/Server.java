import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static AtomicInteger clientCounter = new AtomicInteger(0);  // I'm using an atomic integer for client number tracking so we're 'thread safe' and avoiding race conditions.

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7689)) {
            System.out.println("The server is running...");

            // Continuously listen for a potential client
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Accept client connection
                int clientNumber = clientCounter.incrementAndGet();  // Assign a unique client number
                System.out.println("Client #" + clientNumber + " connected.");

                // Create a new thread to handle this client's requests
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientNumber);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Here I'm handling multiple client requests...
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private int clientNumber;

    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {

            out.println("Hello, you are client #" + clientNumber);

            String clientInput;
            //listening for client input until an empty string is entered
            while ((clientInput = in.readLine()) != null && !clientInput.isEmpty()) {
                if ("time".equalsIgnoreCase(clientInput)) {
                    out.println(new java.util.Date().toString());  // Send current date and time
                } else {
                    out.println(clientInput.toUpperCase());  // responding with capitalized string
                }
            }
            System.out.println("Client #" + clientNumber + " disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

