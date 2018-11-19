package lorenz875013.a06.Materials;

import lorenz875013.a06.Renderer.Hit;
import lorenz875013.a06.Renderer.Ray;

public interface Material {
    ReflectionProperties properties(Ray r, Hit hit);
}
