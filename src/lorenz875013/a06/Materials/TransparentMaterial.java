package lorenz875013.a06.Materials;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.a06.Hit;
import lorenz875013.a06.Ray;
import lorenz875013.a06.ReflectionProperties;

public class TransparentMaterial implements Material {
    Vec3 albedo;
    Vec3 emission;
    double roughness;
    Ray reflectionRay;

    public TransparentMaterial(Vec3 albedo, Vec3 emission, double roughness){
        this.albedo = albedo;
        this.emission = emission;
        this.roughness = roughness;
        this.roughness = roughness;
    }

    public ReflectionProperties properties(Ray ray, Hit hit){
        Random random = new Random(1337);
        this.roughness = roughness * 2;
        double x = (random.nextDouble() - 0.5) * roughness;
        double y = (random.nextDouble() - 0.5) * roughness;
        double z = (random.nextDouble() - 0.5) * roughness;
        Vec3 ranVec = new Vec3(
                hit.normVec.x + x,
                hit.normVec.y + y,
                hit.normVec.z + z);
        this.reflectionRay = new Ray(hit.hitVec, ranVec, 0, Double.POSITIVE_INFINITY);
        ReflectionProperties properties = new ReflectionProperties(albedo, emission, ray);
        return properties;
    }
}
