package lorenz875013.a06;

import cgtools.Vec3;
import lorenz875013.a06.Materials.Material;

public class Hit {
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
}
