package sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() {
        bombMap =  new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    Box get(Coordinate coordinate) {
        return bombMap.get(coordinate);
    }

    private void placeBomb() {
        while (true) {
            Coordinate coordinate = Ranges.getRandomCoordinate();
            if (Box.BOMB == bombMap.get(coordinate)) {
                continue;
            }
            bombMap.set(coordinate, Box.BOMB);
            incrementNumbersAroundBomb(coordinate);
            break;
        }
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().getX() * Ranges.getSize().getY() / 2;
        if (totalBombs > maxBombs) {
            totalBombs = maxBombs;
        }
    }

    private void incrementNumbersAroundBomb(Coordinate coordinate) {
        for (Coordinate arrountCoordinate : Ranges.getCoordinatesAround(coordinate)) {
            if (Box.BOMB != bombMap.get(arrountCoordinate)) {
                bombMap.set(arrountCoordinate, bombMap.get(arrountCoordinate).getNextNumberBox());
            }
        }
    }

    public int getTotalBombs() {
        return totalBombs;
    }
}
