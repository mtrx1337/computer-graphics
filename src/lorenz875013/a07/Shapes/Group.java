package lorenz875013.a07.Shapes;

import lorenz875013.a07.RayTracer.Hit;
import lorenz875013.a07.RayTracer.Ray;
public class Group implements Shape {
    Shape[] shapes;

    public Group(Shape... shapes){
        this.shapes = shapes;
    }

    public Hit intersect(Ray r) {
        Hit closestHit = null;
        /** find hits on all objects in the scene **/
        for (Shape shape : shapes) {
            if(shape != null) {
                Hit intersect = shape.intersect(r);
                if (intersect != null && closestHit == null) {
                    closestHit = intersect;
                } else if(intersect != null && intersect.dirVecMultiplier < closestHit.dirVecMultiplier){
                        closestHit = intersect;
                    }
                }
            }
        return closestHit;
    }
}
