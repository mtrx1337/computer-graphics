package lorenz875013;

import cgtools.ImageWriter;
import cgtools.Vec3;

import java.io.IOException;

public class Image {
    int width;
    int height;
    double[] image;

    public Image(int width, int height) {
        this.image = new double[3 * (width * height)];
        this.width = width;
        this.height = height;
    }

    public void setPixel(int x, int y, Vec3 color) {
        int pixelAdress = 3 * (y * width + x);
        image[pixelAdress] = color.x;
        image[pixelAdress + 1] = color.y;
        image[pixelAdress + 2] = color.z;
    }

    public void write(String filename) throws IOException {
        ImageWriter.write(filename, image, width, height);
    }
}
