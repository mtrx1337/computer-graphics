package lorenz875013.a03;

import cgtools.Vec3;
import static cgtools.Vec3.*;

public class Sphere {
    Vec3 origin;
    double radius;
    Vec3 surfaceColor;

    public Sphere(Vec3 origin, double radius, Vec3 surfaceColor){
        this.origin = origin;
        this.radius = radius;
        this.surfaceColor = surfaceColor;
    }

    public Hit intersect(Ray r){
        Vec3 nullVec = new Vec3(0, 0, 0);
        /** x vector **/
        double a = dotProduct(r.normDirection, r.normDirection);
        double b = 2 * dotProduct(subtract(nullVec, origin), r.normDirection);
        double c = dotProduct(subtract(nullVec, origin), subtract(nullVec, origin)) - Math.pow(radius, 2);

        Vec3 x = new Vec3(a, b, c);

        double discriminant = (b * b) - (4 * a * c);
        if(discriminant >= 0) {
            double t1 = Math.abs((0 - b + Math.sqrt(discriminant)));
            double t2 = Math.abs((0 - b - Math.sqrt(discriminant)));
            double t;
            if (t1 < t2) {
                t = t1;
            } else {
                t = t2;
            }
            Vec3 hitVec = r.pointAt(t);
            Vec3 hitNormVec = divide(subtract(hitVec, origin), radius);
            Hit hit = new Hit(t, hitVec, hitNormVec, surfaceColor);
            return hit;
        } else {
            return null;
        }
    }
}
