package org.kloud.common;

import java.io.Serializable;

public class Dimensions implements Serializable {
    protected float width;
    protected float height;
    protected float depth;

    public Dimensions(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
}
