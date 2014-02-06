/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.tcp.model;

import com.google.gson.Gson;
import me.mgray.universalremote.server.tcp.model.event.CommandEvent;
import me.mgray.universalremote.shared.Command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientService {
    private static ClientService INSTANCE;

    private ExecutorService socketService = Executors.newCachedThreadPool();
    private Map<String, ClientConnection> connectionMap =
            new HashMap<>();
    private Gson gson = new Gson();

    private ClientService() {
        //no-op
    }

    public static ClientService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientService();
        }
        return INSTANCE;
    }

    public void clientConnected(final ClientConnection connection) {
        //Listen for messages from client
        socketService.submit(new Runnable() {
            @Override
            public void run() {
                String line;
                try {
                    // Let the client know the session id
                    connection.write(connection.getSessionId());
                    // Listen for reads from client
                    while ((line = connection.read()) != null) {
                        onReadFromClient(line);
                    }
                } catch (IOException e) {
                    System.out.println("I/O Exception for " + connection.getSessionId());
                }
            }
        });
        connectionMap.put(connection.getSessionId(), connection);
    }

    public void sendCommand(String clientId, Command command) {
        Gson gson = new Gson();
        String json = gson.toJson(command, Command.class);
        connectionMap.get(clientId).write(json);
    }

    public void onReadFromClient(String data) {
        System.out.println("Client says: " + data);
    }

    public void onCommandRecieved(CommandEvent event) {
        System.out.println("Command Recieved by client service");
        ClientConnection connection = connectionMap.get(event.getCommand().getSessionId());
        System.out.println("gson initialized");
        if (connection != null) {
            connection.write(gson.toJson(event.getCommand()));
        }
    }
}
