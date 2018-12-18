package lorenz875013.a08.RayTracer;

import cgtools.Mat4;

public class Transformation {
    public Mat4 transformationMat;
    public Mat4 transMatInv;
    public Mat4 transMatTransposed;
    public Mat4 transMatInvTransposed;

    public Transformation(Mat4 transformationMat){
        this.transformationMat = transformationMat;
        this.transMatInv = transformationMat.invertFull();
        this.transMatTransposed = transformationMat.transpose();
        this.transMatInvTransposed = transformationMat.invertFull().transpose();
    }
}
