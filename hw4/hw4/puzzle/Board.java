package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int size;
    private int[][] board;

    public Board(int[][] tiles) {
        int[][] cowmoo = tiles;
        board = new int[tiles.length][tiles.length];
        for (int i = 0; i < cowmoo.length; i++) {
            System.arraycopy(cowmoo[i], 0, board[i], 0, cowmoo[i].length);
        }
        size = tiles.length;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i > board[0].length || j > board[0].length) {
            throw new IndexOutOfBoundsException("i or j is out of bounds");
        }
        return board[i][j];
    }

    public int size() {
        return size;
    }

    /*
    @source: http://joshh.ug/neighbors.html
    @purpose: I sourced the provided neighbors method written
    by Josh Hug in the homework 4 website spec.
    It is designed to return the neighbors of the current board.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int wrong = 0;
        int correct = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != correct && board[i][j] != 0) {
                    wrong += 1;
                }
                correct += 1;
            }
        }
        return wrong;
    }

    public int manhattan() {
        int total = 0;
        int correct = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != correct && board[i][j] != 0) {
                    total += (Math.abs(((board[i][j] - 1) / board.length) - i))
                            + Math.abs((((board[i][j] - 1) % board.length) - j));
                }
                correct += 1;
            }
        }
        return total;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board x = (Board) y;
        if (this.size() != x.size()) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != x.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        return 0;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
