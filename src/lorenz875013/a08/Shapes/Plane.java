package lorenz875013.a08.Shapes;

import cgtools.Vec3;
import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.Materials.Material;
import lorenz875013.a08.RayTracer.Ray;

import static cgtools.Vec3.*;

public class Plane implements Shape{
    Vec3 center;
    Vec3 dirVec;
    Material material;

    public Plane(Vec3 origin, Vec3 dirVec, Material material){
        this.center = origin;
        this.dirVec = dirVec;
        this.material = material;
    }

    public Hit intersect(Ray r) {
        double div = dotProduct(r.normDirection, dirVec);
        if(div != 0){
            /** find dirVecal vector multiplier/scalar t **/
            double t = dotProduct(subtract(this.center, r.origin), this.dirVec) / div;
            if(t > r.min && t < r.max) {
                /** find hit vector **/
                Vec3 hitVec = r.pointAt(t);
                Hit hit = new Hit(t, hitVec, dirVec, material);
                return hit;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
