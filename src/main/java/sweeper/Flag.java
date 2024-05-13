package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().getX() * Ranges.getSize().getY();
    }

    Box get(Coordinate coordinate) {
        return flagMap.get(coordinate);
    }

    void setOpenedToBox(Coordinate coordinate) {
        flagMap.set(coordinate, Box.OPENED);
        countOfClosedBoxes--;
    }

    public void toggleFLagBox(Coordinate coordinate) {
        switch (flagMap.get(coordinate)) {
            case FLAG -> setClosedToBox(coordinate);
            case CLOSED -> setFlagToBox(coordinate);
        }
    }

    private void setClosedToBox(Coordinate coordinate) {
        flagMap.set(coordinate, Box.CLOSED);
    }

    private void setFlagToBox(Coordinate coordinate) {
        flagMap.set(coordinate, Box.FLAG);
    }

    int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coordinate coordinate) {
        flagMap.set(coordinate, Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coordinate coordinate) {
        if(flagMap.get(coordinate) == Box.CLOSED) {
            flagMap.set(coordinate, Box.OPENED);
        }
    }

    void setNoBombToFlagSafeBox(Coordinate coordinate) {
        if(flagMap.get(coordinate) == Box.FLAG) {
          flagMap.set(coordinate, Box.NO_BOMB);
        }
    }

    int getCountOfFlagBoxesAround(Coordinate coordinate) {
        int count = 0;
        for (Coordinate around: Ranges.getCoordinatesAround(coordinate)) {
            if (flagMap.get(around) == Box.FLAG) {
                count++;
            }
        }
        return count;
    }
}
