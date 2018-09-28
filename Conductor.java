package assignment2;

public class Conductor {
    private boolean[][] grid;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF wquTop;
    private int N;
    public Conductor(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }
        this.N = n;
        grid = new boolean[n][n];
        wqu = new WeightedQuickUnionUF(n * n + 2);
        wquTop = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(int row, int col) {
        if (row< 1 || row> N || col< 1 || col> N) {
            throw new IndexOutOfBoundsException("index out of bounds.");
        }
        grid[row- 1][col-1] = true;
        if (row== 1) {
            wqu.union(0, col);
            wquTop.union(0, col);
        }
        if (row== N) {
            wqu.union(N * N + 1, N * (N - 1) + col);
        }
        if (row> 1 && isMetallic(row- 1, col)) {
            wqu.union((row- 1) * N + col, (row- 2) * N +col);
            wquTop.union((row- 1) * N + col, (row- 2) * N +col);
        }
        if (row< N && isMetallic(row+ 1, col)) {
            wqu.union((row- 1) * N + col, row* N + col);
            wquTop.union((row- 1) * N + col ,row* N +col);
        }
        if (col> 1 && isMetallic(row, col- 1)) {
            wqu.union((row- 1) * N +col, (row- 1) * N +col- 1);
            wquTop.union((row- 1) * N +col, (row- 1) * N +col- 1);
        }
        if (col< N && isMetallic(row, col+ 1)) {
            wqu.union((row- 1) * N +col, (row- 1) * N +col+ 1);
            wquTop.union((row- 1) * N +col, (row- 1) * N +col+ 1);
        }
    }

    public boolean isMetallic(int row, int col) {
        if (row< 1 || row> N ||col< 1 ||col > N) {
            throw new IndexOutOfBoundsException("index out of bounds.");
        }
        return grid[row- 1][col- 1];
    }

    public boolean isNotMetallic(int row, int col) {
        if (row< 1 || row> N || col< 1 ||col> N) {
            throw new IndexOutOfBoundsException("index out of bounds.");
        }
        return wquTop.connected(0,(row-1)*N+col);
    }

    public boolean conducts() {
        return wqu.connected(0, N * N + 1);
    }

}
