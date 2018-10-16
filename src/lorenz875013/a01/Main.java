package lorenz875013.a01;

import cgtools.Random;
import cgtools.Vec3;
import static cgtools.Vec3.*;
import java.io.IOException;
import lorenz875013.Image;

public class Main {

    public static void main(String[] args) {
        //final int width = 160;
        //final int height = 90;
        final int width = 3440;
        final int height = 1440;

        Image image = new Image(width, height);

        /** task 1.4 - draw static color and write image **/

        class ConstantColor {
            Vec3 color;

            ConstantColor(Vec3 color) {
                this.color = color;
            }

            Vec3 pixelColor(double x, double y) {
                return color;
            }
        }

        ConstantColor backgroundColor = new ConstantColor(Vec3.gray);

        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                image.setPixel(x, y, backgroundColor.pixelColor(x, y));
            }
        }

        write(image, "doc/a01.png");

        /** task 1.5 - draw square in image center **/

        Image imageSquare = new Image(width, height);

        class ColoredSquare{
            Vec3 color;

            ColoredSquare(Vec3 color) { this.color = color; }

            Vec3 pixelColor(double x, double y){ return color;}
        }

        ConstantColor squareColor = new ConstantColor(Vec3.red);
        int imageMiddleX = width / 2;
        int imageMiddleY = height / 2;
        double squareSizeMultiplier = 0.8;
        int squareRadius;
        if(height > width){
            squareRadius = (int) (width * squareSizeMultiplier / 2);
        } else {
            squareRadius = (int) (height * squareSizeMultiplier / 2);
        }

        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                /** define left and right space around the square **/
                if(x > imageMiddleX - squareRadius && x < imageMiddleX + squareRadius) {
                    /** define top and bottom space around the square **/
                    if(y > imageMiddleY - squareRadius && y < imageMiddleY + squareRadius) {
                        image.setPixel(x, y, squareColor.pixelColor(x, y));
                    }
                }
            }
        }

        write(image, "doc/a01-square");

        /** task 1.6 - checkerboard background with square in image center **/

        Image imageCheckerBoard = new Image(width, height);

        // size in pixels on how wide one box of the checker board is
        // a checker board has 8 squares
        int checkerBoxSize = (squareRadius * 2) / 8;
        ConstantColor checkerColor = new ConstantColor(white);
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                int scaleX = x / 120;
                int scaleY = y / 120;
                /** define left and right space around the square and **/
                /** define top and bottom space around the square **/
                if(x > imageMiddleX - squareRadius && x < imageMiddleX + squareRadius &&
                   y > imageMiddleY - squareRadius && y < imageMiddleY + squareRadius) {
                    imageCheckerBoard.setPixel(x, y, squareColor.pixelColor(x, y));
                } else {
                    if ((scaleX + scaleY) % 2 == 0) {
                        imageCheckerBoard.setPixel(x, y, checkerColor.pixelColor(x, y));
                    } else {
                        imageCheckerBoard.setPixel(x, y, backgroundColor.pixelColor(x, y));
                    }
                }
            }
        }

        write(imageCheckerBoard, "doc/a01-checkered-background.png");

        /** task 2.1 - random circle creation **/

        Image circleImage = new Image(width, height);

        int circleAmount = 20;
        Circle[] circles = new Circle[circleAmount];
        Random rn = new Random(1337);

        for (int i = 0; i < circleAmount; i++){
            Vec3 circleColor = Vec3.vec3(rn.nextDouble(), rn.nextDouble(), rn.nextDouble());
            circles[i] = new Circle(rn.nextDouble() * 10, rn.nextInt(), rn.nextInt(), circleColor);
        }

        for(Circle circle : circles){
            drawCircle(circle, circleImage);
        }

    }

    static Image drawCircle(Circle circle, Image image){

        return image;
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
