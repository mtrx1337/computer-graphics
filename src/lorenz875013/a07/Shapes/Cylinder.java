package lorenz875013.a07.Shapes;

import cgtools.Vec3;

import static cgtools.Vec3.*;

import lorenz875013.a07.Materials.Material;
import lorenz875013.a07.RayTracer.Hit;
import lorenz875013.a07.RayTracer.Ray;

public class Cylinder implements Shape {
    Vec3 center;
    double radius;
    double height;
    Material material;

    public Cylinder() {

    }

    public Hit intersect(Ray ray) {


        if (t > ray.min && t < ray.max) {
            Vec3 x = ray.pointAt(t);
            Vec3 n;

            // Punkt ist auf den Deckeln
            if (x.y > center.y + height) {
                return null;
            }
            if (x.y < center.y) {
                return null;
            }

            // Punkt ist auf dem Mantel
            //Vec3 newN = vec3(mid.x, x.y, mid.z);
            n = normalize(subtract(x, center));

            Hit hit = new Hit(t, x, n, material);

            return hit;
        } else {
            return null;
        }

        Vec3 x = ray.pointAt(t);

        if (x.y < 0 || x.y > height - 1 )
            return null;

        Vec3 vtemp = subtract(x, center);
        Vec3 v = normalize(vec3(vtemp.x, 0, vtemp.z));
        Vec3 n = vec3(v.x*height/radius,radius/height,v.z*height/radius);

        Hit hit = new Hit(t, x, n, material);

        return hit;
    }
}
