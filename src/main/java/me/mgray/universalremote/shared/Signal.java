/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.shared;

import java.io.Serializable;

public class Signal implements Serializable {

    public static enum KeyDirection {
        UP,
        DOWN
    }

    private String key;
    private KeyDirection direction;

    /**
     * Needed for serialization and gson
     */
    public Signal() {
        //no-op
    }

    public Signal(String key, KeyDirection direction) {
        this.key = key;
        this.direction = direction;
    }

    public String getKey() {
        return key;
    }

    public KeyDirection getDirection() {
        return direction;
    }
}
