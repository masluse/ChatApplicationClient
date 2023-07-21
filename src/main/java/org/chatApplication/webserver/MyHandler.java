package org.chatApplication.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.chatApplication.databaseHandler.MessageDao;
import org.chatApplication.databaseHandler.model.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyHandler implements HttpHandler {
    private final MessageDao messageDao = new MessageDao();
    private final int MESSAGE_LIMIT = 200;

    @Override
    public void handle(HttpExchange http) throws IOException {
        List<Message> messages = messageDao.getLastMessages(MESSAGE_LIMIT);

        JSONArray messagesArray = new JSONArray();
        for (Message message : messages) {
            JSONObject messageObject = new JSONObject();
            messageObject.put("user", message.getUsername());
            messageObject.put("message", message.getText());
            messageObject.put("timestamp", message.getTimestamp());
            messagesArray.put(messageObject);
        }

        String response = messagesArray.toString();
        http.getResponseHeaders().set("Content-Type", "application/json");
        http.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = http.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
