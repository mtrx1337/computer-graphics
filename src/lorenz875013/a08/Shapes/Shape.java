package lorenz875013.a08.Shapes;

import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.RayTracer.Ray;

public interface Shape {
    Hit intersect(Ray r);
}
