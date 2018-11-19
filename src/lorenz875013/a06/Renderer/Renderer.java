package lorenz875013.a06.Renderer;

import cgtools.Vec3;
import lorenz875013.a06.Materials.ReflectionProperties;
import lorenz875013.a06.Shapes.Group;
import lorenz875013.a06.Shapes.Shape;

import static cgtools.Vec3.*;

public class Renderer implements Runnable{
    private int samples;
    private int maxTraceDepth;
    private Camera camera;
    private Group scene;
    private int ID;
    public int width;
    public int height;
    public int fromHeight;
    public int toHeight;
    public Vec3[][] image;

    public Renderer(int fromHeight, int toHeight,
                    int width, int height,
                    int samples, int maxTraceDepth,
                    Camera camera, Group scene, int ID){
        this.fromHeight = fromHeight;
        this.toHeight = toHeight;
        this.width = width;
        this.height = height;
        this.samples = samples;
        this.maxTraceDepth = maxTraceDepth;
        this.camera = camera;
        this.image = new Vec3[width][height];
        this.scene = scene;
        this.ID = ID;
        System.out.println("range: " + fromHeight + " " + toHeight);
    }

    @Override
    public void run() {
        int samplesSquared = samples * samples;
        for (int x = 0; x < this.width; x++) {
            for (int y = this.fromHeight; y < this.toHeight; y++) {
                Vec3 color = new Vec3(0, 0, 0);
                for (int j = 0; j < this.width; j++) {
                    for (int k = fromHeight; k < this.toHeight; k++) {
                        /** create random values **/
                        double ranX = Main.random.nextDouble();
                        double ranY = Main.random.nextDouble();
                        /** calculate new coordinates with randomness **/
                        double coordRanX = x + (j + ranX) / this.samples;
                        double coordRanY = y + (k + ranY) / this.samples;
                        Ray ray = this.camera.shootRay(coordRanX, coordRanY);
                        Hit subPixelHit = scene.intersect(ray);
                        if (subPixelHit != null) {
                            Vec3 radiance = calculateRadiance(scene, ray, this.maxTraceDepth, 0);
                            color = add(color, radiance);
                        }
                    }
                }
                /** calculate the center value for each value of the color vector **/
                color = divide(color, samplesSquared);
                this.image[x][y] = color;
            }
            // print progress every 10 pixel rows
            if (x % 10 == 0) { printStatus(x * 100 / width);}
        }
    }

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

    void printStatus(int percent){
        String threadNumber;
        if(this.ID < 10){
            threadNumber = "0" + this.ID;
        } else { threadNumber = "" + ID; }

        String progBar = "Thread: " + threadNumber + " [";
        for(int i = 0; i < percent; i++){
            progBar = progBar + "X";
        }
        for(int i = 0; i < 100 - percent; i++){
            progBar = progBar + " ";
        }
        progBar = progBar + "]";
        System.out.println(progBar);
    }
}
