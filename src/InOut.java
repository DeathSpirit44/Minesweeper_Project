package minesweeper.src;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class that handles the interface program human. It consists of input and ouput methods
 */
final class InOut {
    /**
     * Print the board associated to the minesweeper object given
     * @param ms minesweeper object
     */
    static void printBoard(@NotNull Minesweeper ms) {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        int cpt = 1;
        for (char[] line : ms.field) {
            System.out.print(cpt + "|");
            cpt++;
            for (char coord : line) {
                System.out.print(coord);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    /**
     * Ask the player the number of bombs he wants to play with
     *
     * @param heightField height of the field
     * @param widthField  width of the field
     * @return nb of mines
     */
    static int intputNbBombs(int heightField, int widthField) {
        final int nbCases = heightField * widthField;
        System.out.println("How many mines do you want on the field?");
        Scanner scanner = new Scanner(System.in);
        int nbMines = scanner.nextInt();
        while (nbMines < 1 || nbMines > nbCases - 2) {
            System.out.println("How many mines do you want on the field? (Between 1 and " + (nbCases - 2) + ")");
            nbMines = scanner.nextInt();
        }
        return nbMines;
    }

    /**
     * Ask the player his move (Ycord, Xcord, action)
     *
     * @param heightField height of the field
     * @param widthField  width of the field
     * @return [Ycoord, Xcoord, action] with m = mine and free = f
     */
    @Contract("_, _ -> new")
    static @NotNull Play inputMove(int heightField, int widthField) {
        Scanner scanner = new Scanner(System.in);
        final String[] set = {"free", "mine"};
        int Ycoord, Xcoord;
        boolean inHeight, inWidth, inSet;
        String stateInput;
        do {
            System.out.println("Set/unset marks or claim a cell as free:");
            Ycoord = scanner.nextInt();
            Xcoord = scanner.nextInt();
            stateInput = scanner.next().toLowerCase(Locale.ROOT);
            inHeight = Ycoord > 0 && Ycoord <= heightField;
            inWidth = Xcoord > 0 && Xcoord <= widthField;
            inSet = Arrays.asList(set).contains(stateInput);
        } while (!(inHeight && inWidth && inSet));
        return new Play(Ycoord - 1, Xcoord - 1, stateInput);
    }

    /**
     * Print the endgame message according to victory or defeat
     */
    static void printEndgame(@NotNull Minesweeper ms) {
        if (ms.victory) {
            System.out.println("Congratulations! You found all the mines!");
        } else {
            System.out.println("You stepped on a mine and failed!");
        }
    }
}
