package lorenz875013.a08.RayTracer;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a08.Materials.ReflectionProperties;
import lorenz875013.a08.Shapes.Group;

import java.text.DecimalFormat;

import static cgtools.Vec3.*;

public class RayTracer {
    int width;
    int height;
    Image image;
    int maxTraceDepth;

    public RayTracer(int width, int height, Image image, int maxTraceDepth) {
        this.width = width;
        this.height = height;
        this.image = image;
        this.maxTraceDepth = maxTraceDepth;
    }

    /**
     * @param camera  origin vector of rays sent out by the camera
     * @param scene   contains the shapes and objects in the scene
     * @param samples the amount of super- (sub-) sampling that should be done for each pixel
     *                /**
     * @param camera  origin vector of rays sent out by the camera
     * @param scene   contains the shapes and objects in the scene
     * @param samples the amount of super- (sub-) sampling that should be done for each pixel
     */
    public void raytrace(Camera camera, Group scene, int samples) {
        Random random = new Random();

        int amountOfThreads = 1;
        //int amountOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Setting up " + amountOfThreads + " Threads");
        Thread[] threads = new Thread[amountOfThreads];

        int threadBlock = width / amountOfThreads;
        int lastBlock = width - (threadBlock * amountOfThreads);


        for (int i = 0; i < amountOfThreads; i++) {
            final int threadBlockWidth;
            final int core = i;
            if(i == amountOfThreads - 1){
                threadBlockWidth = threadBlock * (i + 1);
            } else {
                threadBlockWidth = threadBlock * (i + 1) + lastBlock;
            }
            threads[i] = new Thread(() -> {
                long timestamp = System.nanoTime();
                int samplesSquared = samples * samples;
                for (int x = threadBlock * core; x < threadBlockWidth; x++) {
                    for (int y = 0; y < height; y++) {
                        Vec3 color = new Vec3(0, 0, 0);
                        for (int j = 0; j < samples; j++) {
                            for (int k = 0; k < samples; k++) {
                                /** calculate new coordinates with randomness **/
                                double coordRanX = x + (j + random.nextDouble()) / samples;
                                double coordRanY = y + (k + random.nextDouble()) / samples;
                                Ray ray = camera.genRay(coordRanX, coordRanY);
                                Hit subPixelHit = scene.intersect(ray);
                                if (subPixelHit != null) {
                                    Vec3 radiance = calculateRadiance(scene, ray, maxTraceDepth, 0);
                                    color = add(color, radiance);
                                }
                            }
                        }
                        /** calculate the center value for each value of the color vector **/
                        color = divide(color, samplesSquared);
                        image.setPixel(x, y, color);
                    }
                }
                logRenderTime(core, timestamp);
            });
        }

        for (int i = 0; i < amountOfThreads; i++){
            System.out.println("Thread " + i + " starting");
            threads[i].start();
        }

        for (int i = 0; i < amountOfThreads; i++){
            try {
                threads[i].join();
            } catch (Exception e) {
                System.out.printf("something went wrong when joining threads");
            }
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
    private Vec3 calculateRadiance(Group scene, Ray ray, int maxDepth, int currentDepth) {
        if (currentDepth > maxDepth) {
            return black;
        }
        Hit hit = scene.intersect(ray);
        if (hit != null) {
            ReflectionProperties properties = hit.material.properties(ray, hit);
            if (properties.ray != null) {
                Vec3 toReturn = add(properties.emission, multiply(properties.albedo, calculateRadiance(scene, properties.ray, maxDepth, ++currentDepth)));
                return toReturn;
            } else {
                return properties.emission;
            }
        } else {
            return black;
        }
    }

    private void printStatus(int percent) {
        String progBar = "[";
        for (int i = 0; i < percent; i++) {
            progBar = progBar + "X";
        }
        for (int i = 0; i < 100 - percent; i++) {
            progBar = progBar + " ";
        }
        progBar = progBar + "]";
        System.out.println(progBar);
    }

    private void logRenderTime(int thread, long timestamp) {
        /*
        // print progress every 10 pixel rows
        if (x % 10 == 0) {
            printStatus(x * 100 / width);
        }
        */

        long rendertime = System.nanoTime() - timestamp;
        /** convert nanoseconds to milliseconds and cast it to an integer to floor the result **/
        /** then convert milliseconds to seconds and print it **/
        double timeMS = 1.0 * rendertime / 1000000;
        double timeS = 1.0 * rendertime / 1000000000;
        DecimalFormat df = new DecimalFormat("###.###");
        System.out.printf("Thread " + thread + " finished. Render time: " + df.format(timeMS) + " milliseconds or around " + df.format(timeS) + " seconds.\n");
    }
}
