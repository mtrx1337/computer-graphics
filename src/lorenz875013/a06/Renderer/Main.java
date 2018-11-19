package lorenz875013.a06.Renderer;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a06.Materials.BackgroundMaterial;
import lorenz875013.a06.Materials.DiffuseMaterial;
import lorenz875013.a06.Materials.ReflectionMaterial;
import lorenz875013.a06.Shapes.*;

import java.io.IOException;

public class Main {
    private static final double resMultiplier = 0.25;
    public static final int width = (int) (1920 * resMultiplier);
    public static final int height = (int) (1080 * resMultiplier);
    public static Image image = new Image(width, height);
    public static final Vec3 origin = new Vec3(0,0,0);
    public static final int samples = 3;
    public static final int traceDepth = 4;
    public static final int threadAmount = 20;
    public static final double fieldOfViewAngle = Math.PI / 2;
    public static final Random random = new Random(1337);

    public static void main(String[] args) {
        Camera cam = new Camera(new Vec3(0,0,7), fieldOfViewAngle, width, height);
        Group scene = initializeScene();
        Raytracer raytracer = new Raytracer(width, height, image, traceDepth, threadAmount);
        raytracer.raytrace(cam, scene, samples);
        write(image,"doc/a06-reflection-spheres.png");
    }

    /**
     * creates the objects and shapes and adds them to a shape array
     * also creates the materials used for the shapes
     * @return the array of shapes
     */
    static Group initializeScene(){
        int max = 20;
        Shape[] shapes = new Shape[max];
        BackgroundMaterial backgroundMaterial = new BackgroundMaterial(new Vec3(1,1,1));
        DiffuseMaterial planeMaterial = new DiffuseMaterial(
                new Vec3(0.2,0.1,0.1),
                new Vec3(0,0,0));
        DiffuseMaterial sphereDiffuse = new DiffuseMaterial(
                new Vec3(0.0,0.2,0.1),
                new Vec3(0,0,0));
        ReflectionMaterial reflectionMaterial = new ReflectionMaterial(
                new Vec3(0.7,0.7,0.7),
                new Vec3(0,0,0),
                0.0);

        shapes[0] = new Background(backgroundMaterial);
        shapes[1] = new Plane(new Vec3(0,-3,0), new Vec3(0,1,0), planeMaterial);
        shapes[2] = new Sphere(new Vec3(0,2,2), 0.5, sphereDiffuse);
        shapes[3] = new Sphere(new Vec3(0,-0.7,-2), 2, reflectionMaterial);
        Group scene = new Group(shapes);
        return scene;
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
