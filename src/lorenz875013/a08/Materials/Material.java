package lorenz875013.a07.Materials;

import lorenz875013.a07.RayTracer.Hit;
import lorenz875013.a07.RayTracer.Ray;

public interface Material {
    ReflectionProperties properties(Ray r, Hit hit);
}
