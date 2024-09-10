import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Ask for the server IP address
            System.out.print("Enter the server IP address: ");
            String serverIp = scanner.nextLine();

            // Connect to the server
            Socket socket = new Socket(serverIp, 7689);
            System.out.println("Connected to the server");

            // Receive the date and time from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage = in.readLine();
            System.out.println("Received from server: " + serverMessage);

            // Send acknowledgment to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Received");

            // Close the connection
            socket.close();
            System.out.println("Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

