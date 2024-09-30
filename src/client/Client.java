package client;

import rpc.RpcRequest;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Ask the user for the server IP address
            System.out.print("Enter the server IP address: ");
            String serverIp = scanner.nextLine();

            // Connect to the server
            Socket socket = new Socket(serverIp, 7689);
            System.out.println("Connected to the server");

            // Setup ObjectInputStream and ObjectOutputStream for object communication
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // greeting from the server
            System.out.println(in.readObject());

            String userInput;
            // Continuously ask the user for input until an empty string is entered
            System.out.print("Enter 'time' for the current time or any string to capitalize or send empty prompt to disconnect: \n");
            while (true) {

                userInput = scanner.nextLine();

                if (userInput.isEmpty()) {
                    // Exit the loop and close the connection if the input is empty
                    break;
                }

                // Create an RpcRequest based on the user input
                RpcRequest request = new RpcRequest();

                if ("time".equalsIgnoreCase(userInput)) {
                    request.setMethod("getTime");
                } else {
                    request.setMethod("capitalize");
                    request.setInput(userInput);
                }

                // Send the request object to the server
                out.writeObject(request);

                // Receive and print the response from the server
                Object response = in.readObject();
                System.out.println("Server Response: " + response);
            }

            // Close the socket connection once the user exits
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
