package lorenz875013.a08.Materials;

import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.RayTracer.Ray;

public interface Material {
    ReflectionProperties properties(Ray r, Hit hit);
}
