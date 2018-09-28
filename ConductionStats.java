package assignment2;
import java.util.Scanner;
public class ConductionStats {
    private double[] openCells;
    public ConductionStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("N and T must be grater than 0");
        }
        openCells = new double[trials];
        for (int t = 0; t < trials; t++) {
            Conductor p = new Conductor(n);
            int count = 0;
            do {
                int i, j;
                do {
                    i = (int) (Math.random() * n) + 1;
                    j = (int) (Math.random() * n) + 1;
                } while (p.isMetallic(i, j));
                p.open(i, j);
                count++;
            } while (!p.conducts());
            openCells[t] = (double)count/(n*n);
        }
    }

    public double mean() {
        double total = 0;
        for (int i = 0; i < openCells.length; i++) {
            total += openCells[i];
        }
        return total / openCells.length;
    }

    public double stddev() {
        double total = 0;
        double m = mean();
        for (int i = 0; i < openCells.length; i++) {
            total += openCells[i] * openCells[i];
        }
        return Math.sqrt((total - openCells.length * m* m)/(openCells.length-1));
    }

    public double lowConfRange() {
        return mean()-1.96*stddev()/Math.sqrt(openCells.length);
    }

    public double highConfRange() {
        return mean()+1.96*stddev()/Math.sqrt(openCells.length);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter size of grid: ");
        int n = in.nextInt();
        System.out.println("Enter trials: ");
        int trials = in.nextInt();
        ConductionStats cs = new ConductionStats(n, trials);
        System.out.println("Mean = " + cs.mean());
        System.out.println("Standart deviation = " + cs.stddev());
        System.out.println("95% confidence interval = " + cs.lowConfRange() + ", " + cs.highConfRange());

    }

}