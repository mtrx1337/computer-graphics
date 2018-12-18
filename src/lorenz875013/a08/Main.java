package lorenz875013.a08;

import cgtools.Mat4;
import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a08.Materials.BackgroundMaterial;
import lorenz875013.a08.Materials.DiffuseMaterial;
import lorenz875013.a08.Materials.ReflectionMaterial;
import lorenz875013.a08.Materials.GlassMaterial;
import lorenz875013.a08.RayTracer.Camera;
import lorenz875013.a08.RayTracer.RayTracer;
import lorenz875013.a08.RayTracer.Transformation;
import lorenz875013.a08.Shapes.*;
import lorenz875013.a08.Shapes.Cone;

import java.io.IOException;

public class Main {
    private static final double resMultiplier = 0.3;
    public static final int width = (int) (1920 * resMultiplier);
    public static final int height = (int) (1080 * resMultiplier);
    public static final Vec3 origin = new Vec3(0,0,0);
    public static final int samples = (int) (20 * resMultiplier);
    public static final int traceDepth = (int) (10 * resMultiplier);
    public static final double fieldOfViewAngle = Math.PI / 2;
    public static final Random random = new Random();

    public static void main(String[] args) {

        Mat4 camTransMat1 = Mat4.rotate(new Vec3(1,0,0), -33);
        camTransMat1 = camTransMat1.multiply(Mat4.translate(new Vec3(0,0,16)));
        Camera cam1 = new Camera(new Transformation(camTransMat1), fieldOfViewAngle, width, height);
        Image image1 = new Image(width, height);
        Group scene = initializeScene();
        RayTracer raytracer = new RayTracer(width, height, image1, traceDepth);
        raytracer.raytrace(cam1, scene, samples);
        write(image1,"doc/a08-1.png");

        /*
        Mat4 camTransMat2 = Mat4.rotate(new Vec3(1,0,0), -33);
        camTransMat2 = camTransMat2.multiply(Mat4.translate(new Vec3(0,0,8)));
        Camera cam2 = new Camera(new Transformation(camTransMat2), fieldOfViewAngle, width, height);
        Image image2 = new Image(width, height);
        RayTracer raytracer2 = new RayTracer(width, height, image2, traceDepth);
        Group scene2 = initializeScene2();
        raytracer2.raytrace(cam2, scene2, samples);
        write(image2,"doc/a08-2.png");
        */
    }

    /**
     * creates the objects and shapes and adds them to a shape array
     * also creates the materials used for the shapes
     * @return the array of shapes
     */
    private static Group initializeScene(){

        Mat4 sceneTransformation = Mat4.rotate(new Vec3(0,0,0), 0);
        sceneTransformation = sceneTransformation.multiply(Mat4.translate(new Vec3(0,0,0)));

        int max = 50;
        Shape[] shapes = new Shape[max];
        BackgroundMaterial backgroundMaterial = new BackgroundMaterial(new Vec3(1,1,1));
        DiffuseMaterial planeMaterial = new DiffuseMaterial(
                new Vec3(0.0,0.2,0.3),
                new Vec3(0.0,0.0,0.0));
        DiffuseMaterial bodyMaterial = new DiffuseMaterial(
                new Vec3(0.3,0.1,0.1),
                new Vec3(0,0,0));
        ReflectionMaterial eyeMaterial = new ReflectionMaterial(
                new Vec3(0.9,0.9,0.9),
                0.1);
        GlassMaterial glassMaterial = new GlassMaterial(
                new Vec3(1,1,1),
                0.0,
                1.6
        );

        shapes[0] = new Background(backgroundMaterial);
        shapes[1] = new Plane(new Vec3(0,-3,0), new Vec3(0,1,0), planeMaterial);

        /** main body */
        Mat4 cylinderTransformationMat =  Mat4.rotate(new Vec3(0,1,0), -90);
        cylinderTransformationMat =  cylinderTransformationMat.rotate(new Vec3(0,0,1), 45);
        cylinderTransformationMat = cylinderTransformationMat.multiply(Mat4.translate(new Vec3(0,4,0)));
        shapes[2] = new Group(new Transformation(cylinderTransformationMat),
                new Cylinder(new Vec3(0,0,0), 4, 2, bodyMaterial),
                new Disk(new Vec3(0,0,0), new Vec3(0,1,0), 2, bodyMaterial),
                new Disk(new Vec3(0,4,0), new Vec3(0,1,0), 2, bodyMaterial));

        Mat4 legTrans =  Mat4.rotate(new Vec3(0,0,0), 0);
        legTrans =  legTrans.rotate(new Vec3(0,0,0), 0);
        legTrans = legTrans.multiply(Mat4.translate(new Vec3(0,-3,0)));
        /** 4 legs */
        shapes[3] = new Group(new Transformation(legTrans),
                new Group(new Transformation(cylinderTransformationMat),
                    new Cylinder(new Vec3(1,0,2), 4, 0.3, bodyMaterial),
                    new Disk(new Vec3(1,0,2), new Vec3(0,1,0), 0.3, bodyMaterial),
                    new Disk(new Vec3(1,4,2), new Vec3(0,1,0), 0.3, bodyMaterial)),

                new Group(new Transformation(cylinderTransformationMat),
                    new Cylinder(new Vec3(-1,0,2), 4, 0.3, bodyMaterial),
                    new Disk(new Vec3(-1,0,2), new Vec3(0,1,0), 0.3, bodyMaterial),
                    new Disk(new Vec3(-1,4,2), new Vec3(0,1,0), 0.3, bodyMaterial)),

                new Group(new Transformation(cylinderTransformationMat),
                        new Cylinder(new Vec3(-1,0,-2), 4, 0.3, bodyMaterial),
                        new Disk(new Vec3(-1,0,-2), new Vec3(0,1,0), 0.3, bodyMaterial),
                        new Disk(new Vec3(-1,4,-2), new Vec3(0,1,0), 0.3, bodyMaterial)),

                new Group(new Transformation(cylinderTransformationMat),
                        new Cylinder(new Vec3(1,0,-2), 4, 0.3, bodyMaterial),
                        new Disk(new Vec3(1,0,-2), new Vec3(0,1,0), 0.3, bodyMaterial),
                        new Disk(new Vec3(1,4,-2), new Vec3(0,1,0), 0.3, bodyMaterial))
        );

        Mat4 headTrans =  Mat4.rotate(new Vec3(0,1,0), -90);
        headTrans =  headTrans.rotate(new Vec3(0,0,1), 45);
        headTrans = headTrans.multiply(Mat4.translate(new Vec3(0,4,0)));
        shapes[4] = new Group(new Transformation(headTrans),
                new Sphere(new Vec3(0,0,0), 1.5, bodyMaterial),
                new Sphere(new Vec3(-1,0,-2), 0.4, bodyMaterial),
                new Sphere(new Vec3(1,0,-2), 0.4, bodyMaterial),
                new Sphere(new Vec3(-1,1,-2), 0.4, bodyMaterial),
                new Sphere(new Vec3(1,1,-2), 0.4, bodyMaterial));


        Mat4 cylinderTransformationMat1 =  Mat4.rotate(new Vec3(0,0,0), 0);
        cylinderTransformationMat1 =  cylinderTransformationMat1.rotate(new Vec3(0,0,0), 0);
        cylinderTransformationMat1 = cylinderTransformationMat1.multiply(Mat4.translate(new Vec3(0,0,0)));

        Mat4 cylinderTransformationMat2 =  Mat4.rotate(new Vec3(0,0,0), 0);
        cylinderTransformationMat2 =  cylinderTransformationMat2.rotate(new Vec3(0,0,0), 0);
        cylinderTransformationMat2 = cylinderTransformationMat2.multiply(Mat4.translate(new Vec3(0,0,0)));

        Mat4 tailTrans =  Mat4.rotate(new Vec3(0,0,0), 0);
        tailTrans =  tailTrans.rotate(new Vec3(0,0,0), 0);
        tailTrans = tailTrans.multiply(Mat4.translate(new Vec3(3,0,-3)));

        /** 4 legs */
        shapes[5] = new Group(new Transformation(tailTrans),
                new Group(new Transformation(cylinderTransformationMat1),
                        new Cylinder(new Vec3(0,0,0), 4, 2, bodyMaterial),
                        new Disk(new Vec3(0,0,0), new Vec3(0,1,0), 2, bodyMaterial),
                        new Disk(new Vec3(0,4,0), new Vec3(0,1,0), 2, bodyMaterial)),

                new Group(new Transformation(cylinderTransformationMat2),
                        new Cylinder(new Vec3(0,0,0), 4, 2, bodyMaterial),
                        new Disk(new Vec3(0,0,0), new Vec3(0,1,0), 2, bodyMaterial),
                        new Disk(new Vec3(0,4,0), new Vec3(0,1,0), 2, bodyMaterial))
        );

        Group scene = new Group(new Transformation(sceneTransformation), shapes);
        return scene;
    }

    /**
     * creates the objects and shapes and adds them to a shape array
     * also creates the materials used for the shapes
     * @return the array of shapes
     */
    private static Group initializeScene2(){

        Mat4 sceneTrans = Mat4.rotate(new Vec3(1,0,0), -33);
        sceneTrans= sceneTrans.multiply(Mat4.translate(new Vec3(0,0,0)));

        int max = 100;
        Shape[] shapes = new Shape[max];
        BackgroundMaterial backgroundMaterial = new BackgroundMaterial(new Vec3(1,1,1));
        ReflectionMaterial planeMaterial = new ReflectionMaterial(
                new Vec3(0.8,0.8,0.8),
                0.1);
        DiffuseMaterial sphereDiffusing = new DiffuseMaterial(
                new Vec3(0.5,0.1,0.0),
                new Vec3(0,0,0));
        ReflectionMaterial sphereReflecting = new ReflectionMaterial(
                new Vec3(0.8,0.8,0.8),
                0.0);
        GlassMaterial sphereGlass = new GlassMaterial(
                new Vec3(0.6,0.6,0.6),
                0.0,
                1.6
        );

        shapes[0] = new Background(backgroundMaterial);
        shapes[1] = new Plane(new Vec3(0,-3,0), new Vec3(0,1,0), planeMaterial);
        //shapes[2] = new Cone(new Vec3(0, 3, 0), new Vec3(0,-1, 0), Math.PI / 4, sphereDiffusing);

        Mat4 cylinderTransformationMat =  Mat4.rotate(new Vec3(1,1,1), -33);
        cylinderTransformationMat = cylinderTransformationMat.multiply(Mat4.translate(new Vec3(0,-4,0)));
        shapes[2] = new Group(new Transformation(cylinderTransformationMat),
                new Cylinder(new Vec3(0,0,0), 4, 2, sphereDiffusing),
                //new Disk(new Vec3(0,0,0), new Vec3(0,1,0), 2, sphereDiffusing),
                new Disk(new Vec3(0,4,0), new Vec3(0,1,0), 2, sphereDiffusing));

        int r = 5;
        int iterator = 5;
        double x,z;
        double y = -3;
        double q = 0;
        for(int i = 0; i < 3; i++) {
            for (int angle = 0; angle < 360; angle += 360 / 16) {
                GlassMaterial sphereGlassColor = new GlassMaterial(
                        new Vec3(q,0.6,0.6),
                        0.0,
                        1.6
                );
                q += 0.025;
                x = r * Math.cos(angle * Math.PI / 180);
                y = y + 0.2;
                z = r * Math.sin(angle * Math.PI / 180);
                shapes[iterator] = new Sphere(new Vec3(x, y, z), 0.5, sphereGlassColor);
                //System.out.println(x + " " + y + " " + z + "\n");
                iterator++;
            }
        }
        Group scene = new Group(new Transformation(sceneTrans), shapes);
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
