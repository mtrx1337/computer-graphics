package lorenz875013.a04;

import cgtools.Vec3;
import static cgtools.Vec3.*;

public class Plane implements Shape{
    Vec3 center;
    Vec3 dirVec;
    Vec3 color;

    public Plane(Vec3 origin, Vec3 dirVec, Vec3 color){
        this.center = origin;
        this.dirVec = dirVec;
        this.color = color;
    }

    public Hit intersect(Ray r) {
        double div = dotProduct(r.normDirection, dirVec);
        if(div != 0){
            /** find dirVecal vector multiplier/scalar t **/
            double t = (dotProduct(center, dirVec) - dotProduct(r.origin, center)) / div;
            if(t > r.min && t < r.max) {
                /** find hit vector **/
                Vec3 hitVec = r.pointAt(t);
                Hit hit = new Hit(t, hitVec, dirVec, color);
                return hit;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
