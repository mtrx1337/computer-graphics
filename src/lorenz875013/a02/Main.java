package lorenz875013.a02;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        final int width = 3440;
        final int height = 1440;
        /** task 2.1 - random circle creation **/

        Image circleImage = new Image(width, height);

        int circleAmount = 300;
        Circle[] circles = new Circle[circleAmount];
        Random rn = new Random(1337);

        for (int i = 0; i < circleAmount; i++){
            int ranX = (int) (rn.nextDouble() * width);
            int ranY = (int) (rn.nextDouble() * height);
            double ranR;
            if (width < height) {
                ranR = rn.nextDouble() * width * 10;
            } else {
                ranR = rn.nextDouble() * height * 10;
            }
            System.out.println(ranX + " " + ranY + " " + (int) ranR);
            Vec3 circleColor = Vec3.vec3(rn.nextDouble(), rn.nextDouble(), rn.nextDouble());
            circles[i] = new Circle(ranR, ranX, ranY, circleColor);
        }

        Arrays.sort(circles);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                for (Circle circle : circles){
                    // distance from circle center to coordinate
                    double distx = Math.abs(circle.x - x);
                    double disty = Math.abs(circle.y - y);
                    if(circle.radius * circle.radius >= (distx * distx) + (disty * disty)) {
                        circleImage.setPixel(x, y, circle.color);
                    }
                }
            }
        }
        write(circleImage, "doc/a02-discs.png");
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
