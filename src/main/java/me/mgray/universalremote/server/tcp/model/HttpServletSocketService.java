/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.tcp.model;

import me.mgray.universalremote.server.shared.InternalConnection;
import me.mgray.universalremote.server.tcp.model.event.CommandEvent;
import me.mgray.universalremote.shared.Command;

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
                Command command;
                try {
                    while ((command = (Command) connection.getObjectInputStream().
                            readObject()) != null) {
                        final CommandEvent commandEvent = new CommandEvent(command);
                        System.out.println("TCP server received new command");
                        listenerService.submit(new Runnable() {
                            @Override
                            public void run() {
                                ClientService.getInstance().onCommandRecieved(commandEvent);
                            }
                        });
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
