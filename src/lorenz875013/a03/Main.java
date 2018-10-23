package lorenz875013.a02;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;
import lorenz875013.a03.Camera;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int width = 1500;
        final int height = 500;

        Image image = new Image(width, height);

        Random rn = new Random(1337);

        /** task 3.1 - Camera/Ray Tests **/
        Camera camera = new Camera(Math.toDegrees(Math.PI / 2), width, height);
    }

    static void write(Image image, String filename) {
        try {
            image.write(filename);
            System.out.println("Wrote image: " + filename);
        } catch (IOException error) {
            System.out.println(String.format("Something went wrong writing: %s: %s", filename, error));
        }
    }
}
