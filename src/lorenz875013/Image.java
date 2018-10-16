package lorenz875013;

import cgtools.ImageWriter;
import cgtools.Vec3;

import java.io.IOException;

public class Image {
    private int width;
    private int height;
    private double gamma = 2.2;
    private double[] image;

    public Image(int width, int height) {
        this.image = new double[3 * (width * height)];
        this.width = width;
        this.height = height;
    }

    public void setPixel(int x, int y, Vec3 color) {
        int pixelAddress = 3 * (y * width + x);
        image[pixelAddress] = gammaCorrect(color.x);
        image[pixelAddress + 1] = gammaCorrect(color.y);
        image[pixelAddress + 2] = gammaCorrect(color.z);
    }

    /** part of task 2.2 - gamma correction **/
    public double gammaCorrect(double colorValue){
        colorValue = Math.pow(colorValue, 1 / this.gamma);
        return colorValue;
    }

    public void write(String filename) throws IOException {
        ImageWriter.write(filename, image, width, height);
    }
}
