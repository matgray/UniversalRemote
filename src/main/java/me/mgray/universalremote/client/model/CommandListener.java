/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.client.model;

import com.google.gson.Gson;
import me.mgray.universalremote.client.model.event.CommandReceivedEvent;
import me.mgray.universalremote.shared.Command;
import me.mgray.universalremote.shared.Connection;
import org.bushe.swing.event.EventBus;

import java.io.IOException;

public class CommandListener implements Runnable {
    Connection connection;

    public CommandListener(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Gson gson = new Gson();
        while (true) {
            try {
                String incomingCommand = connection.read();
                System.out.println("Received Event:\n" + incomingCommand);
                EventBus.publish(new CommandReceivedEvent(
                        gson.fromJson(incomingCommand, Command.class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
