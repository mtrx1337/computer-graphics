package lorenz875013.a08.RayTracer;


import cgtools.Mat4;
import cgtools.Vec3;

import static cgtools.Vec3.*;

public class Camera {
    Transformation transformation;
    double fov;
    int width;
    int height;

    public Camera(Transformation transformation, double fov, int width, int height) {
        this.transformation = transformation;
        this.fov = fov;
        this.width = width;
        this.height = height;
    }

    public Ray genRay(double x, double y) {
        /** calculate ray direction:
         *  source https://tramberend.beuth-hochschule.de/lehre/18-ws/bmi-cgg/lectures/03-raytracing/03-raytracing-deck.html#/49 **/
        double a = x - width / 2;
        double b = height / 2 - y;
        double c = -(height / 2) / (Math.tan(fov / 2));
        Vec3 direction = normalize(new Vec3(a, b, c));

        Vec3 finalDirection = transformation.transformationMat.transformDirection(direction);
        Vec3 finalOrigin = transformation.transformationMat.transformPoint(new Vec3(0,0,0));

        Ray ray = new Ray(finalOrigin, finalDirection, 0.0001, Double.POSITIVE_INFINITY);

        return ray;
    }
}
