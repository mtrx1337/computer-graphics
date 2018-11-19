package lorenz875013.a06.Materials;

import cgtools.Vec3;
import lorenz875013.a06.Hit;
import lorenz875013.a06.Ray;
import lorenz875013.a06.ReflectionProperties;

public class BackgroundMaterial implements Material {
    Vec3 albedo = null;
    Vec3 emission = null;
    Ray scatteredRay = null;

    public BackgroundMaterial(Vec3 emission){
        this.emission = emission;
    }

    public ReflectionProperties properties(Ray ray, Hit hit){
        ReflectionProperties properties = new ReflectionProperties(albedo, emission, scatteredRay);
        return properties;
    }
}
