package lorenz875013.a08.Shapes;

import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.RayTracer.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements Shape {
    Shape[] shapes;

    public Group(Shape... shapes){
        this.shapes = shapes;
    }

    public Hit intersect(Ray r) {
        /** find hits on all objects in the scene **/
        List<Hit> hits = new ArrayList<>();
        for (Shape shape : shapes) {
            if(shape != null) {
                Hit intersect = shape.intersect(r);
                if (intersect != null) {
                    hits.add(intersect);
                }
            }
        }

        Collections.sort(hits);
        Hit closestHit = hits.get(0);
        return closestHit;
    }
}
