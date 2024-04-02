package org.kloud.common;

import java.io.Serializable;

public class Dimensions extends CustomDatatype implements Serializable {
    protected float width;
    protected float height;
    protected float depth;

    public Dimensions() {

    }

    public Dimensions(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    @Override
    public String serializeToString() {
        return width + "--" + height + "--" + depth;
    }

    @Override
    public void deserializeFromString(String data) {
        var parts = data.split("--");
        width = Float.parseFloat(parts[0]);
        height = Float.parseFloat(parts[1]);
        depth = Float.parseFloat(parts[2]);
    }
}
