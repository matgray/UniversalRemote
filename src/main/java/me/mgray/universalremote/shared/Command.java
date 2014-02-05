/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.shared;

import java.util.ArrayList;
import java.util.List;

public class Command {
    List<String> signals = new ArrayList<String>();

    public Command() {
        //no-op for json serialization
    }

    public Command(String... signals) {
        for (String signal : signals) {
            this.signals.add(signal);
        }
    }
}
