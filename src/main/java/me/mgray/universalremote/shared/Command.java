/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Command implements Serializable {
    private String sessionId;
    private List<String> signals = new ArrayList<String>();

    public Command() {
        //no-op for json serialization
    }

    public Command(String sessionId, String... signals) {
        this.sessionId = sessionId;
        for (String signal : signals) {
            this.signals.add(signal);
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<String> getSignalsSequence() {
        return signals;
    }
}
