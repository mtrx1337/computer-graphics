package lorenz875013.a06.Shapes;

import cgtools.Vec3;
import lorenz875013.a06.Renderer.Hit;
import lorenz875013.a06.Materials.Material;
import lorenz875013.a06.Renderer.Ray;

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
