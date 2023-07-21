package org.chatApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ChatClient {
    private String SERVER_HOST;
    private int SERVER_PORT;
    private static final BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.initialize();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            System.out.println("\u001B[32m[*] Connected to the server " + SERVER_HOST + " on Port " + SERVER_PORT + "\u001B[0m");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Add login phase before chat
            String username;
            do {
                System.out.print("[*] Enter your username: ");
                username = userInput.readLine();
                System.out.print("[*] Enter your password: ");
                String password = userInput.readLine();
                out.println(username + ":" + password);
                if (in.readLine().equals("Authenticated")) {
                    System.out.println("[*] Authenticated successfully");
                    break;
                } else {
                    System.out.println("[*] Authentication failed");
                }
            } while (true);

            // Create a final variable to hold the username for use in the lambda expression
            final String finalUsername = username;

            Thread receiveThread = new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        if (!message.startsWith(finalUsername + ": ")) {
                            System.out.println(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            while (true) {
                String message = userInput.readLine();
                out.println(username + ": " + message);
            }

        } catch (IOException e) {
            System.out.println("\u001B[31m[*] No connection could be made. Make sure you entered the correct IP and Port.\u001B[0m");
        }
    }

    private void initialize() {
        try {
            System.out.print("[*] Enter the IP Address for the chat-server: ");
            SERVER_HOST = userInput.readLine();
            System.out.print("[*] Enter the port the server is listening on: ");
            SERVER_PORT = Integer.parseInt(userInput.readLine());
        } catch (NumberFormatException | IOException ex){
            System.out.println("\u001B[31m[*] Make sure you use the correct format.\u001B[0m");
        }
    }
}
