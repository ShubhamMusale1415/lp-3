import java.util.Scanner;

class FibonacciComparison {

    // Iterative Fibonacci
    public static long fibonacciIterative(int n) {
        if (n <= 1) return n;
        long a = 0, b = 1, c = 0;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    // Recursive Fibonacci
    public static long fibonacciRecursive(int n) {
        if (n <= 1) return n;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the term number (n) to compute Fibonacci:");
        int n = scanner.nextInt(); // Change this value to test different terms

        // Iterative execution
        long startIter = System.nanoTime();
        long resultIter = fibonacciIterative(n);
        long endIter = System.nanoTime();

        // Recursive execution
        long startRec = System.nanoTime();
        long resultRec = fibonacciRecursive(n);
        long endRec = System.nanoTime();

        // Print results
        System.out.println("Fibonacci number for n = " + n);
        System.out.println("--------------------------------");
        System.out.println("Iterative Result : " + resultIter);
        System.out.println("Iterative Time   : " + (endIter - startIter) + " ns");
        System.out.println("--------------------------------");
        System.out.println("Recursive Result : " + resultRec);
        System.out.println("Recursive Time   : " + (endRec - startRec) + " ns");
        scanner.close();
    }
}
