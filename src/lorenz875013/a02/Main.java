package lorenz875013.a02;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int width = 160;
        final int height = 90;
        /** task 2.1 - random circle creation **/

        Image circleImage = new Image(width, height);

        int circleAmount = 100;
        Circle[] circles = new Circle[circleAmount];
        Random rn = new Random(1337);

        /** set the circle coordinates and circle size **/
        for (int i = 0; i < circleAmount; i++) {
            int ranX = (int) (rn.nextDouble() * width);
            int ranY = (int) (rn.nextDouble() * height);
            double ranR;
            if (width < height) {
                ranR = rn.nextDouble() * width * 10;
            } else {
                ranR = rn.nextDouble() * height * 10;
            }
            Vec3 circleColor = Vec3.vec3(rn.nextDouble(), rn.nextDouble(), rn.nextDouble());
            circles[i] = new Circle(ranR, ranX, ranY, circleColor);
        }

        /** put smaller circles on top **/
        Arrays.sort(circles);

        int progress = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (Circle circle : circles) {
                    /** distance from circle center to coordinate **/
                    double distX = Math.abs(circle.x - x);
                    double distY = Math.abs(circle.y - y);
                    if (circle.radius * circle.radius >= (distX * distX) + (distY * distY)) {
                        circleImage.setPixel(x, y, circle.color);
                    }
                }
            }
            progress++;
            System.out.println(progress + " out of " + width);
        }

        write(circleImage, "doc/a02-discs.png");

        /** task 2.3 - anti aliasing **/

        Image circleImageAA = new Image(width, height);

        int samplingMultiplier = 10;
        int samplingMultiplierSquared = samplingMultiplier * samplingMultiplier;

        progress = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double r = 0.0;
                double g = 0.0;
                double b = 0.0;
                /** is this pixel in the radius of a circle? Go through each one and check...**/
                for (int j = 0; j < samplingMultiplier; j++) {
                    for (int k = 0; k < samplingMultiplier; k++) {
                        for (Circle circle : circles) {
                            /** create random values **/
                            double ranX = rn.nextDouble();
                            double ranY = rn.nextDouble();
                            /** calculate new coordinates with randomness **/
                            double coordRanX = x + (j + ranX) / samplingMultiplier;
                            double coordRanY = y + (k + ranY) / samplingMultiplier;
                            /** distance from circle center to coordinates from above **/
                            double distX = Math.abs(coordRanX - circle.x);
                            double distY = Math.abs(coordRanY - circle.y);
                            if ((circle.radius * circle.radius) >= (distX * distX) + (distY * distY)) {
                                /** add the values of the color vector
                                 * to the color values created above
                                 * to later calculate the center value */
                                r = circle.color.x;
                                g = circle.color.y;
                                b = circle.color.z;
                                /** when a fitting circle  was found, break the loop
                                 * to not add other (below) circles in **/
                                break;
                            }
                        }
                    }
                }
                /** calculate the center value for each value of the color vector **/
                r = r / samplingMultiplierSquared;
                g = g / samplingMultiplierSquared;
                b = b / samplingMultiplierSquared;
                /** set final pixel vector and write it to the image **/
                circleImageAA.setPixel(x, y, new Vec3(r, g, b));
                //System.out.println(r + " " + g + " " + b);
            }
            progress++;
            System.out.println(progress + " out of " + width);
        }
        write(circleImageAA, "doc/a02-supersampling.png");
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
