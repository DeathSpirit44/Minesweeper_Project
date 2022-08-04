package minesweeper.src;

public class Main {
    public static void main(String[] args) {
        final int height = 9;
        final int width = 9;
        Minesweeper ms = new Minesweeper(height, width);
        ms.setNbBombs(InOut.intputNbBombs(height, width));
        InOut.printBoard(ms);
        Play init = InOut.inputMove(height, width);
        while (!"free".equals(init.action)) {
            ms.mark(init.YX);
            InOut.printBoard(ms);
            init = InOut.inputMove(height, width);
        }
        ms.plantBombs(init.YX);
        ms.discover(init);
        InOut.printBoard(ms);
//        Game is on
        while (!ms.victory && !ms.defeat) {
            Play input = InOut.inputMove(9, 9);
            ms.action(input);
            InOut.printBoard(ms);
        }
        InOut.printEndgame(ms);
    }
}
