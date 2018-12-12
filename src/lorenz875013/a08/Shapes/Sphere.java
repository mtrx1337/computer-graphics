package lorenz875013.a07.Shapes;

import cgtools.Vec3;
import lorenz875013.a07.Materials.Material;
import lorenz875013.a07.RayTracer.Hit;
import lorenz875013.a07.RayTracer.Ray;

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
        double b = 2 * dotProduct(subtract(r.origin, center), r.normDirection);
        double c = dotProduct(subtract(r.origin, center), subtract(r.origin, center)) - (radius * radius);

        /** Vec3 x = new Vec3(a, b, c); **/

        double discriminant = (b * b) - (4 * a * c);
        double t;
        if (discriminant > 0) {
            /** which of the two hits of the sphere is closer? return the closest hit! **/
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            if(t2 > r.min && t2 < r.max) {
                Vec3 hitVec = r.pointAt(t2);
                Vec3 hitNormVec = normalize(subtract(hitVec, this.center));
                Hit hit = new Hit(t2, hitVec, hitNormVec, material);
                return hit;
            } else if (t1 > r.min && t1 < r.max) {
                Vec3 hitVec = r.pointAt(t1);
                Vec3 hitNormVec = normalize(subtract(hitVec, this.center));
                Hit hit = new Hit(t1, hitVec, hitNormVec, material);
                return hit;
            }
        }
        return null;
    }
}
