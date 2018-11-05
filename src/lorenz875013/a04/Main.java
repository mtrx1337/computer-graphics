package lorenz875013.a04;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;

import java.io.IOException;

import static cgtools.Vec3.*;

public class Main {
    public static void main(String[] args) {
        final int width = 1920;
        final int height = 1080;

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

        ConstantColor backgroundColor = new ConstantColor(Vec3.gray);
        ConstantColor sphereColor = new ConstantColor(Vec3.red);

        Vec3 origin = new Vec3(0.0,0.0,0.0);
        Camera cam = new Camera(Math.PI / 2, width, height);
        Sphere sphere = new Sphere(new Vec3(0, 0, -3), 1, sphereColor.color);
        Ray ray = new Ray(origin, new Vec3(0, 0, -1), 0, Double.POSITIVE_INFINITY);

        int samplingMultiplier = 8;
        int samplingMultiplierSquared = samplingMultiplier * samplingMultiplier;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vec3 color = new Vec3(0,0,0);
                for (int j = 0; j < samplingMultiplier; j++) {
                    for (int k = 0; k < samplingMultiplier; k++) {
                        /** create random values **/
                        double ranX = rn.nextDouble();
                        double ranY = rn.nextDouble();
                        /** calculate new coordinates with randomness **/
                        double coordRanX = x + (j + ranX) / samplingMultiplier;
                        double coordRanY = y + (k + ranY) / samplingMultiplier;
                        Hit subPixelHit = sphere.intersect(cam.shootRay(coordRanX, coordRanY));
                        if (subPixelHit != null) {
                            Vec3 subPixColor = lightSurface(subPixelHit.hitVec, subPixelHit.normVec, subPixelHit.surfaceColor);
                            color = add(subPixColor, color);
                        } else {
                            color = add(color, backgroundColor.color);
                        }
                    }
                }
                /** calculate the center value for each value of the color vector **/
                color = divide(color, samplingMultiplierSquared);
                image.setPixel(x, y, color);
            }
        }

        write(image,"doc/a03-one-sphere.png");
    }

    static Vec3 lightSurface(Vec3 position, Vec3 normal, Vec3 color){
        Vec3 lightDir = normalize(vec3(1, 1, 0.5));
        Vec3 ambient = multiply(0.1, color);
        Vec3 diffuse = multiply(0.9 * Math.max(0, dotProduct(lightDir, normal)), color);
        return add(ambient, diffuse);
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
