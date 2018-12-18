package lorenz875013.a08.Materials;

import cgtools.Vec3;
import lorenz875013.a08.RayTracer.Hit;
import lorenz875013.a08.RayTracer.Ray;

public class BackgroundMaterial implements Material {
    Vec3 emission;

    public BackgroundMaterial(Vec3 emission){
        this.emission = emission;
    }

    public ReflectionProperties properties(Ray ray, Hit hit){
        ReflectionProperties properties = new ReflectionProperties(null , emission, null);
        return properties;
    }
}
