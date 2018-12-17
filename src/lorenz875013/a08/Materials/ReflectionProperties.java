package lorenz875013.a08.Materials;

import cgtools.Vec3;
import lorenz875013.a08.RayTracer.Ray;

public class ReflectionProperties {
    public Vec3 albedo;
    public Vec3 emission;
    public Ray ray;

    /**
     * @param albedo how much light this material sends back with RGB channels
     * @param emission how much ambient light this material emits
     */
    public ReflectionProperties(Vec3 albedo, Vec3 emission, Ray ray){
        this.albedo = albedo;
        this.emission = emission;
        this.ray = ray;
    }
}
