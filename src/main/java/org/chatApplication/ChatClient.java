package org.chatApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ChatClient {
    private String SERVER_HOST;
    private int SERVER_PORT;

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

            Scanner userInput = new Scanner(System.in);
            System.out.print("Enter your username: ");
            final String[] username = {userInput.nextLine()};

            if (username[0].isEmpty()) username[0] = "guest " + UUID.randomUUID();
            System.out.println("You are now chatting with the username " + username[0]);

            Thread receiveThread = new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        if (!message.startsWith(username[0] + ": ")) {
                            System.out.println(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            while (true) {
                String message = userInput.nextLine();
                out.println(username[0] + ": " + message);
            }

        } catch (IOException e) {
            System.out.println("\u001B[31m[*] No connection could be made. Make sure you entered the correct IP and Port.\u001B[0m");
        }
    }

    private void initialize() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the IP Address -> ");
        SERVER_HOST = sc.nextLine();
        System.out.print("Enter the Port -> ");
        try {
            SERVER_PORT = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException ex){
            System.out.println("\u001B[31m[*] The port can only contain numbers.\u001B[0m");
        }
    }
}
