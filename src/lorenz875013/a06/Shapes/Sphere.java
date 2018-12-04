package lorenz875013.a06.Shapes;

import cgtools.Vec3;
import lorenz875013.a06.Materials.Material;
import lorenz875013.a06.RayTracer.Hit;
import lorenz875013.a06.RayTracer.Ray;

import static cgtools.Vec3.*;

public class Sphere implements Shape {
    Vec3 center;
    double radius;
    Material material;

    public Sphere(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public Hit intersect(Ray r) {
        /** x vector **/
        double a = dotProduct(r.normDirection, r.normDirection);
        double b = 2 * dotProduct(subtract(r.origin, this.center), r.normDirection);
        double c = dotProduct(subtract(r.origin, this.center), subtract(r.origin, center)) - (this.radius * this.radius);

        /** Vec3 x = new Vec3(a, b, c); **/

        double discriminant = (b * b) - (4 * a * c);
        double t;
        if (discriminant > 0) {
            /** which of the two hits of the sphere is closer? return the closest hit! **/
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            if (t1 < t2) {
                t = t1;
            } else {
                t = t2;
            }
        } else {
            return null;
        }

        if (t > r.min && t < r.max) {
            Vec3 hitVec = r.pointAt(t);
            Vec3 hitNormVec = normalize(subtract(hitVec, this.center));
            Hit hit = new Hit(t, hitVec, hitNormVec, material);
            return hit;
        } else {
            return null;
        }
    }
}
