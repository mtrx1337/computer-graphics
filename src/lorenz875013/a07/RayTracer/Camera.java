package lorenz875013.a07.RayTracer;

import cgtools.Mat4;
import cgtools.Vec3;
import static cgtools.Vec3.*;

public class Camera {
    Vec3 origin;
    double fov;
    int width;
    int height;
    Mat4 directionMat;

    public Camera(Mat4 coords, double fov, int width, int height) {
        this.origin = origin;
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.directionMat = directionMat;
    }

    public Ray genRay(double x, double y) {
        /** calculate ray direction:
         *  source https://tramberend.beuth-hochschule.de/lehre/18-ws/bmi-cgg/lectures/03-raytracing/03-raytracing-deck.html#/49 **/
        double a = x - width / 2;
        double b = height / 2 - y;
        double c = -(height / 2) / (Math.tan(fov / 2));
        Vec3 direction = normalize(new Vec3(a, b, c));

        Ray ray = new Ray(origin, direction, 0.0001, Double.POSITIVE_INFINITY);
        ray.normDirection = direction;

        return ray;
    }
}
