package lorenz875013.a06.Tests;

import cgtools.Vec3;
import lorenz875013.a06.Materials.ReflectionMaterial;
import lorenz875013.a06.RayTracer.Camera;
import lorenz875013.a06.RayTracer.Hit;
import lorenz875013.a06.RayTracer.Ray;
import lorenz875013.a06.Shapes.Sphere;
import org.junit.Test;

import static org.junit.Assert.*;

import static lorenz875013.a06.Main.fieldOfViewAngle;

public class SphereTests {

    public SphereTests() {
    }

    @Test
    public void testIntersect() {
        Camera cam = new Camera(new Vec3(0,0,7), new Vec3(0, 0, 0), fieldOfViewAngle, 1920, 1080);
        Ray ray1 = cam.shootRay(1920/2,1080/4);
        Ray ray2 = cam.shootRay(1920/2,1080/2);
        Ray ray3 = cam.shootRay(1920/2,1080/4*3);
        ReflectionMaterial material = new ReflectionMaterial(new Vec3(1,1,1), 0);
        Sphere sphere = new Sphere(new Vec3(0,0,0), 2, material);
        Hit hit1 = (sphere.intersect(ray1));
        Hit hit2 = (sphere.intersect(ray2));
        Hit hit3 = (sphere.intersect(ray3));
        assertEquals(null, hit1);
        assertNotNull(hit2);
        Vec3 emission = hit2.material.properties(ray2, hit2).emission;
        Vec3 albedo = hit2.material.properties(ray2, hit2).albedo;
        System.out.println(hit2.hitVec + " " + hit2.normVec + " " + hit2.dirVecMultiplier + " " + emission + " " + albedo);
        assertEquals(null, hit3);
    }
}
