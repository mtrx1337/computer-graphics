package lorenz875013.a04;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;

import java.io.IOException;
import java.util.ArrayList;

import static cgtools.Vec3.*;

public class Main {
    static final int width = 1920;
    static final int height = 1080;
    static Image image1 = new Image(width, height);
    static Image image2 = new Image(width, height);
    static final Vec3 origin = new Vec3(0.0,0.0,0.0);
    static final int samples = 8;

    public static void main(String[] args) {
        Camera cam = new Camera(Math.PI / 2, width, height);
        //Group scene1 = initializeScene1();
        //raytrace(image1, cam, scene1, samples);
        //write(image1,"doc/a04-3-spheres.png");
        Group scene2 = initializeScene2();
        raytrace(image2, cam, scene2, samples);
        write(image2,"doc/a04-scene.png");
    }

    static Group initializeScene1(){
        Shape[] shapes = new Shape[5];
        shapes[0] = new Background(new Vec3(0.0,0.0,0.0));
        shapes[1] = new Plane(new Vec3(0, -0.75, -4), new Vec3(0,1,0), new Vec3(0.2,0.2,0.2));
        shapes[2] = new Sphere(new Vec3(0, 0, -4), 1, new Vec3(0.4,0.1,0.1));
        shapes[3] = new Sphere(new Vec3(1, 0, -4), 1, new Vec3(0.1,0.4,0.1));
        shapes[4] = new Sphere(new Vec3(-1, 0, -4), 1, new Vec3(0.1,0.1,0.4));
        Group scene = new Group(shapes);
        return scene;
    }

    static Group initializeScene2(){
        Shape[] shapes = new Shape[11];
        Vec3 color = new Vec3(0.4, 0.1,0.4);
        shapes[0] = new Background(new Vec3(0.0,0.0,0.0));
        shapes[1] = new Plane(new Vec3(0, -2, 0), new Vec3(0,1,0), new Vec3(0.2,0.2,0.2));
        /** left eye **/
        shapes[2] = new Sphere(new Vec3(-1, 1, -4), 0.5, color);
        /** right eye **/
        shapes[3] = new Sphere(new Vec3(1, 1, -4), 0.5, color);
        /** nose **/
        shapes[5] = new Sphere(new Vec3(0, 0, -4), 0.5, color);
        /** left outer mouth **/
        shapes[6] = new Sphere(new Vec3(-1, -0.5, -4), 0.5, color);
        /** left inner mouth **/
        shapes[7] = new Sphere(new Vec3(-0.5, -1, -4), 0.5, color);
        /** middle mouth **/
        shapes[10] = new Sphere(new Vec3(0, -1.1, -4), 0.5, color);
        /** right inner mouth **/
        shapes[8] = new Sphere(new Vec3(0.5, -1, -4), 0.5, color);
        /** right outer mouth **/
        shapes[9] = new Sphere(new Vec3(1, -0.5, -4), 0.5, color);
        Group scene = new Group(shapes);
        return scene;
    }

    static void raytrace(Image image, Camera camera, Group scene, int samples){
        Random rn = new Random(1337);
        int samplesSquared = samples * samples;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vec3 color = new Vec3(0,0,0);
                for (int j = 0; j < samples; j++) {
                    for (int k = 0; k < samples; k++) {
                        /** create random values **/
                        double ranX = rn.nextDouble();
                        double ranY = rn.nextDouble();
                        /** calculate new coordinates with randomness **/
                        double coordRanX = x + (j + ranX) / samples;
                        double coordRanY = y + (k + ranY) / samples;
                        Hit subPixelHit = scene.intersect(camera.shootRay(coordRanX, coordRanY));
                        if (subPixelHit != null) {
                            Vec3 subPixColor = lightSurface(subPixelHit.hitVec, subPixelHit.normVec, subPixelHit.surfaceColor);
                            color = add(subPixColor, color);
                        }
                    }
                }
                /** calculate the center value for each value of the color vector **/
                color = divide(color, samplesSquared);
                image.setPixel(x, y, color);
            }
        }
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
