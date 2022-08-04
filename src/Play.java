package minesweeper;

class Play {
    final int[] YX;
    final String action;

    Play(int[] YX, String action) {
        this.YX = YX;
        this.action = action;
    }

    Play(int y, int x, String action) {
        int[] buffer = {y, x};
        this.YX = buffer.clone();
        this.action = action;
    }


    String getAction() {
        return action;
    }
}
