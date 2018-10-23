package lorenz875013.a03;

import cgtools.Vec3;
import org.junit.Test;
import org.hamcrest.core.*;
import static junit.framework.TestCase.*;

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

        testIntersect(new Vec3(0,0,-2), 1, 0, Double.POSITIVE_INFINITY,
                new Vec3(0,0,0), new Vec3(0,0,-1), new Vec3(0,0,-1), new Vec3(0,0,1));

        testIntersect(new Vec3(0,0,-2), 1, 0, Double.POSITIVE_INFINITY,
                new Vec3(0,0,0), new Vec3(0,0,-1), null, null);

        testIntersect(new Vec3(0,-1,-2), 1, 0, Double.POSITIVE_INFINITY,
                new Vec3(0,0,0), new Vec3(0,0,-1), new Vec3(0,0,-2), new Vec3(0,1,0));

        testIntersect(new Vec3(0,0,-2), 1, 0, Double.POSITIVE_INFINITY,
                new Vec3(0,0,-4), new Vec3(0,0,-1), null, null);

        testIntersect(new Vec3(0,0,-4), 1, 0, 2,
                new Vec3(0,0,0), new Vec3(0,0,-1), null, null);
    }

    public void testIntersect(Vec3 c, double r, double t0, double t1,
                              Vec3 origin, Vec3 direction, Vec3 hitVec, Vec3 normVec){
        Sphere sphere1 = new Sphere(origin, r, Vec3.white);
        Ray ray1 = new Ray(origin, direction, t0, t1);
        Hit intersect = sphere1.intersect(ray1);
        System.out.println("normvec");
        assertEquals(intersect.normVec, normVec);
        System.out.println("hitvec");
        assertEquals(intersect.hitVec, hitVec);
    }
}