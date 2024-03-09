import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.Array;


public class Percolation {
    private boolean[][] grid; // 记录每个单元格的状态
    private int openSites = 0; // 记录打开的单元格数量
    private WeightedQuickUnionUF mainUfSet;
    private WeightedQuickUnionUF auxiliaryUfSet;
    private int topVirtualIndex; // 顶部虚拟节点索引
    private int bottomVirtualIndex; // 底部虚拟节点索引
    private int size;

    public Percolation(int N) {
        size = N;
        grid = new boolean[N][N];
        mainUfSet = new WeightedQuickUnionUF(N * N + 2); // 增添顶部和底部两个虚拟节点
        auxiliaryUfSet = new WeightedQuickUnionUF(N * N + 1); // 辅助并查集，只添加一个虚拟顶部节点
        topVirtualIndex = N * N;
        bottomVirtualIndex = N * N + 1;
    }

    private int xyTo1D(int row, int col) {
        return row * size + col;
    }

    public void open(int row, int col) {
        if (!grid[row][col]) {
            grid[row][col] = true; // 标记单元格为开
            openSites += 1;
        }
        int index = xyTo1D(row, col);
        // 连接到四周的单元格
        if (row > 0 && isOpen(row - 1, col)) {
            mainUfSet.union(index, xyTo1D(row - 1, col));
            auxiliaryUfSet.union(index, xyTo1D(row - 1, col));
        } // up
        if (row < size - 1 && isOpen(row + 1, col)) {
            mainUfSet.union(index, xyTo1D(row + 1, col));
            auxiliaryUfSet.union(index, xyTo1D(row + 1, col));
        } // down
        if (col > 0 && isOpen(row, col - 1)) {
            mainUfSet.union(index, xyTo1D(row, col - 1));
            auxiliaryUfSet.union(index, xyTo1D(row, col - 1));
        } // left
        if (col < size - 1 && isOpen(row, col + 1)) {
            mainUfSet.union(index, xyTo1D(row, col + 1));
            auxiliaryUfSet.union(index, xyTo1D(row, col + 1));
        } // right

        // 连接到顶部和底部的虚拟节点
        if (row == 0) {
            mainUfSet.union(index, topVirtualIndex);
            auxiliaryUfSet.union(index, topVirtualIndex);
        }
        if (row == size - 1) {
            mainUfSet.union(index, bottomVirtualIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        return auxiliaryUfSet.connected(index, topVirtualIndex);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return mainUfSet.connected(topVirtualIndex, bottomVirtualIndex);
    }
}
