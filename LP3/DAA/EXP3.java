import java.util.*;

class FractionalKnapsack {

    // Comparator to sort items by value-to-weight ratio in descending order
    static class ItemComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            double r1 = (1.0 * a[0]) / a[1];
            double r2 = (1.0 * b[0]) / b[1];
            return Double.compare(r2, r1); // descending order
        }
    }

    static double fractionalKnapsack(int[] val, int[] wt, int capacity) {
        int n = val.length;

        // Create 2D array: items[i][0] = value, items[i][1] = weight
        int[][] items = new int[n][2];
        for (int i = 0; i < n; i++) {
            items[i][0] = val[i];
            items[i][1] = wt[i];
        }

        // Sort items by value-to-weight ratio
        Arrays.sort(items, new ItemComparator());

        double totalValue = 0.0;
        int remainingCapacity = capacity;

        // To store table data
        List<String[]> table = new ArrayList<>();

        // Process each item
        for (int i = 0; i < n; i++) {
            double ratio = (1.0 * items[i][0]) / items[i][1];
            double fraction = 0.0;

            if (items[i][1] <= remainingCapacity) {
                // take full item
                totalValue += items[i][0];
                remainingCapacity -= items[i][1];
                fraction = 1.0;
            } else {
                // take fraction of item
                fraction = (1.0 * remainingCapacity) / items[i][1];
                totalValue += items[i][0] * fraction;
                remainingCapacity = 0;
            }

            // store row for printing
            table.add(new String[]{
                String.valueOf(items[i][0]),                  // Value
                String.valueOf(items[i][1]),                  // Weight
                String.format("%.2f", ratio),                 // Ratio
                String.format("%.2f", fraction)               // Fraction taken
            });

            if (remainingCapacity == 0) break; // knapsack is full
        }

        // Print table
        System.out.println("Value\tWeight\tRatio\tFraction");
        for (String[] row : table) {
            System.out.println(row[0] + "\t" + row[1] + "\t" + row[2] + "\t" + row[3]);
        }

        return totalValue;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // User input
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        int[] val = new int[n];
        int[] wt = new int[n];

        System.out.println("Enter values of items:");
        for (int i = 0; i < n; i++) val[i] = sc.nextInt();

        System.out.println("Enter weights of items:");
        for (int i = 0; i < n; i++) wt[i] = sc.nextInt();

        System.out.print("Enter knapsack capacity: ");
        int capacity = sc.nextInt();

        // Solve fractional knapsack
        double maxValue = fractionalKnapsack(val, wt, capacity);
        System.out.printf("\nMaximum value in Knapsack = %.2f\n", maxValue);

        sc.close();
    }
}
