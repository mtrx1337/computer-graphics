package lorenz875013.a03;

import cgtools.Vec3;

public class Hit {
    double rayParameter;
    Vec3 hitVec;
    Vec3 normVec;
    Vec3 surfaceColor;

    public Hit(double rayParameter, Vec3 hitVec, Vec3 normVec, Vec3 surfaceColor){
        this.rayParameter = rayParameter;
        this.hitVec = hitVec;
        this.normVec = normVec;
        this.surfaceColor = surfaceColor;
    }
}
