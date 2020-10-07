import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] 	_experimentResults;
	private int         _numExperiments;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        _experimentResults = new double[trials];
        _numExperiments = trials;

        for (int t = 0; t < trials; t++) {
			Percolation p = new Percolation(n);

			while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    continue;
                }
            }

            _experimentResults[t] = (double)p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(_experimentResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(_experimentResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean() - (1.96 * stddev() / Math.sqrt(_numExperiments));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(_numExperiments));
    }

    // test client (see below)
    public static void main(String[] args) {
    	int n = Integer.parseInt(args[0]);
    	int T = Integer.parseInt(args[1]);

    	PercolationStats ps = new PercolationStats(n, T);

    	System.out.println("mean                    = " + ps.mean());
    	System.out.println("stddev                  = " + ps.stddev());
    	System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
