package lorenz875013.a04;

import cgtools.Vec3;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SphereTest {
    int width;
    int height;

    void setUp() {
        this.width = 720;
        this.height = 480;
    }

    @Test
    public void intersect() {
        setUp();
        double camFOV = Math.PI /2;
        Camera camera = new Camera(camFOV, width, height);

        testIntersect(
                /** middle **/
                new Vec3(0,0,-2),
                /** radius **/
                1,
                /** origin **/
                new Vec3(0,0,0),
                /** direction **/
                new Vec3(0,0,-1),
                /** t min **/
                0,
                /** t max **/
                Double.POSITIVE_INFINITY,
                /** hitvec **/
                new Vec3(0,0,-1),
                /** normvec **/
                new Vec3(0,0,1));

        testIntersect(
                new Vec3(0,0,-2),
                1,
                new Vec3(0,0,0),
                new Vec3(0,1,-1),
                0,
                Double.POSITIVE_INFINITY,
                null,
                null);

        testIntersect(
                new Vec3(0,-1,-2),
                1,
                new Vec3(0,0,0),
                new Vec3(0,0,-1),
                0,
                Double.POSITIVE_INFINITY,
                new Vec3(0,0,-2),
                new Vec3(0,1,0));

        testIntersect(
                new Vec3(0,0,-2),
                1,
                new Vec3(0,0,-4),
                new Vec3(0,0,-1),
                0,
                Double.POSITIVE_INFINITY,
                null,
                null);

        testIntersect(
                new Vec3(0,0,-4),
                1,
                new Vec3(0,0,0),
                new Vec3(0,0,-1),
                0,
                2,
                null,
                null);
    }

    public void testIntersect(
            Vec3 middle,
            double radius,
            Vec3 origin,
            Vec3 direction,
            double tMin,
            double tMax,
            Vec3 hitVec,
            Vec3 normVec){
        Sphere sphere = new Sphere(middle, radius, Vec3.white);
        Ray ray = new Ray(origin, direction, tMin, tMax);
        Hit hit = sphere.intersect(ray);
        if(hit != null){
            System.out.println("testing normvector");
            assertEquals(normVec, hit.normVec);
            System.out.println("tested normvector");
            System.out.println("testing hitvector");
            assertEquals(hitVec, hit.hitVec);
            System.out.println("tested hitvector");
        }
    }
}