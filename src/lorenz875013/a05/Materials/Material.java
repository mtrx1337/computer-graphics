package lorenz875013.a05.Materials;

import lorenz875013.a05.Hit;
import lorenz875013.a05.Ray;
import lorenz875013.a05.ReflectionProperties;

public interface Material {
    ReflectionProperties properties(Ray r, Hit hit);
}
