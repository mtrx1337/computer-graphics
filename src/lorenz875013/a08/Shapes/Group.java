package lorenz875013.a08.Shapes;

import cgtools.Mat4;
import cgtools.Vec3;
import static cgtools.Vec3.*;
import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.RayTracer.Ray;
import lorenz875013.a08.RayTracer.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements Shape {
    Shape[] shapes;
    Transformation transformation;

    public Group(Transformation transformation, Shape... shapes){
        this.transformation = transformation;
        this.shapes = shapes;
    }

    public Hit intersect(Ray r) {

        Vec3 rayDir = transformation.transMatInv.transformDirection(r.normDirection);
        Vec3 rayOrigin = transformation.transMatInv.transformPoint(r.origin);

        /** find hits on all objects in the scene **/
        List<Hit> hits = new ArrayList<>();
        Ray tempRay = new Ray(rayOrigin, rayDir, r.min, r.max);
        for (Shape shape : shapes) {
            if(shape != null) {
                Hit intersect = shape.intersect(tempRay);
                if (intersect != null) {
                    hits.add(intersect);
                }
            }
        }

        Collections.sort(hits);
        Hit closestHit = null;
        if(hits.size() > 0) {
            closestHit = transformHit(hits.get(0));
        }
        return closestHit;
    }

    public Hit transformHit(Hit hit){
        Vec3 transNormVec = normalize(transformation.transMatInvTransposed.transformDirection(hit.normVec));
        Vec3 transHitVec= transformation.transformationMat.transformPoint(hit.hitVec);
        return new Hit(hit.dirVecMultiplier, transHitVec, transNormVec, hit.material);
    }
}
