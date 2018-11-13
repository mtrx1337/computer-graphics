package lorenz875013.a05;

import cgtools.Vec3;

public class ReflectionProperties {
    Vec3 albedo;
    Vec3 emission;
    Ray ray;

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
