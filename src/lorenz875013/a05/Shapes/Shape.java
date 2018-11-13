package lorenz875013.a05.Shapes;

import lorenz875013.a05.Hit;
import lorenz875013.a05.Ray;

public interface Shape {
    Hit intersect(Ray r);
}
