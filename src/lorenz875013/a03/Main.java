package lorenz875013.a03;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int width = 720;
        final int height = 480;

        Image image = new Image(width, height);

        Random rn = new Random(1337);

        /** task 3.1 - Camera/Ray Tests **/


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                continue;
            }
        }

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
