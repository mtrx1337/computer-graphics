package lorenz875013.a07.RayTracer;

import cgtools.Vec3;
import lorenz875013.a07.Materials.Material;

public class Hit implements Comparable<Hit> {
    public double dirVecMultiplier;
    public Vec3 hitVec;
    public Vec3 normVec;
    public Material material;

    public Hit(double dirVecMultiplier, Vec3 hitVec, Vec3 normVec, Material material){
        this.dirVecMultiplier = dirVecMultiplier;
        this.hitVec = hitVec;
        this.normVec = normVec;
        this.material = material;
    }

    @Override
    public int compareTo(Hit hit) {
        if(hit != null) {
            if (this.dirVecMultiplier > hit.dirVecMultiplier) {
                return 1;
            } else if (this.dirVecMultiplier < hit.dirVecMultiplier) {
                return -1;
            }
        }
        return 0;
    }
}
