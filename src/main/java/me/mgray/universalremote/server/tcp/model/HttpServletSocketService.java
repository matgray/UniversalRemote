/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.tcp.model;

import me.mgray.universalremote.server.shared.InternalConnection;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServletSocketService {
    public static HttpServletSocketService INSTANCE;
    private ExecutorService listenerService = Executors.newCachedThreadPool();


    public static HttpServletSocketService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpServletSocketService();
        }
        return INSTANCE;
    }

    public void addHttpServlet(final InternalConnection connection) {
        Runnable listenRunnable = new Runnable() {
            @Override
            public void run() {
                String inputLine;
                try {
                    while ((inputLine = connection.getObjectInputStream().
                            readObject().toString()) != null) {
                        System.out.println("Got " + inputLine + " from http server");
                    }
                } catch (IOException e) {
                    return;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        listenerService.submit(listenRunnable);
    }
}
