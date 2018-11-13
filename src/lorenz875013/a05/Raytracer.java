package lorenz875013.a05;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a05.Shapes.Group;
import lorenz875013.a05.Shapes.Shape;

import static cgtools.Vec3.*;

public class Raytracer {
    int width;
    int height;
    Image image;
    int maxTraceDepth;
    Random random = new Random(1337);

    public Raytracer(int width, int height, Image image, int maxTraceDepth) {
        this.width = width;
        this.height = height;
        this.image = image;
        this.maxTraceDepth = maxTraceDepth;
    }

    /**
     * @param camera  origin vector of rays sent out by the camera
     * @param scene   contains the shapes and objects in the scene
     * @param samples the amount of super- (sub-) sampling that should be done for each pixel
     */
    void raytrace(Camera camera, Group scene, int samples) {
        int samplesSquared = samples * samples;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vec3 color = new Vec3(0, 0, 0);
                for (int j = 0; j < samples; j++) {
                    for (int k = 0; k < samples; k++) {
                        /** create random values **/
                        double ranX = random.nextDouble();
                        double ranY = random.nextDouble();
                        /** calculate new coordinates with randomness **/
                        double coordRanX = x + (j + ranX) / samples;
                        double coordRanY = y + (k + ranY) / samples;
                        Ray ray = camera.shootRay(coordRanX, coordRanY);
                        Hit subPixelHit = scene.intersect(ray);
                        if (subPixelHit != null) {
                            Vec3 radiance = calculateRadiance(scene, ray, this.maxTraceDepth, 0);
                            color = add(color, radiance);
                        }
                    }
                }
                /** calculate the center value for each value of the color vector **/
                color = divide(color, samplesSquared);
                image.setPixel(x, y, color);
            }
            System.out.println(x + "/" + width);
        }
    }

    /**
     * calculates the radiance of shapes in the scene recursively and returns it
     *
     * @param scene        contains shapes
     * @param ray          the primary ray sent out by the camera, after the first recursion it becomes the ray sent
     *                     out by the last hit point
     * @param maxDepth     maximum depth of rays sent out from new hit points
     * @param currentDepth current depth of rays sent out
     * @return see radiance above
     */
    Vec3 calculateRadiance(Shape scene, Ray ray, int maxDepth, int currentDepth) {
        if (currentDepth > maxDepth) {
            return black;
        }
        /** if the material does not absorb light completely **/
        Hit secondaryHit = scene.intersect(ray);
        ReflectionProperties properties = secondaryHit.material.properties(ray, secondaryHit);
        if(properties.ray != null) {
            return add(properties.emission, multiply(properties.albedo, calculateRadiance(scene, properties.ray, maxDepth, ++currentDepth)));
        }
        return properties.emission;
    }
}
