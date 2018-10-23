package lorenz875013.a02;

import cgtools.Vec3;

public class Circle implements Comparable<Circle>{
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

    public int compareTo(Circle otherCircle){
        if(this.radius < otherCircle.radius){
            return 1;
        } else if (this.radius > otherCircle.radius){
            return -1;
        } else if (this.radius == otherCircle.radius){
            return 0;
        }
        return this.compareTo(otherCircle);
    }
}
