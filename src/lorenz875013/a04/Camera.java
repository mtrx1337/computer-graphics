package lorenz875013.a04;

import cgtools.Vec3;

public class Camera {
    Vec3 origin = new Vec3(0, 0, 0);
    double fov;
    int width;
    int height;

    public Camera(double fov, int width, int height) {
        this.fov = fov;
        this.width = width;
        this.height = height;
    }

    public Ray shootRay(double x, double y) {
        /** calculate ray direction:
         *  source https://tramberend.beuth-hochschule.de/lehre/18-ws/bmi-cgg/lectures/03-raytracing/03-raytracing-deck.html#/49 **/
        double a = x - width / 2;
        double b = height / 2 - y;
        double c = 0 - (width / 2) / (Math.tan(fov / 2));
        Vec3 direction = new Vec3(a, b, c);

        Ray ray = new Ray(origin, direction, 0, Double.POSITIVE_INFINITY);
        ray.normDirection = direction;

        return ray;
    }
}
