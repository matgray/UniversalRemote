/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.client.model;

import me.mgray.universalremote.client.model.event.CommandReceivedEvent;
import me.mgray.universalremote.shared.Command;
import me.mgray.universalremote.shared.Signal;
import org.bushe.swing.event.annotation.EventSubscriber;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class ClientRobot extends Robot {

    private static ClientRobot INSTANCE;

    private ClientRobot() throws AWTException {
        // no-op
    }

    public static ClientRobot getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new ClientRobot();
            } catch (AWTException e) {
                e.printStackTrace();
                return null;
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    @EventSubscriber(eventClass = CommandReceivedEvent.class)
    public void onCommandReceived(CommandReceivedEvent event) {
        System.out.println("Executing Command");
        Command command = event.getCommand();
        List<Signal> signalSequence = command.getSignalsSequence();
        for (Signal signal : signalSequence) {
            if (signal.getDirection() == Signal.KeyDirection.DOWN) {
                System.out.println(String.format("Pressing Key Code: %X", getKeyCodeFromSignal(signal.getKey())));
                keyPress(getKeyCodeFromSignal(signal.getKey()));
            } else if (signal.getDirection() == Signal.KeyDirection.UP) {
                System.out.println(String.format("Releasing Key Code: %X", getKeyCodeFromSignal(signal.getKey())));
                keyRelease(getKeyCodeFromSignal(signal.getKey()));
            }
        }
    }

    private int getKeyCodeFromSignal(String signal) {
        if (signal.length() == 1) {
            return KeyEvent.getExtendedKeyCodeForChar(signal.charAt(0));
        }
        if (signal.length() > 1) {
            switch (signal) {
                case "SHIFT":
                    return KeyEvent.VK_SHIFT;
                case "CTRL":
                    return KeyEvent.VK_CONTROL;
                case "ALT":
                    return KeyEvent.VK_ALT;
                case "WIN":
                    return KeyEvent.VK_WINDOWS;
                case "TAB":
                    return KeyEvent.VK_TAB;
            }
        }
        return -1;
    }

}
