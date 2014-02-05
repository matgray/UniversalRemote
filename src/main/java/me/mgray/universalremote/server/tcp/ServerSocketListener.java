/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.tcp;

import me.mgray.universalremote.server.shared.InternalConnection;
import me.mgray.universalremote.server.tcp.model.ClientConnection;
import me.mgray.universalremote.server.tcp.model.ClientService;
import me.mgray.universalremote.server.tcp.model.HttpServletSocketService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketListener implements ServletContextListener {
    ExecutorService listenerService = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    private ServerSocket httpServletSocket;
    private int listenPort = 15413;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            serverSocket = new ServerSocket(listenPort);
            httpServletSocket = new ServerSocket(listenPort + 1);

            Runnable clientListen = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Starting server socket at port: " + listenPort);
                    while (true) {
                        Socket client = null;
                        try {
                            client = serverSocket.accept();
                            System.out.print("Client connecting...");
                            ClientConnection c = new ClientConnection(client);
                            ClientService.getInstance().clientConnected(c);
                            System.out.println(" Success!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Client connected from: " + client.getInetAddress().getHostAddress());
                    }
                }
            };

            Runnable httpServletListen = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Starting server socket at port: " + (listenPort + 1));
                    while (true) {
                        Socket httpServlet = null;
                        try {
                            httpServlet = httpServletSocket.accept();
                            System.out.print("Front end connecting...");
                            InternalConnection c = new InternalConnection(httpServlet);
                            HttpServletSocketService.getInstance().addHttpServlet(c);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            listenerService.submit(clientListen);
            listenerService.submit(httpServletListen);

            System.out.println("Server socket started successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (serverSocket != null) {
                System.out.println("Stopping server socket at port: " + listenPort);
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
