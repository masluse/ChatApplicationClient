package org.chatApplication;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Chat Application");
    private JTextField textField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 40);

    public ChatClient() {
        // GUI Layout
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();

        // Dark mode
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        messageArea.setBackground(Color.DARK_GRAY);
        messageArea.setForeground(Color.WHITE);

        // Action for the text field
        textField.addActionListener(e -> {
            out.println(textField.getText());
            textField.setText("");
        });
    }

    private String getServerAddress() {
        return JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Chat Application",
                JOptionPane.QUESTION_MESSAGE);
    }

    private String getName() {
        return JOptionPane.showInputDialog(
                frame,
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String getPassword() {
        return JOptionPane.showInputDialog(
                frame,
                "Enter your password:",
                "Password entry",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void run() throws Exception {
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 5000);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(getName() + ":" + getPassword());
            } else if (line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}
