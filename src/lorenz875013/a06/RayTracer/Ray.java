package lorenz875013.a06.RayTracer;

import cgtools.Vec3;

import static cgtools.Vec3.*;

public class Ray {
    public Vec3 origin;
    public Vec3 normDirection;
    public double min;
    public double max;

    public Ray(Vec3 origin, Vec3 normDirection, double min, double max){
        this.origin = origin;
        this.normDirection = Vec3.normalize(normDirection);
        this.min = min;
        this.max = max;
    }

    public Vec3 pointAt(double t){
        return add(multiply(normDirection, t), origin);
    }
}