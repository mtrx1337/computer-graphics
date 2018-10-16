package lorenz875013.a02;

import cgtools.Random;
import cgtools.Vec3;
import lorenz875013.Image;

import java.util.Arrays;

public class Main {
    final int width = 3440;
    final int height = 1440;
    /** task 2.1 - random circle creation **/

    Image circleImage = new Image(width, height);

    int circleAmount = 20;
    Circle[] circles = new Circle[circleAmount];
    Random rn = new Random(1337);

    for (int i = 0; i < circleAmount; i++){
        Vec3 circleColor = Vec3.vec3(rn.nextDouble(), rn.nextDouble(), rn.nextDouble());
        circles[i] = new Circle(rn.nextDouble() * 10, rn.nextInt(), rn.nextInt(), circleColor);
    }

    Arrays.sort(circles);

    for(int x = 0; x < width; x++) {
        for(int y = 0; y < height; y++) {
            for (Circle circle : circles){
                // distance from circle center to coordinate
                double distx = circle.x - x;
                double disty = circle.y - y;
                if(circle.radius * circle.radius <= (distx * distx) + disty * disty){
                    circleImage.setPixel(x, y, circle.color);
                }
            }
        }
    }
    write(circleImage, "doc/a02-discs.png");
}
