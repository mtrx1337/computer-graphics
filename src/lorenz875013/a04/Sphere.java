package lorenz875013.a04;

import cgtools.Vec3;

import static cgtools.Vec3.*;

public class Sphere implements Shape {
    Vec3 center;
    Vec3 color;
    double radius;

    public Sphere(Vec3 center, double radius, Vec3 color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public Hit intersect(Ray r) {
        /** x vector **/
        double a = dotProduct(r.normDirection, r.normDirection);
        double b = 2 * dotProduct(subtract(r.origin, center), r.normDirection);
        double c = dotProduct(subtract(r.origin, center), subtract(r.origin, center)) - Math.pow(radius, 2);

        /** Vec3 x = new Vec3(a, b, c); **/

        double discriminant = (b * b) - (4 * a * c);
        if (discriminant >= 0) {
            /** which of the two hits of the sphere is closer? return the closest hit! **/
            double t1 = (0 - b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (0 - b - Math.sqrt(discriminant)) / (2 * a);
            double t;
            if (t1 <= t2) {
                t = t1;
            } else {
                t = t2;
            }
            if (t > r.min && t < r.max) {
                Vec3 hitVec = r.pointAt(t);
                Vec3 hitNormVec = normalize(divide(subtract(hitVec, center), radius));
                Hit hit = new Hit(t, hitVec, hitNormVec, color);
                return hit;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
