package org.chatApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ChatClient {
    private static String SERVER_HOST;
    private static int SERVER_PORT;

    private void run() {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("\u001B[32m[*] Connected to the server " + SERVER_HOST + " on Port " + SERVER_PORT + "\u001B[0m");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your username: ");
            String username = userInput.readLine();

            if (username.isEmpty()) username = "guest " + UUID.randomUUID();
            System.out.println("You are now chatting with the username " + username);

            String finalUsername = username;
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
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

            String message;
            while (true) {
                message = userInput.readLine();
                out.println(username + ": " + message);
            }

        } catch (IOException e) {
            System.out.println("\u001B[31m[*] No connection could be made. Make sure you entered the correct IP and Port.\u001B[0m");
        }
    }

    public void initialize() {
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

    public static void main(String[] args) {
        new ChatClient().initialize();
        new ChatClient().run();
    }
}