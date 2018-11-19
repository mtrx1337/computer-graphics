package lorenz875013.a06.Renderer;

import lorenz875013.Image;
import lorenz875013.a06.Shapes.Group;

public class Raytracer {
    private int width;
    private int height;
    private int maxTraceDepth;
    private int threadAmount;
    public Image image;

    public Raytracer(int width, int height, Image image, int maxTraceDepth, int threadAmount) {
        this.width = width;
        this.height = height;
        this.image = image;
        this.maxTraceDepth = maxTraceDepth;
        this.threadAmount = threadAmount;
    }

    /**
     * @param camera  origin vector of rays sent out by the camera
     * @param scene   contains the shapes and objects in the scene
     * @param samples the amount of super- (sub-) sampling that should be done for each pixel
     */
    void raytrace(Camera camera, Group scene, int samples) {
        long timestamp = System.nanoTime();

        RenderTask[] renderTasks = new RenderTask[threadAmount];
        int threadBlockSize = (height / threadAmount);
        int fromHeight = 0;
        int toHeight = threadBlockSize;

        for(int i = 0; i < threadAmount; i++) {
            if(i == threadAmount){
                /** compensate for the one line cut off at every thread to avoid calculating rows twice **/
                /** only needed for the last line of pixels, hence only setting the offset once for the last thread **/
                toHeight = height + 1;
                System.out.println(fromHeight + " + " + toHeight);
            }

            Renderer renderer = new Renderer(
                    /** only calculate to toHeight-1 so next thread will not calculate the same line again **/
                    fromHeight, toHeight - 1,
                    width, height,
                    samples, this.maxTraceDepth,
                    camera, scene);

            Thread renderThread = new Thread(renderer);

            renderTasks[i] = new RenderTask(renderer, renderThread);

            fromHeight += threadBlockSize;
            toHeight += threadBlockSize;
        }

        /** start all the render threads **/
        for(RenderTask renderTask: renderTasks){
            renderTask.start();
        }

        /** wait for all of them to finish **/
        for(RenderTask renderTask: renderTasks){
            Renderer renderer = renderTask.renderer;
            try {
                renderTask.join();
                for(int x = 0; x < renderer.width; x++) {
                    for(int y = renderer.fromHeight; y < renderer.toHeight; y++) {
                        this.image.setPixel(x, y, renderer.image[x][y]);
                    }
                }
            }
            catch (Exception e){
                System.out.printf(e.getMessage());
            }
        }

        long rendertime = System.nanoTime() - timestamp;
        //convert nanoseconds to milliseconds and cast it to an integer to floor the result
        System.out.printf("Renderer time: ~" + (int)(rendertime * 0.000001) + "ms \n");
    }


}
