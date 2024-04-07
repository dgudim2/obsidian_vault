package org.kloud.common.datatypes;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

/**
 * Custom datatype that holds 3 floats (basically a Vector3)
 */
@Getter
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
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dimensions that)) return false;

        return Float.compare(width, that.width) == 0 && Float.compare(height, that.height) == 0 && Float.compare(depth, that.depth) == 0;
    }

    @Override
    public int hashCode() {
        int result = Float.hashCode(width);
        result = 31 * result + Float.hashCode(height);
        result = 31 * result + Float.hashCode(depth);
        return result;
    }
}
