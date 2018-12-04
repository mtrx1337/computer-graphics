package lorenz875013.a06.Materials;

import cgtools.Random;
import cgtools.Vec3;
import static cgtools.Vec3.*;

import lorenz875013.a06.RayTracer.Hit;
import lorenz875013.a06.RayTracer.Ray;

public class ReflectionMaterial implements Material {
    Vec3 albedo;
    Vec3 emission;
    double roughness;
    Ray reflectionRay;

    public ReflectionMaterial(Vec3 albedo, double roughness){
        this.albedo = albedo;
        this.emission = new Vec3(0,0,0);
        this.roughness = roughness;
    }

    public ReflectionProperties properties(Ray ray, Hit hit){
        Random random = new Random();
        double x,y,z;
        do {
            x = ((2 * roughness) * random.nextDouble()) - roughness;
            y = ((2 * roughness) * random.nextDouble()) - roughness;
            z = ((2 * roughness) * random.nextDouble()) - roughness;
        } while (1.0 < (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));

        /** r = d - 2 * (n * d) * n
         * r = reflectionNormVec
         * d = ray.normDirection
         * n = hit.normVec */
        Vec3 reflectionNormVec = subtract(ray.normDirection, multiply(2, multiply(dotProduct(ray.normDirection, hit.normVec), hit.normVec)));

        Vec3 ranReflectionNormVec = subtract(reflectionNormVec, new Vec3(x,y,z));

        if(dotProduct(ranReflectionNormVec, hit.normVec) > 0){
            this.reflectionRay = new Ray(hit.hitVec, ranReflectionNormVec, 0.0001, Double.POSITIVE_INFINITY);
        } else {
            this.reflectionRay = null;
        }

        ReflectionProperties properties = new ReflectionProperties(albedo, emission, reflectionRay);
        return properties;
    }
}
