package server;

import rpc.RpcRequest;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static final AtomicInteger clientCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7689)) {
            System.out.println("The server is running...");

            // Continuously listen for clients
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Accept client connection
                int clientNumber = clientCounter.incrementAndGet();  // Assign a unique client number

                // Create a new thread to handle this client's requests
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientNumber);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private final Socket clientSocket;
    private final int clientNumber;
    private final RpcService rpcService = new RpcService();  // Service to handle RPC calls

    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            // Logging client connection
            logger.info("Client #" + clientNumber + " connected.");

            out.writeObject("Hello! You are client #" + clientNumber);

            Object clientInput;
            // Listening for client input until connection is closed
            while ((clientInput = in.readObject()) != null) {
                if (clientInput instanceof RpcRequest) {
                    RpcRequest request = (RpcRequest) clientInput;

                    String response;
                    if ("getTime".equalsIgnoreCase(request.getMethod())) {
                        // Call the getTime method
                        response = rpcService.getTime();
                    } else if ("capitalize".equalsIgnoreCase(request.getMethod())) {
                        // Call the capitalize method
                        response = rpcService.capitalize(request.getInput());
                    } else {
                        response = "Unknown method";
                    }

                    // Sending the result back to the client
                    out.writeObject(response);
                }
            }
        } catch (EOFException e) {
            // exception expected, print disconnection message instead
            logger.info("Client #" + clientNumber + " disconnected.");
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error occurred while handling client #" + clientNumber, e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error closing socket for client #" + clientNumber, e);
            }
        }
    }
}

