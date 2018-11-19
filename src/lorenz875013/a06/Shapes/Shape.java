package lorenz875013.a06.Shapes;

import lorenz875013.a06.Renderer.Hit;
import lorenz875013.a06.Renderer.Ray;

public interface Shape {
    Hit intersect(Ray r);
}
