package sweeper;

public class Game {
    private final Bomb bomb;
    private final Flag flag;
    private GameState state;

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coordinate(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public GameState getState() {
        return state;
    }

    public Box getBox(Coordinate coordinate) {
        if (flag.get(coordinate) == Box.OPENED) {
            return bomb.get(coordinate);
        } else {
            return flag.get(coordinate);
        }
    }

    public void pressLeftButton(Coordinate coordinate) {
        if (gameOver()) {
            return;
        }
        openBox(coordinate);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
            }
        }
    }

    private void openBox(Coordinate coordinate) {
        switch (flag.get(coordinate)) {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coordinate); return;
            case FLAG: return;
            case CLOSED:
                switch (bomb.get(coordinate)) {
                    case ZERO: openBoxesAround(coordinate); return;
                    case BOMB: openBombs(coordinate); return;
                    default: flag.setOpenedToBox(coordinate);
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coordinate coordinate) {
        if (bomb.get(coordinate) != Box.BOMB) {
            if (flag.getCountOfFlagBoxesAround(coordinate) == bomb.get(coordinate).getNumber()) {
                for (Coordinate around : Ranges.getCoordinatesAround(coordinate)) {
                    if (flag.get(around) == Box.CLOSED) {
                        openBox(around);
                    }
                }
            }
        }
    }

    private void openBombs(Coordinate bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coordinate coordinate: Ranges.getAllCoordinates()) {
            if (bomb.get(coordinate) == Box.BOMB) {
                flag.setOpenedToClosedBombBox(coordinate);
            } else {
                flag.setNoBombToFlagSafeBox(coordinate);
            }
        }
    }

    private void openBoxesAround(Coordinate coordinate) {
        flag.setOpenedToBox(coordinate);
        for (Coordinate around: Ranges.getCoordinatesAround(coordinate)) {
                openBox(around);
        }
    }

    public void pressRightButton(Coordinate coordinate) {
        if (gameOver()) {
            return;
        }
        flag.toggleFLagBox(coordinate);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED) {
            return false;
        } else {
            start();
            return true;
        }
    }
}
