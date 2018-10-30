package lorenz875013.a03;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int width = 720;
        final int height = 480;

        Image image = new Image(width, height);

        Random rn = new Random(1337);

        class ConstantColor {
            Vec3 color;

            ConstantColor(Vec3 color) {
                this.color = color;
            }

            Vec3 pixelColor(double x, double y) {
                return color;
            }
        }

        ConstantColor backgroundColor = new ConstantColor(Vec3.black);
        ConstantColor sphereColor = new ConstantColor(Vec3.red);

        Vec3 origin = new Vec3(0.0,0.0,0.0);

        Camera cam = new Camera(Math.PI / 2, width, height);
        Sphere sphere = new Sphere(new Vec3(0, 0, 3), 2, sphereColor.color);
        Ray ray = new Ray(origin, new Vec3(0, 0, -1), 0, Double.POSITIVE_INFINITY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Hit hit = sphere.intersect(cam.shootRay(x, y));
                if(hit != null){
                    image.setPixel(x, y, hit.surfaceColor);
                }
            }
        }
    }

    static void write(Image image, String filename) {
        try {
            image.write(filename);
            System.out.println("Wrote image: " + filename);
        } catch (IOException error) {
            System.out.println(String.format("Something went wrong writing: %s: %s", filename, error));
        }
    }
}
