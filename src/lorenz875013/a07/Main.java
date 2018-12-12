package lorenz875013.a07;

import cgtools.Mat4;
import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a07.Materials.BackgroundMaterial;
import lorenz875013.a07.Materials.DiffuseMaterial;
import lorenz875013.a07.Materials.ReflectionMaterial;
import lorenz875013.a07.Materials.GlassMaterial;
import lorenz875013.a07.RayTracer.Camera;
import lorenz875013.a07.RayTracer.RayTracer;
import lorenz875013.a07.Shapes.*;
import lorenz875013.a07.Shapes.Cone;

import java.io.IOException;

public class Main {
    private static final double resMultiplier = 0.3;
    public static final int width = (int) (1920 * resMultiplier);
    public static final int height = (int) (1080 * resMultiplier);
    public static final Vec3 origin = new Vec3(0,0,0);
    public static final int samples = 3;
    public static final int traceDepth = 4;
    public static final double fieldOfViewAngle = Math.PI / 2;
    public static final Random random = new Random();

    public static void main(String[] args) {
        Mat4 transformation = Mat4.rotate(new Vec3(1,0,1), 0);
        transformation = transformation.multiply(Mat4.translate(new Vec3(0,2,14)));
        Camera cam = new Camera(transformation, fieldOfViewAngle, width, height);
        /*
        Image image = new Image(width, height);
        Group scene = initializeScene();
        RayTracer raytracer = new RayTracer(width, height, image, traceDepth);
        raytracer.raytrace(cam, scene, samples);
        write(image,"doc/a07-1.png");
        */
        Image image2 = new Image(width, height);
        RayTracer raytracer2 = new RayTracer(width, height, image2, traceDepth);
        Group scene2 = initializeScene2();
        raytracer2.raytrace(cam, scene2, samples);
        write(image2,"doc/a07-2.png");
    }

    /**
     * creates the objects and shapes and adds them to a shape array
     * also creates the materials used for the shapes
     * @return the array of shapes
     */
    private static Group initializeScene(){
        int max = 50;
        Shape[] shapes = new Shape[max];
        BackgroundMaterial backgroundMaterial = new BackgroundMaterial(new Vec3(1,1,1));
        DiffuseMaterial planeMaterial = new DiffuseMaterial(
                new Vec3(0.0,0.2,0.3),
                new Vec3(0.0,0.0,0.0));
        DiffuseMaterial sphereDiffusing = new DiffuseMaterial(
                new Vec3(0.3,0.1,0.1),
                new Vec3(0,0,0));
        ReflectionMaterial sphereReflecting = new ReflectionMaterial(
                new Vec3(0.9,0.9,0.9),
                0.0);
        GlassMaterial sphereGlass = new GlassMaterial(
                new Vec3(1,1,1),
                0.1,
                1.6
        );

        shapes[0] = new Background(backgroundMaterial);
        shapes[1] = new Plane(new Vec3(0,-3,0), new Vec3(0,1,0), planeMaterial);
        shapes[2] = new Sphere(new Vec3(0,-0.5,-1), 2, sphereDiffusing);
        shapes[3] = new Sphere(new Vec3(2,-0.5,1), 1.5, sphereReflecting);
        shapes[4] = new Sphere(new Vec3(-2,-0.5,1), 1.5, sphereGlass);
        int r = 4;
        int iterator = 5;
        double o = 0;
        for(int angle = 0; angle < 360; angle+=360/16){
            sphereReflecting = new ReflectionMaterial(
                    new Vec3(0.9,0.9,0.9),
                    o);
            double x = r * Math.cos(angle * Math.PI / 180);
            double y = 0.5;
            double z = r * Math.sin(angle * Math.PI / 180);
            shapes[iterator] = new Sphere(new Vec3(x, y, z), 0.5, sphereDiffusing);
            //System.out.println(x + " " + y + " " + z + "\n");
            iterator++;
            o += 0.005;
        }
        Group scene = new Group(shapes);
        return scene;
    }

    /**
     * creates the objects and shapes and adds them to a shape array
     * also creates the materials used for the shapes
     * @return the array of shapes
     */
    private static Group initializeScene2(){
        int max = 50;
        Shape[] shapes = new Shape[max];
        BackgroundMaterial backgroundMaterial = new BackgroundMaterial(new Vec3(1,1,1));
        DiffuseMaterial planeMaterial = new DiffuseMaterial(
                new Vec3(0.0,0.2,0.3),
                new Vec3(0.0,0.0,0.0));
        DiffuseMaterial sphereDiffusing = new DiffuseMaterial(
                new Vec3(0.5,0.1,0.5),
                new Vec3(0,0,0));
        ReflectionMaterial sphereReflecting = new ReflectionMaterial(
                new Vec3(0.3,0.3,0.4),
                0.0);
        GlassMaterial sphereGlass = new GlassMaterial(
                new Vec3(0.6,0.6,0.6),
                0.1,
                1.6
        );

        shapes[0] = new Background(backgroundMaterial);
        shapes[1] = new Plane(new Vec3(0,-3,0), new Vec3(0,1,0), planeMaterial);
        //shapes[2] = new Cone(new Vec3(0, 3, 0), new Vec3(0,-1, 0), Math.PI / 4, sphereDiffusing);
        shapes[2] =
        //shapes[3] = new Sphere(new Vec3(0,-0.5,0), 3, sphereGlass);
        shapes[3] = new Group(new Cylinder(new Vec3(0,-0.5,0), 8, 2, sphereDiffusing),
                              new Disk(new Vec3(0,-0.5,0), new Vec3(0,1,0), 2, sphereDiffusing),
                              new Disk(new Vec3(0,0,0), new Vec3(0,1,0), 2, sphereDiffusing));
        int r = 5;
        int iterator = 4;
        double x,z;
        double y = -3;
        for(int i = 0; i < 2; i++) {
            for (int angle = 0; angle < 360; angle += 360 / 16) {
                x = r * Math.cos(angle * Math.PI / 180);
                y = y + 0.2;
                z = r * Math.sin(angle * Math.PI / 180);
                shapes[iterator] = new Sphere(new Vec3(x, y, z), 0.5, sphereDiffusing);
                //System.out.println(x + " " + y + " " + z + "\n");
                iterator++;
            }
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
