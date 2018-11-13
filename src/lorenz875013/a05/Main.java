package lorenz875013.a05;

import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a05.Materials.BackgroundMaterial;
import lorenz875013.a05.Materials.DiffuseMaterial;
import lorenz875013.a05.Shapes.*;

import java.io.IOException;

public class Main {
    static final int width = 1280;
    static final int height = 720;
    static Image image = new Image(width, height);
    static final Vec3 origin = new Vec3(0.0,0.0,0.0);
    static final int samples = 20;
    static final int traceDepth = 4;
    static final double fieldOfViewAngle = Math.PI / 2;

    public static void main(String[] args) {
        Camera cam = new Camera(origin, fieldOfViewAngle, width, height);
        Group scene = initializeScene();
        Raytracer raytracer = new Raytracer(width, height, image, traceDepth);
        raytracer.raytrace(cam, scene, samples);
        write(image,"doc/a05-diffuse-spheres.png");
    }

    /**
     * creates the objects and shapes and adds them to a shape array
     * also creates the materials used for the shapes
     * @return the array of shapes
     */
    static Group initializeScene(){
        Shape[] shapes = new Shape[5];
        BackgroundMaterial backgroundMaterial = new BackgroundMaterial(new Vec3(1,1,1));
        DiffuseMaterial planeMaterial = new DiffuseMaterial(
                new Vec3(0.2,0.1,0.1),
                new Vec3(0,0,0));
        DiffuseMaterial sphereMaterial1 = new DiffuseMaterial(
                new Vec3(0.7,0.2,0.2),
                new Vec3(0,0,0));
        DiffuseMaterial sphereMaterial2 = new DiffuseMaterial(
                new Vec3(1,1,1),
                new Vec3(0,0,0));
        DiffuseMaterial sphereMaterial3 = new DiffuseMaterial(
                new Vec3(0.2,0.2,0.7),
                new Vec3(0,0,0));

        shapes[0] = new Background(backgroundMaterial);
        shapes[1] = new Plane(new Vec3(0,-1,0), new Vec3(0,1,0), planeMaterial);
        shapes[2] = new Sphere(new Vec3(0,0,-4), 1, sphereMaterial1);
        shapes[3] = new Sphere(new Vec3(1,0,-4), 1, sphereMaterial2);
        shapes[4] = new Sphere(new Vec3(-1,0,-4), 1, sphereMaterial3);
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
