package me.mgray.universalremote.client.model.event;

import me.mgray.universalremote.shared.Command;

public class CommandReceivedEvent {
    Command command;

    public CommandReceivedEvent(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
