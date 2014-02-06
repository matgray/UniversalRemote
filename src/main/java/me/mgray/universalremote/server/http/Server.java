/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.http;

import me.mgray.universalremote.server.shared.InternalConnection;
import me.mgray.universalremote.shared.Command;
import me.mgray.universalremote.shared.Signal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends HttpServlet {
    InternalConnection serverConnection;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        if (serverConnection == null) {
            serverConnection = new InternalConnection(new Socket("127.0.0.1", 15414));
        }
        String sessionId = request.getParameter("sessionId");
        String command = request.getParameter("command");
        if (sessionId != null && command != null) {
            System.out.println("HTTP Server recieved new command");
            List<Signal> signals = parseSignals(command);

            // Verify it's a valid sequence of key presses
            if (!verify(signals, out)) {
                out.flush();
                out.close();
                return;
            }
            out.println(String.format("OK\nSending command to client with sessionid %s", sessionId));
            serverConnection.write(new Command(sessionId, signals));
        } else {
            out.println("Invalid Request");
        }

        out.flush();
        out.close();
    }

    private List<Signal> parseSignals(String commandString) {
        List<Signal> result = new ArrayList<>();
        for (String signal : commandString.split(",")) {

            char direction = signal.charAt(0);
            String key = signal.substring(1, signal.length());

            // Ensure there is a key given
            if (key.length() == 0) {
                return null;
            }

            switch (direction) {
                case 'p':
                    result.add(new Signal(key, Signal.KeyDirection.DOWN));
                    break;
                case 'r':
                    result.add(new Signal(key, Signal.KeyDirection.UP));
                    break;
                default:
                    return null;
            }
        }
        return result;
    }

    private boolean verify(List<Signal> signals, PrintWriter out) {
        List<String> pressedKeys = new ArrayList<>(signals.size());
        for (Signal signal : signals) {
            if (signal.getDirection() == Signal.KeyDirection.DOWN) {
                pressedKeys.add(signal.getKey());
            } else if (signal.getDirection() == Signal.KeyDirection.UP) {
                pressedKeys.remove(signal.getKey());
            }
        }
        if (pressedKeys.size() > 0) {
            out.println("Error:");
            for (String key : pressedKeys) {
                out.println(String.format("%s never released", key));
            }
            return false;
        }
        return true;
    }
}
