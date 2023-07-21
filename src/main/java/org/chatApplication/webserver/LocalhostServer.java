package org.chatApplication.webserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LocalhostServer {
    public static void startServer() throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("[*] Server started on localhost:" + port);
        while (true) { Thread.onSpinWait(); } // This line will keep the server running indefinitely
    }
}
