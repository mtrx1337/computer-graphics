package lorenz875013.a08.Shapes;

import cgtools.Vec3;
import lorenz875013.a08.Materials.Material;
import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.RayTracer.Ray;

import static cgtools.Vec3.*;

public class Cylinder implements Shape {
    Vec3 center;
    double height;
    double radius;
    Material material;

    public Cylinder(Vec3 center, double height, double radius, Material material) {
        this.center = center;
        this.height = height;
        this.radius = radius;
        this.material = material;
    }

    public Hit intersect(Ray r) {
        Vec3 yzero = new Vec3(1,0,1);
        Vec3 rc = multiply(r.normDirection, yzero);
        Vec3 ro = multiply(r.origin, yzero);
        Vec3 ce = multiply(center, yzero);
        /** x vector **/
        double a = dotProduct(rc, rc);
        double b = 2 * dotProduct(subtract(ro, ce), rc);
        double c = dotProduct(subtract(ro, ce), subtract(ro, ce)) - (radius * radius);

        /** Vec3 x = new Vec3(a, b, c); **/

        double discriminant = (b * b) - (4 * a * c);
        if (discriminant >= 0) {
            /** which of the two hits of the sphere is closer? return the closest hit! **/
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            if(t2 > r.min && t2 < r.max) {
                Vec3 hitVec = r.pointAt(t2);
                if(hitVec.y > center.y + height || hitVec.y < center.y){
                    return null;
                } else {
                    // center = center aber mit center.y = hit.y
                    Vec3 cylinderCenter = new Vec3(center.x, hitVec.y, center.z);
                    Vec3 hitNormVec = normalize(subtract(hitVec, cylinderCenter));
                    Hit hit = new Hit(t2, hitVec, hitNormVec, material);
                    return hit;
                }
            } else if (t1 > r.min && t1 < r.max) {
                Vec3 hitVec = r.pointAt(t1);
                if(hitVec.y > center.y + height || hitVec.y < center.y){
                    return null;
                } else {
                    // center = center aber mit center.y = hit.y
                    Vec3 cylinderCenter = new Vec3(center.x, hitVec.y, center.z);
                    Vec3 hitNormVec = normalize(subtract(hitVec, cylinderCenter));
                    Hit hit = new Hit(t1, hitVec, hitNormVec, material);
                    return hit;
                }
            }
        }
        return null;
    }
}
