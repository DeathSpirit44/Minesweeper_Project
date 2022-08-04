package minesweeper.src;

/**
 * Class that represent a move input by the user
 */
final class Play {
    final int[] YX;
    final String action;

    /**
     * Constructor
     * @param y coordinate y
     * @param x coordinate x
     * @param action name of the action
     */
    Play(int y, int x, String action) {
        int[] buffer = {y, x};
        this.YX = buffer.clone();
        this.action = action;
    }
}
