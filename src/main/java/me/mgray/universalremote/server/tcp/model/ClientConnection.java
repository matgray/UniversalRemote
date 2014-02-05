/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.tcp.model;

import me.mgray.universalremote.shared.Connection;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Connection {

    private String hostname;
    private String macAddress;

    public ClientConnection(Socket socket) throws IOException {
        super(socket);
        System.out.println(" client initialized");
    }

    public String getId() {
        return "test_id_1";
    }

}
