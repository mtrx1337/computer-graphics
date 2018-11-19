package lorenz875013.a06.Shapes;

import lorenz875013.a06.Hit;
import lorenz875013.a06.Ray;

import java.util.ArrayList;

public class Group implements Shape {
    Shape[] shapes;

    public Group(Shape[] shapes){
        this.shapes = shapes;
    }

    public Hit intersect(Ray r) {
        /** find hits on all objects in the scene **/
        ArrayList<Hit> hits = new ArrayList<Hit>();
        for (Shape shape : shapes) {
            if(shape != null) {
                Hit intersect = shape.intersect(r);
                if (intersect != null) {
                    hits.add(intersect);
                }
            }
        }

        /** find closest hit **/
        Hit closestHit = hits.get(0);
        for (Hit hit : hits){
            if(hit.dirVecMultiplier < closestHit.dirVecMultiplier){
                closestHit = hit;
            }
        }

        return closestHit;
    }
}
