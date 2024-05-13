package sweeper;

class Matrix {
    private Box[][] matrix;

    Matrix(Box defaultBox) {
        matrix = new Box[Ranges.getSize().getX()][Ranges.getSize().getY()];
        for (Coordinate coordinate : Ranges.getAllCoordinates()) {
            matrix[coordinate.getX()][coordinate.getY()] = defaultBox;
        }
    }

    Box get(Coordinate coordinate) {
        if (Ranges.inRange(coordinate)) {
            return matrix[coordinate.getX()][coordinate.getY()];
        }
        return null;
    }

    void set(Coordinate coordinate, Box box) {
        if (Ranges.inRange(coordinate)) {
            matrix[coordinate.getX()][coordinate.getY()] = box;
        }
    }
}
