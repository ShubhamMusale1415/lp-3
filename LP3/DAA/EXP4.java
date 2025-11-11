import java.util.*;

class ZeroOneKnapsack {

    // Function to solve 0-1 Knapsack problem
    static void knapSack(int n, int W, int[] val, int[] wt) {
        int[][] dp = new int[n + 1][W + 1];

        // Build DP table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (wt[i - 1] <= w) {
                    dp[i][w] = Math.max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Traceback to find which items are included
        int res = dp[n][W];
        int w = W;
        int[] taken = new int[n]; // binary array to store selection

        for (int i = n; i > 0 && res > 0; i--) {
            if (res != dp[i - 1][w]) {
                taken[i - 1] = 1; // item included
                res -= val[i - 1];
                w -= wt[i - 1];
            } else {
                taken[i - 1] = 0; // item not included
            }
        }

        // Print selection as binary string
        System.out.print("Selected items (binary): ");
        for (int bit : taken) System.out.print(bit);
        System.out.println();

        // Print max profit
        System.out.println("Maximum profit = " + dp[n][W]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // User input
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        System.out.print("Enter knapsack capacity: ");
        int W = sc.nextInt();

        int[] val = new int[n];
        int[] wt = new int[n];

        System.out.println("Enter values of items:");
        for (int i = 0; i < n; i++) val[i] = sc.nextInt();

        System.out.println("Enter weights of items:");
        for (int i = 0; i < n; i++) wt[i] = sc.nextInt();

        // Solve 0-1 Knapsack
        knapSack(n, W, val, wt);

        sc.close();
    }
}
