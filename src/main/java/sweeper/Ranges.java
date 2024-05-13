package sweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ranges {
    private static Coordinate size;
    private static List<Coordinate> allCoordinates;
    private static Random rand = new Random();

    public static void setSize(Coordinate _size) {
        size = _size;
        allCoordinates = new ArrayList<>();
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                allCoordinates.add(new Coordinate(x, y));
            }
        }
    }

    public static Coordinate getSize() {
        return size;
    }

    public static List<Coordinate> getAllCoordinates() {
        return allCoordinates;
    }

    public static boolean inRange(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < size.getX()
                && coordinate.getY() >= 0 && coordinate.getY() < size.getY();
    }

    static Coordinate getRandomCoordinate() {
        return new Coordinate(rand.nextInt(size.getX()), rand.nextInt(size.getY()));
    }

    static List<Coordinate> getCoordinatesAround(Coordinate coordinate) {
        Coordinate around;
        List<Coordinate> coordinateList = new ArrayList<>();
        for (int x = coordinate.getX() - 1; x <= coordinate.getX() + 1; x++) {
            for (int y = coordinate.getY() - 1; y <= coordinate.getY() + 1; y++) {
                if (inRange(around = new Coordinate(x, y))) {
                    if (!around.equals(coordinate)) {
                        coordinateList.add(around);
                    }
                }
            }
        }
        return coordinateList;
    }
}
