package tramberend7215.a01;

import cgtools.Vec3;
import static cgtools.Vec3.*;
import java.io.IOException;
import java.util.function.*;
import tramberend7215.Image;

public class Main {

    public static void main(String[] args) {
        final int width = 160;
        final int height = 90;

        Image image = new Image(width, height);

        class ConstantColor {
            Vec3 color;

            ConstantColor(Vec3 color) {
                this.color = color;
            }

            Vec3 pixelColor(double x, double y) {
                return color;
            }
        }

        ConstantColor allGray = new ConstantColor(Vec3.gray);
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                image.setPixel(x, y, allGray.pixelColor(x, y));
            }
        }
        write(image, "doc/a01.png");
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
