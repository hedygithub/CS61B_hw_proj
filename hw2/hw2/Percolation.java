package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;

public class Percolation {
    public int[][] grid;
    public int openSites;
    //auto connect all items in first line
    public WeightedQuickUnionUF set;
    //auto connect all items in first line, last line
    public WeightedQuickUnionUF percolationSet;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        grid = new int[N][N];
        openSites = 0;
        set = new WeightedQuickUnionUF(N*N);
        percolationSet = new WeightedQuickUnionUF(N*N);
        //0--closed, 1--open
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = 0;

                //to decrease isFull running time
                if (i == 0 && j !=0){
                    set.union(0, j);
                    percolationSet.union(0, j);
                }

                //to decrease percolation running time
                if (i == N-1 && j !=0){
                    percolationSet.union(XYto1D(N-1,0), XYto1D(N-1,j));
                }
            }
        }
    }

    private int XYto1D(int x, int y) {
        int d = x * grid.length + y;
        return(d);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(!isOpen(row, col)) {
            grid[row][col] = 1;
            openSites += 1;
            if (row - 1 >= 0 && isOpen(row - 1, col)) {
                set.union(XYto1D(row, col), XYto1D(row - 1, col));
                percolationSet.union(XYto1D(row, col), XYto1D(row - 1, col));
            }
            if (row + 1 < grid.length && isOpen(row + 1, col)) {
                set.union(XYto1D(row, col), XYto1D(row + 1, col));
                percolationSet.union(XYto1D(row, col), XYto1D(row + 1, col));
            }
            if (col - 1 >= 0 && isOpen(row, col - 1)) {
                set.union(XYto1D(row, col), XYto1D(row, col - 1));
                percolationSet.union(XYto1D(row, col), XYto1D(row, col - 1));
            }
            if (col + 1 < grid.length && isOpen(row, col + 1)) {
                set.union(XYto1D(row, col), XYto1D(row, col + 1));
                percolationSet.union(XYto1D(row, col), XYto1D(row, col + 1));
            }
        }

    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return (grid[row][col] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && set.connected(0, XYto1D(row, col));
    }


    // number of open sites
    public int numberOfOpenSites() {
        return(openSites);
    }
    // does the system percolate?
    public boolean percolates() {
        return percolationSet.connected(0, XYto1D(grid.length - 1, 0));
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
        Percolation testGrid = new Percolation(4);
        System.out.println(testGrid.isOpen(1, 1)); //False
        testGrid.open(1,1);
        System.out.println(testGrid.isOpen(1, 1)); //Ture
        testGrid.open(2,1);
        testGrid.open(3,1);
        System.out.println(testGrid.isFull(3,1));  //False
        System.out.println(testGrid.percolates());          //False
        testGrid.open(0,0);
        testGrid.open(1,0);
        System.out.println(testGrid.isFull(3,1));  //True
        System.out.println(testGrid.percolates());         //True
        testGrid.open(3,3);
        System.out.println(testGrid.isFull(3,3));  //False

    }

}
