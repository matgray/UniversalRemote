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
import java.security.SecureRandom;

public class ClientConnection extends Connection {

    private String sessionId;
    private static SecureRandom random = new SecureRandom();

    public ClientConnection(Socket socket) throws IOException {
        super(socket);
        this.sessionId = nextSessionId();
    }

    public static String nextSessionId() {
//        return new BigInteger(130, random).toString(32);
        return "test";
    }

    public String getSessionId() {
        return sessionId;
    }
}
