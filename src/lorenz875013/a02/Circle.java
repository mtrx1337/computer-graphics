package lorenz875013.a02;

import cgtools.Vec3;

import java.util.Comparator;

public class Circle {
    final double radius;
    final int x;
    final int y;
    final Vec3 color;
    Comparator comp;

    public Circle(double radius, int x, int y, Vec3 color){
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.color = color;

        this.comp = new Comparator<Circle>() {
            @Override
            public int compare(Circle c1, Circle c2) {
                return Double.compare(c1.radius, c2.radius);
            }
        };
    }
}
