package minesweeper;

import java.util.Arrays;
import java.util.Random;

class Minesweeper {
    final char[][] field;
    final boolean[][] markedYX;
    final boolean[][] bombsYX;
    private int nbExplored;
    int height;
    int width;
    boolean defeat = false;
    boolean victory = false;
    private int nbMarked;
    private int nbBombs;

    /**
     * Constructor for the minesweeper object and initiate the values with createField and PlantBombs methods
     *
     * @param height height of the board
     * @param width  width of the board
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
     * Plants the nbBombs in the field
     *
     * @return return an array containing the coordinates of all the bombs
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
     * @param play L'object Play associée à l'action du joueur
     * @return true si le joueur a perdu et false sinon
     */
    void discover(Play play) {
        int[] coord = play.YX;
        if (!bombsYX[coord[0]][coord[1]]) {
            discoverRecursive(coord);
        } else {
            defeat = true;
        }
        markedVictory();
    }

    private void discoverRecursive(int[] coord) {
        if (field[coord[0]][coord[1]] == '*') { // gere les cas on on supprime des drapeaux avec la discovery
            nbMarked --;
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
            nbExplored++;
        }


    }

    private boolean isAroundEmpty(int[] coord) {
        for (int iHeight = (coord[0] != 0) ? -1 + coord[0] : 0; iHeight <= 1 + coord[0] && iHeight < height; iHeight++) {
            for (int iWidth = (coord[1] != 0) ? -1 + coord[1] : 0; iWidth <= 1 + coord[1] && iWidth < width; iWidth++) {
                if (bombsYX[iHeight][iWidth]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countBombs(int[] coord) {
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

    void mark(int[] coord) {
        final char value = field[coord[0]][coord[1]];
        if (value == '.' || value == '*') {
            field[coord[0]][coord[1]] = (value == '.') ? '*' : '.';
            markedYX[coord[0]][coord[1]] = !markedYX[coord[0]][coord[1]];
            nbMarked = (value == '.') ? ++nbMarked : --nbMarked;
            markedVictory();
        } else {
            System.out.println("You can't mark this cell !");
        }
    }

    private void markedVictory() {
        if (nbBombs == nbMarked) {
            if (Arrays.deepEquals(markedYX, bombsYX)) {
                victory = true;
            }
        }
    }

    private void exploredVictory() {
        if (nbExplored == height * width - nbBombs) {
            victory = true;
        }
    }

    void action(Play play) {
        switch (play.action) {
            case "free":
                discover(play);
                break;
            case "mine":
                mark(play.YX);

        }
    }

    /**
     * check if all the mines have been found
     *
     * @return true if all the mines have been found
     */
//    boolean checkVictory() {
    // faire deux fontions pour les deux conditions de victoire
//        if (bombsXY.length == markedXY.size()) {
//            for (int[] bombsV :
//                    bombsXY) {
//                boolean check = false;
//                for (int[] markedV :
//                        markedXY) {
//                    if (Arrays.equals(bombsV, markedV)) {
//                        check = true;
//                        break;
//                    }
//                }
//                if (!check) {
//                    return false;
//                }
//            }
//            if (!(explored == field.length * field[0].length - bombsXY.length))
//                return false;
//            return true;
//    }

    /**
     * ask the player where he wants to mark / unmark a case
     */

//    void mark() {
//        boolean number = true;
//        while (number) {
//            Play input = InOut.inputMove(field.length, field[0].length);
//            int[] coord = {input.getYX()[0] - 1, input.getYX()[1] - 1};
//            switch (field[coord[0]][coord[1]]) {
//                case '*':
//                    field[coord[0]][coord[1]] = '.';
//                    number = false;
//                    for (int[] value :
//                            markedXY) {
//                        if (Arrays.equals(coord, value)) {
//                            markedXY.remove(value);
//                            break;
//                        }
//                    }
//                    break;
//                case '.':
//                    field[coord[0]][coord[1]] = '*';
//                    number = false;
//                    markedXY.add(coord.clone());
//                    break;
//                default:
//                    System.out.println("This case is not valid");
//            }
//
//        }
//    }

    /**
     * fill fill the board with the near bomb number and remove bombs
     */
//    void countBombs() {
//        final int height = field.length;
//        final int width = field[0].length;
//        int cptBombs;
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                cptBombs = 0;
//                if (field[i][j] == 'X') {
//                    continue;
//                } else {
//                    for (int k = i - 1; k <= i + 1; k++) {
//                        for (int l = j - 1; l <= j + 1; l++) {
//                            if (k < 0 || l < 0 || k > height - 1 || l > height - 1) {
//                                continue;
//                            } else {
//                                if (field[k][l] == 'X') {
//                                    cptBombs++;
//                                }
//                            }
//                        }
//                    }
//                }
//                if (cptBombs != 0) {
//                    field[i][j] = Character.forDigit(cptBombs, 10);
//                }
//            }
//        }
//        for (int[] coord :
//                bombsXY) {
//            field[coord[0]][coord[1]] = '.';
//        }
//    }
}