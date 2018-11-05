package lorenz875013.a04;

import cgtools.Vec3;

public class Plane {
    Vec3 origin;
    Vec3 size;
    /** set black for now **/
    Vec3 color;

    public Plane(Vec3 origin, Vec3 size, Vec3 color){
        this.origin = origin;
        this.size = size;
        this.color = color;
    }

    public Hit intersect(Ray r) {
        return null;
    }
}
