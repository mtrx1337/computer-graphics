package lorenz875013.a06.Materials;

import lorenz875013.a06.RayTracer.Hit;
import lorenz875013.a06.RayTracer.Ray;

public interface Material {
    ReflectionProperties properties(Ray r, Hit hit);
}
