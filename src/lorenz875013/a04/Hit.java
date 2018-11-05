package lorenz875013.a04;

import cgtools.Vec3;

public class Hit {
    double dirVecMultiplier;
    Vec3 hitVec;
    Vec3 normVec;
    Vec3 surfaceColor;

    public Hit(double dirVecMultiplier, Vec3 hitVec, Vec3 normVec, Vec3 surfaceColor){
        this.dirVecMultiplier = dirVecMultiplier;
        this.hitVec = hitVec;
        this.normVec = normVec;
        this.surfaceColor = surfaceColor;
    }
}
