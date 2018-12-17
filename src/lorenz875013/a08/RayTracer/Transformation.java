package lorenz875013.a08.RayTracer;

import cgtools.Mat4;

public class Transformation {
    Mat4 transformationMat;
    Mat4 transMatInv;
    Mat4 transMatTransposed;

    public Transformation(Mat4 transformationMat){
        transformationMat = transformationMat;
        transMatInv = transformationMat.invertFull();
        transMatTransposed = transformationMat.transpose();
    }
}
