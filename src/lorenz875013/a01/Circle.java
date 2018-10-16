package lorenz875013.a01;

import cgtools.Vec3;

public class Circle {
    final double radius;
    final int x;
    final int y;
    final Vec3 color;

    public Circle(double radius, int x, int y, Vec3 color){
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
