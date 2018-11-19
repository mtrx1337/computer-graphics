package lorenz875013.a06.Materials;

import lorenz875013.a06.Hit;
import lorenz875013.a06.Ray;
import lorenz875013.a06.ReflectionProperties;

public interface Material {
    ReflectionProperties properties(Ray r, Hit hit);
}
