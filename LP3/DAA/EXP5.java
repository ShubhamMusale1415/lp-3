import java.util.ArrayList;
import java.util.Scanner;

class NQueens {

    // Function to check if it is safe to place queen at (row, col)
    static int isSafe(int[][] board, int row, int col) {
        int n = board.length;

        // Check column above the current row
        for (int i = 0; i < row; i++)
            if (board[i][col] == 1)
                return 0;

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return 0;

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++)
            if (board[i][j] == 1)
                return 0;

        return 1;
    }

    // Recursive backtracking function to place queens
    static void placeQueens(int row, int[][] board, ArrayList<int[][]> result) {
        int n = board.length;

        // If all queens are placed successfully
        if (row == n) {
            int[][] snapshot = new int[n][n];

            for (int i = 0; i < n; i++)
                System.arraycopy(board[i], 0, snapshot[i], 0, n);

            result.add(snapshot);
            return;
        }

        // Try placing a queen in every column of current row
        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col) == 1) {
                board[row][col] = 1; // place queen
                placeQueens(row + 1, board, result); // recursive call

                board[row][col] = 0; // backtrack
            }
        }
    }

    // Wrapper function to solve N-Queens
    static ArrayList<int[][]> nQueen(int n) {
        int[][] board = new int[n][n];
        ArrayList<int[][]> result = new ArrayList<>();
        placeQueens(0, board, result);
        return result;
    }

    // Print solution board using Q and .
    static void printBoard(int[][] board) {
        int n = board.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] == 1 ? "Q " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter value of N (number of queens): ");
        int n = sc.nextInt();

        ArrayList<int[][]> result = nQueen(n);

        System.out.println("\nTotal Solutions for " + n + " Queens = " + result.size() + "\n");

        int solutionNo = 1;
        for (int[][] board : result) {
            System.out.println("Solution " + solutionNo++ + ":");
            printBoard(board);
        }

        sc.close();
    }
}
