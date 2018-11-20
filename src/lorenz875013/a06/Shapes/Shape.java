package lorenz875013.a06.Shapes;

import lorenz875013.a06.RayTracer.Hit;
import lorenz875013.a06.RayTracer.Ray;

public interface Shape {
    Hit intersect(Ray r);
}
