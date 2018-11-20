package lorenz875013.a06;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a06.Materials.BackgroundMaterial;
import lorenz875013.a06.Materials.DiffuseMaterial;
import lorenz875013.a06.Materials.ReflectionMaterial;
import lorenz875013.a06.RayTracer.Camera;
import lorenz875013.a06.RayTracer.RayTracer;
import lorenz875013.a06.Shapes.*;

import java.io.IOException;

public class Main {
    private static final double resMultiplier = 0.25;
    public static final int width = (int) (1920 * resMultiplier);
    public static final int height = (int) (1080 * resMultiplier);
    public static Image image = new Image(width, height);
    public static final Vec3 origin = new Vec3(0,0,0);
    public static final int samples = 6;
    public static final int traceDepth = 6;
    public static final double fieldOfViewAngle = Math.PI / 2;
    public static final Random random = new Random(1337);

    public static void main(String[] args) {
        Camera cam = new Camera(new Vec3(0,0,12), new Vec3(0, 0, 0), fieldOfViewAngle, width, height);
        Group scene = initializeScene();
        RayTracer raytracer = new RayTracer(width, height, image, traceDepth);
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
                new Vec3(0.0,0.2,0.3),
                new Vec3(0.0,0.0,0.0));
        DiffuseMaterial sphereDiffusing = new DiffuseMaterial(
                new Vec3(0.3,0.1,0.1),
                new Vec3(0,0,0));
        ReflectionMaterial sphereReflecting = new ReflectionMaterial(
                new Vec3(0.7,0.7,0.7),
                new Vec3(0,0,0),
                0.005);

        shapes[0] = new Background(backgroundMaterial);
        shapes[1] = new Plane(new Vec3(0,-3,0), new Vec3(0,1,0), planeMaterial);
        shapes[2] = new Sphere(new Vec3(0,-0.5,0), 2, sphereReflecting);
        int r = 5;
        int iterator = 3;
        for(int angle = 0; angle < 360; angle+=360/16){
            double x = r * Math.cos(angle * Math.PI / 180);
            double y = 2;
            double z = r * Math.sin(angle * Math.PI / 180);
            shapes[iterator] = new Sphere(new Vec3(x, y, z), 0.5, sphereDiffusing);
            //System.out.println(x + " " + y + " " + z + "\n");
            iterator++;
        }
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
