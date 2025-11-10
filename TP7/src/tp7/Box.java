package tp7;

import java.io.Serializable;

public class Box implements Serializable {
    private int x;
    private int y;
    private int z;

    public Box(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Box(" + x + ", " + y + ", " + z + ")";
    }
}
