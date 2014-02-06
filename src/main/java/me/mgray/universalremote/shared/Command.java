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
    private List<Signal> signals = new ArrayList<>();

    public Command() {
        //no-op for json serialization
    }

    public Command(String sessionId, List<Signal> signals) {
        this.sessionId = sessionId;
        this.signals = signals;
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<Signal> getSignalsSequence() {
        return signals;
    }
}
