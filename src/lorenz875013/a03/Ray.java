package lorenz875013.a03;

import cgtools.Vec3;

public class Ray {
    Vec3 origin;
    Vec3 normDir;
    double min;
    double max;

    public Ray(Vec3 origin, Vec3 normDir, double min, double max){
        this.origin = origin;
        this.normDir = normDir;
        this.min = min;
        this.max = max;
    }

    public Vec3 pointAt(double t){
        return origin.add(Vec3.multiply(t, normDir));
    }
}
