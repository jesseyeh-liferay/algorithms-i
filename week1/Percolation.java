import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF    _grid;
    private int                     _gridSize;
    private int                     _numOpenSites = 0;
    private boolean[]               _openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        _grid = new WeightedQuickUnionUF(n * n + 2);
        _gridSize = n;
        _openSites = new boolean[n * n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	if (isOpen(row, col)) {
    	    return;
        }

    	_numOpenSites++;
    	_openSites[_getSingleIndex(row, col)] = true;

    	int index = _getSingleIndex(row, col);

    	// Check if site can be connected to virtual top/bottom site
    	if (row == 1) {
            _grid.union(index, 0);
        } else if (row == _gridSize) {
    	    _grid.union(index, _gridSize * _gridSize + 1);
        }

    	// Connect site to adjacent, open sites
    	if (_isValidSite(row, col - 1) && isOpen(row, col - 1)) {
    	    _grid.union(_getSingleIndex(row, col - 1), index);
        }

        if (_isValidSite(row, col + 1) && isOpen(row, col + 1)) {
            _grid.union(_getSingleIndex(row, col + 1), index);
        }

        if (_isValidSite(row - 1, col) && isOpen(row - 1, col)) {
            _grid.union(_getSingleIndex(row - 1, col), index);
        }

        if (_isValidSite(row + 1, col) && isOpen(row + 1, col)) {
            _grid.union(_getSingleIndex(row + 1, col), index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        _validateSite(row, col);

        return _openSites[_getSingleIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        _validateSite(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        return _grid.connected(_getSingleIndex(row, col), 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return _numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return _grid.connected(0, _gridSize * _gridSize + 1);
    }

    private int _getSingleIndex(int row, int col) {
        return _gridSize * (row - 1) + col - 1;
    }

    private boolean _isValidSite(int row, int col) {
        return (row >= 1 && col >= 1 && row <= _gridSize && col <= _gridSize);
    }

    private void _validateSite(int row, int col) {
    	if (!_isValidSite(row, col)) {
            throw new IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
