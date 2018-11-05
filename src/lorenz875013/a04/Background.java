package lorenz875013.a04;

import cgtools.Vec3;
import static cgtools.Vec3.*;

public class Background implements Shape {
    Vec3 center;
    Vec3 size;
    Vec3 color;

    public Background(Vec3 center, Vec3 size, Vec3 color){
        this.center = center;
        this.size = size;
        this.color = color;
    }

    public Hit intersect(Ray r) {
        /** x vector **/
        Vec3 hitVec = r.pointAt(Double.POSITIVE_INFINITY);
        Vec3 hitNormVec = normalize(subtract(hitVec, center));
        Hit hit = new Hit(Double.POSITIVE_INFINITY, hitVec, hitNormVec, color);
        return hit;
    }
}
