package lorenz875013.a06.Renderer;

public class RenderTask {
    public Renderer renderer;
    public Thread thread;

    public RenderTask(Renderer renderer, Thread thread){
        this.renderer = renderer;
        this.thread = thread;
    }
    public void start(){
        thread.start();
    }
    public void join() throws Exception{
        try {
            thread.join();
        } catch (Exception e){
            throw e;
        }
    }
}
