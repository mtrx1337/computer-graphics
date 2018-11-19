package lorenz875013.a06.Shapes;

import lorenz875013.a06.Hit;
import lorenz875013.a06.Ray;

public interface Shape {
    Hit intersect(Ray r);
}
