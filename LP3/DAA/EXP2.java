import java.util.*;

// Class to represent Huffman tree node
class Node {
    int freq;       // frequency of character
    char ch;        // actual character
    Node left, right;

    Node(int freq, char ch) {
        this.freq = freq;
        this.ch = ch;   // only valid for leaf nodes
        left = null;
        right = null;
    }

    Node(int freq, Node left, Node right) {
        this.freq = freq;
        this.ch = '\0'; // internal nodes don't hold a character
        this.left = left;
        this.right = right;
    }
}

class HuffmanEncoding {

    // Traverse tree and store Huffman code for each character
    static void preOrder(Node root, Map<Character, String> ans, String curr) {
        if (root == null) return;

        // Leaf node -> store character and its code
        if (root.left == null && root.right == null) {
            ans.put(root.ch, curr);
            return;
        }

        preOrder(root.left, ans, curr + '0');
        preOrder(root.right, ans, curr + '1');
    }

    static Map<Character, String> huffmanCodes(char[] chars, int[] freq) {
        int n = chars.length;

        // Min heap for building Huffman tree
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.freq - b.freq);

        // Create leaf nodes for each character
        for (int i = 0; i < n; i++) {
            pq.add(new Node(freq[i], chars[i]));
        }

        // Construct Huffman tree
        while (pq.size() > 1) {
            Node l = pq.poll();
            Node r = pq.poll();
            Node newNode = new Node(l.freq + r.freq, l, r);
            pq.add(newNode);
        }

        // Root of Huffman tree
        Node root = pq.poll();

        // Map to store Huffman codes
        Map<Character, String> ans = new HashMap<>();
        preOrder(root, ans, "");
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of characters: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        char[] chars = new char[n];
        int[] freq = new int[n];

        System.out.println("Enter characters (space-separated):");
        String[] charInput = sc.nextLine().split("\\s+");
        for (int i = 0; i < n; i++) {
            chars[i] = charInput[i].charAt(0);
        }

        System.out.println("Enter corresponding frequencies:");
        for (int i = 0; i < n; i++) {
            freq[i] = sc.nextInt();
        }

        Map<Character, String> ans = huffmanCodes(chars, freq);

        // Sort characters by frequency descending for output
        List<Character> charList = new ArrayList<>();
        for (char c : chars) charList.add(c);

        charList.sort((a, b) -> Integer.compare(freq[indexOf(chars, b)], freq[indexOf(chars, a)]));

        // Print Huffman codes
        System.out.println("\nCharacter : Frequency : Huffman Code");
        for (char c : charList) {
            System.out.println(c + " : " + freq[indexOf(chars, c)] + " : " + ans.get(c));
        }

        sc.close();
    }

    // Helper function to find index of character in array
    static int indexOf(char[] arr, char ch) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ch) return i;
        }
        return -1; // not found
    }
}
