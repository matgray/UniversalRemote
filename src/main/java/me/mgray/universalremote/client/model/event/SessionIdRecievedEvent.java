/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.client.model.event;

/**
 * Created by mat on 2/6/14.
 */
public class SessionIdRecievedEvent {
    private String sessionId;

    public SessionIdRecievedEvent(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
