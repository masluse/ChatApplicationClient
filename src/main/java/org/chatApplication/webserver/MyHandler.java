package org.chatApplication.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange http) throws IOException {
        String filePath = "src/main/java/org/chatApplication/webserver/template/index.html";

        File file = new File(filePath);
        if (file.exists()) {
            byte[] fileContent = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(fileContent);
            fis.close();

            http.getResponseHeaders().set("Content-Type", "text/html");

            http.sendResponseHeaders(200, fileContent.length);
            OutputStream os = http.getResponseBody();
            os.write(fileContent);
            os.close();
        } else {
            String response = "404 Not Found";
            http.sendResponseHeaders(404, response.length());
            OutputStream os = http.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
