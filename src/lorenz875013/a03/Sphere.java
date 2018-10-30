package lorenz875013.a03;

import cgtools.Vec3;
import static cgtools.Vec3.*;

public class Sphere {
    Vec3 center;
    double radius;
    Vec3 surfaceColor;

    public Sphere(Vec3 center, double radius, Vec3 surfaceColor){
        this.center = center;
        this.radius = radius;
        this.surfaceColor = surfaceColor;
    }

    public Hit intersect(Ray r){
        Vec3 nullVec = new Vec3(0.0, 0.0, 0.0);
        /** x vector **/
        double a = dotProduct(r.normDirection, r.normDirection);
        double b = 2 * dotProduct(subtract(r.origin, center), r.normDirection);
        double c = dotProduct(subtract(r.origin, center), subtract(r.origin, center)) - Math.pow(radius, 2);

        /** Vec3 x = new Vec3(a, b, c); **/

        double discriminant = (b * b) - (4 * a * c);
        if(discriminant >= 0) {
            double t1 = (0 - b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (0 - b - Math.sqrt(discriminant)) / (2 * a);
            double t;
            if (t1 <= t2) {
                t = t1;
            } else {
                t = t2;
            }
            if(t > r.min && t < r.max){
                /** add positive 0.0 (nullVec) because java is too retarded to understand that a -0.0 equals 0.0 **/
                /** adding this null vector to the hitNormVector to output positive zeroes now. **/
                Vec3 hitVec = r.pointAt(t);
                Vec3 hitNormVec = normalize(divide(subtract(hitVec, center), radius));
                Hit hit = new Hit(t, hitVec, hitNormVec, surfaceColor);
                return hit;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
