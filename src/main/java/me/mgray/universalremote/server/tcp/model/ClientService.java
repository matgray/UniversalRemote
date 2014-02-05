/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.tcp.model;

import com.google.gson.Gson;
import me.mgray.universalremote.shared.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientService {
    private static ClientService INSTANCE;

    private ExecutorService socketService;
    private Map<String, ClientConnection> connectionMap;
    private Gson gson;

    private ClientService() {
        //no-op
    }

    public static ClientService getInstance() {
        System.out.println("returning instance");
        if (INSTANCE == null) {
            INSTANCE = new ClientService();
            INSTANCE.socketService = Executors.newCachedThreadPool();
            INSTANCE.connectionMap = new HashMap<String, ClientConnection>();
        }
        return INSTANCE;
    }

    public void clientConnected(ClientConnection connection) {
        connectionMap.put(connection.getId(), connection);
    }

    public void sendCommand(String clientId, Command command) {
        Gson gson = new Gson();
        String json = gson.toJson(command, Command.class);
        connectionMap.get(clientId).write(json);
    }
}
