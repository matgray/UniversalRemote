/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.http;

import me.mgray.universalremote.server.shared.InternalConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Server extends HttpServlet {
    InternalConnection serverConnection;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        if (serverConnection == null) {
            serverConnection = new InternalConnection(new Socket("127.0.0.1", 15414));
        }
        serverConnection.write("foobar");
        out.println("SimpleServlet Executed");
        out.flush();
        out.close();
    }
}
