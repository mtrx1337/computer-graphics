package lorenz875013.a08.Shapes;

import cgtools.Vec3;
import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.Materials.Material;
import lorenz875013.a08.RayTracer.Ray;

import static cgtools.Vec3.*;

public class Background implements Shape {
    Material material;

    public Background(Material material){
        this.material = material;
    }

    public Hit intersect(Ray r) {
        /** x vector **/
        Vec3 hitVec = r.pointAt(Double.POSITIVE_INFINITY);
        Vec3 hitNormVec = normalize(hitVec);
        Hit hit = new Hit(Double.POSITIVE_INFINITY, hitVec, hitNormVec, material);
        return hit;
    }
}
