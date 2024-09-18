import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the server IP address: ");
            String serverIp = scanner.nextLine();

            Socket socket = new Socket(serverIp, 7689);
            System.out.println("Connected to the server");

            // Input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(in.readLine());

            String userInput;
            // Continuously send input to the server until the user enters an empty string
            while (true) {
                System.out.print("Enter 'time' for the time or send an empty message to disconnect: ");
                userInput = scanner.nextLine();

                if (userInput.isEmpty()) {
                    break;  // Exit if the input is an empty string
                }

                // Send input to the server
                out.println(userInput);

                // Receive response from the server and print it
                String response = in.readLine();
                System.out.println("Server Response: " + response);
            }

            // Close the connection
            socket.close();
            System.out.println("CONNECTION CLOSED...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

