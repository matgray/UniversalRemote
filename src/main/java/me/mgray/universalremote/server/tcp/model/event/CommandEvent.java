/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.server.tcp.model.event;

import me.mgray.universalremote.shared.Command;

/**
 * Created by mat on 2/5/14.
 */
public class CommandEvent {
    private Command command;

    public CommandEvent(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
