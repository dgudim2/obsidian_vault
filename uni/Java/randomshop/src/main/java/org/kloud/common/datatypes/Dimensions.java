package org.kloud.common.datatypes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

/**
 * Custom datatype that holds 3 floats (basically a Vector3)
 */
@Getter
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
public class Dimensions extends CustomDatatype {

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
    public void deserializeFromString(@Nullable String data) {
        if (data == null) {
            return;
        }
        var parts = data.split("--");
        width = Float.parseFloat(parts[0]);
        height = Float.parseFloat(parts[1]);
        depth = Float.parseFloat(parts[2]);
    }

    @Override
    public String toString() {
        return "W:" + width + " H:" + height + " D:" + depth;
    }
}
