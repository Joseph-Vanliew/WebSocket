import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Ask for the server's IP address
            System.out.print("Enter the server IP address: ");
            String serverIp = scanner.nextLine();
            // Connect to the server
            Socket socket = new Socket(serverIp, 7689);
            System.out.println("Connected to the server");

            // Input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Display welcome message from the server
            System.out.println(in.readLine());

            String userInput;
            // Continuously send input to the server until the user enters an empty string
            while (true) {
                System.out.print("Enter a string (or press Enter to quit): ");
                userInput = scanner.nextLine();

                if (userInput.isEmpty()) {
                    break;  // Exit if the input is an empty string
                }

                // Send input to the server
                out.println(userInput);

                // Receive response from the server and print it
                String response = in.readLine();
                System.out.println("Received from server: " + response);
            }

            // Close the connection
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

