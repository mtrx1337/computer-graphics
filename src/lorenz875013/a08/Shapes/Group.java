package lorenz875013.a08.Shapes;

import cgtools.Mat4;
import cgtools.Vec3;
import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.RayTracer.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements Shape {
    Shape[] shapes;
    Mat4 transformation;

    public Group(Mat4 transformation, Shape... shapes){
        this.transformation = transformation;
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
        if(transformation != null){
            transformHit(closestHit);
        }
        return closestHit;
    }

    public Hit transformHit(Hit hit){
        Vec3 hitVec = hit.hitVec;
        Vec3 normVec = hit.normVec;
        Vec3 transHitVec= transformation.transformPoint(hitVec);
        Vec3 transNormVec = transformation.transformDirection(normVec);
        return new Hit(hit.dirVecMultiplier, transHitVec, transNormVec, hit.material);
    }
}
