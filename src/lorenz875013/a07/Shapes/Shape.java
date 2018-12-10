package lorenz875013.a07.Shapes;

import lorenz875013.a07.RayTracer.Hit;
import lorenz875013.a07.RayTracer.Ray;

public interface Shape {
    Hit intersect(Ray r);
}
