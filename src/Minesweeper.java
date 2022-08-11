package minesweeper.src;


import java.util.Arrays;
import java.util.Random;

/**
 * Class that represent the state of a minesweeper
 */
final class Minesweeper {
    final char[][] field;
    final boolean[][] markedYX;
    final boolean[][] bombsYX;
    int height;
    int width;
    boolean defeat = false;
    boolean victory = false;
    private int nbExplored;
    private int nbMarked;
    private int nbBombs;

    /**
     * Constructor that init all the variable for an empty board
     * @param height height of the board
     * @param width width of the board
     */
    public Minesweeper(int height, int width) {
        this.height = height;
        this.width = width;
        this.field = createField(height, width);
        this.nbExplored = 0;
        this.markedYX = new boolean[height][width];
        this.nbMarked = 0;
        this.bombsYX = new boolean[height][width];
        this.nbBombs = 0;
    }

    /**
     * Create the field and initiate it with '.'
     *
     * @param height height of the field
     * @param width  width of the field
     * @return return an array representing the field
     */
    private static char[][] createField(int height, int width) {
        char[][] field = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = '.';
            }
        }
        return field;
    }


    public void setNbBombs(int nbBombs) {
        this.nbBombs = nbBombs;
    }

    /**
     * Fill the board (BombsYX) with bombs (true) according to the user input and ruleset
     * @param initCoord coordinates of the first input by user
     */
    void plantBombs(int[] initCoord) {
        Random random = new Random();
        int i = 1;
        while (i <= nbBombs) {
            int r1 = random.nextInt(height);
            int r2 = random.nextInt(width);
            if (!bombsYX[r1][r2] && (r1 != initCoord[0] || r2 != initCoord[1])) {
                bombsYX[r1][r2] = true;
                i++;
            }
        }
    }

    /**
     * Launch the discovery of the board according to the ruleset at coordinates given by play object
     * @param play action of the user
     */
    void discover(Play play) {
        int[] coord = play.YX;
        if (!bombsYX[coord[0]][coord[1]]) {
            discoverRecursive(coord);
        } else {
            defeat = true;
        }
        exploredVictory();
    }

    /**
     * recursive function that explores the board according to ruleset
     * @param coord coordinates to discover
     */
    private void discoverRecursive(int [] coord) {
        if (field[coord[0]][coord[1]] == '*') { // gere les cas on on supprime des drapeaux avec la discovery
            nbMarked--;
        }
        field[coord[0]][coord[1]] = '/';
        nbExplored++;
        if (isAroundEmpty(coord)) {
            for (int iHeight = (coord[0] != 0) ? -1 + coord[0] : 0; iHeight <= 1 + coord[0] && iHeight < height; iHeight++) {
                for (int iWidth = (coord[1] != 0) ? -1 + coord[1] : 0; iWidth <= 1 + coord[1] && iWidth < width; iWidth++) {
                    if (field[iHeight][iWidth] == '.' || field[iHeight][iWidth] == '*') {
                        int[] buffer = {iHeight, iWidth};
                        discoverRecursive(buffer);
                    }
                }
            }
        } else {
            field[coord[0]][coord[1]] = Character.forDigit(countBombs(coord), 10);
        }
    }

    /**
     * @param coord coordinates
     * @return true is all neighborhood cells do not contains mines
     */
    private boolean isAroundEmpty(int [] coord) {
        for (int iHeight = (coord[0] != 0) ? -1 + coord[0] : 0; iHeight <= 1 + coord[0] && iHeight < height; iHeight++) {
            for (int iWidth = (coord[1] != 0) ? -1 + coord[1] : 0; iWidth <= 1 + coord[1] && iWidth < width; iWidth++) {
                if (bombsYX[iHeight][iWidth]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param coord coordinates of a cell
     * @return the number of bombs in the neighborhood of the cell
     */
    private int countBombs(int [] coord) {
        int sumBombs = 0;
        for (int iHeight = (coord[0] != 0) ? -1 + coord[0] : 0; iHeight <= 1 + coord[0] && iHeight < height; iHeight++) {
            for (int iWidth = (coord[1] != 0) ? -1 + coord[1] : 0; iWidth <= 1 + coord[1] && iWidth < width; iWidth++) {
                if (bombsYX[iHeight][iWidth]) {
                    sumBombs++;
                }
            }
        }
        return sumBombs;
    }

    /**
     * marked or unmarked cell
     * @param coord coordinates of the cell that should be marked or unmarked
     */
    void mark(int [] coord) {
        final char value = field[coord[0]][coord[1]];
        if (value == '.' || value == '*') {
            field[coord[0]][coord[1]] = (value == '.') ? '*' : '.';
            markedYX[coord[0]][coord[1]] = !markedYX[coord[0]][coord[1]];
            if ((value == '.')) {
                ++nbMarked;
            } else {
                --nbMarked;
            }
            markedVictory();
        } else {
            System.out.println("You can't mark this cell !");
        }
    }

    /**
     * check if the user win according to ruleset and modifie this.victory accordingly
     */
    private void markedVictory() {
        if (nbBombs == nbMarked) {
            if (Arrays.deepEquals(markedYX, bombsYX)) {
                victory = true;
            }
        }
    }

    /**
     * check if the user win according to ruleset and modifie this.victory accordingly
     */
    private void exploredVictory() {
        if (nbExplored == height * width - nbBombs) {
            victory = true;
        }
    }

    /**
     * Launch a method according of the action of the user
     * @param play action of the user
     */
    void action(Play play) {
        switch (play.action) {
            case "free" -> discover(play);
            case "mine" -> mark(play.YX);
        }
    }
}