package lorenz875013.a05.Materials;

import cgtools.Random;
import cgtools.Vec3;
import static cgtools.Vec3.*;
import lorenz875013.a05.Hit;
import lorenz875013.a05.Ray;
import lorenz875013.a05.ReflectionProperties;

public class ReflectionMaterial implements Material {
    Vec3 albedo;
    Vec3 emission;
    double roughness;
    Ray reflectionRay;

    public ReflectionMaterial(Vec3 albedo, Vec3 emission, double roughness){
        this.albedo = albedo;
        this.emission = emission;
        this.roughness = roughness;
    }

    public ReflectionProperties properties(Ray ray, Hit hit){
        Random random = new Random(1337);
        this.roughness = roughness * 2;
        double x = (random.nextDouble() - 0.5) * roughness;
        double y = (random.nextDouble() - 0.5) * roughness;
        double z = (random.nextDouble() - 0.5) * roughness;
        Vec3 reflectionNormVec = subtract(ray.normDirection, multiply(2, multiply(multiply(ray.normDirection, hit.normVec), hit.normVec)));
        Vec3 ranVec = new Vec3(
                reflectionNormVec.x + x,
                reflectionNormVec.y + y,
                reflectionNormVec.z + z);

        this.reflectionRay = new Ray(hit.hitVec, ranVec, 0, Double.POSITIVE_INFINITY);
        ReflectionProperties properties = new ReflectionProperties(albedo, emission, ray);
        return properties;
    }
}
