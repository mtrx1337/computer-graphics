package lorenz875013.a06.Materials;

import cgtools.Random;
import cgtools.Vec3;
import static cgtools.Vec3.*;

import lorenz875013.a06.Main;
import lorenz875013.a06.RayTracer.Hit;
import lorenz875013.a06.RayTracer.Ray;

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
        Random random = Main.random;
        /** r = d - 2 * (n * d) * n
        * r = reflectionNormVec
        * d = ray.normDirection
        * n = hit.normVec
         */
        Vec3 reflectionNormVec = subtract(ray.normDirection, multiply(multiply(multiply(ray.normDirection, hit.normVec), hit.normVec), 2));
        double x,y,z;
        do {
            x = (random.nextDouble() - 0.5) * roughness * 2;
            y = (random.nextDouble() - 0.5) * roughness * 2;
            z = (random.nextDouble() - 0.5) * roughness * 2;
        } while (1.0 < (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));
        Vec3 ranReflectionNormVec = normalize(add(reflectionNormVec, new Vec3(x,y,z)));

        this.reflectionRay = new Ray(hit.hitVec, ranReflectionNormVec, 0.0001, Double.POSITIVE_INFINITY);
        ReflectionProperties properties = new ReflectionProperties(albedo, emission, reflectionRay);
        return properties;
    }
}
