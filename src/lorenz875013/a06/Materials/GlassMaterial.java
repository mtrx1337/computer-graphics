package lorenz875013.a06.Materials;

import cgtools.Vec3;
import lorenz875013.a06.RayTracer.Hit;
import lorenz875013.a06.RayTracer.Ray;

import static cgtools.Vec3.*;

public class GlassMaterial implements Material {
    Vec3 albedo;
    Vec3 emission;
    double roughness;
    double refractionFactor;
    Ray reflectionRay;

    // TODO: roughness for in and outgoing normVecs
    public GlassMaterial(Vec3 color, double roughness, double refractionFactor){
        this.albedo = color;
        this.emission = new Vec3(0,0,0);
        this.roughness = roughness;
        this.refractionFactor = refractionFactor;
    }

    public ReflectionProperties properties(Ray ray, Hit hit) {

        double ingoingRefr= 1.0;
        Vec3 rayNormVec = ray.normDirection;
        Vec3 hitNormVec = hit.normVec;

        if (dotProduct(rayNormVec, hitNormVec) > 0) {
            // Strahl kommt von innen
            hitNormVec = multiply(hitNormVec, -1);
            double temp = ingoingRefr;
            ingoingRefr = refractionFactor;
            refractionFactor = temp;
        }

        Vec3 dt;
        // Brechung findet statt
        if (cgtools.Random.random() > schlick(rayNormVec, hitNormVec, ingoingRefr, refractionFactor)) {
            //TransmissionsAnteil
            dt = refract(rayNormVec, hitNormVec, ingoingRefr, refractionFactor);
        } else {
            // ReflexionsAnteil
            dt = reflect(rayNormVec, hitNormVec);
        }

        this.reflectionRay = new Ray(hit.hitVec, dt, 0.0001, Double.POSITIVE_INFINITY);
        ReflectionProperties properties = new ReflectionProperties(emission, albedo, this.reflectionRay);
        return properties;
    }

    static Vec3 refract(Vec3 d, Vec3 hitNormVec, double n1, double n2) {
        double r = n1 / n2;
        double c = -dotProduct(hitNormVec, d);
        double subsqrt = 1 - (r * r) * (1 - (c * c));
        if (subsqrt < 0) {
            // Totalreflexion;
            return reflect(d, hitNormVec);
        } else {
            return add(multiply(r, d), multiply((r * c) - Math.sqrt(subsqrt), hitNormVec));
        }
    }

    static double schlick(Vec3 d, Vec3 n, double n1, double n2) {
        double reflexionCoefficient = ((n1 - n2) / (n1 + n2)) * ((n1 - n2) / (n1 + n2));
        return reflexionCoefficient + (1 - reflexionCoefficient) * Math.pow((1 + dotProduct(d, n)), 5);
    }

    static Vec3 reflect(Vec3 d, Vec3 n) {
        return subtract(d, (multiply(2, multiply(dotProduct(d, n), n))));
    }
}
