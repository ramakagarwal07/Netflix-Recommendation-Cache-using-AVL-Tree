class AVLNode {
    int key, height;
    AVLNode left, right;

    AVLNode(int key) {
        this.key = key;
        this.height = 1;
    }
}

public class NetflixRecommendationAVL {

    AVLNode root;

    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {

        System.out.println("Right Rotation on Node " + y.key);

        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {

        System.out.println("Left Rotation on Node " + x.key);

        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int key) {

        if (node == null) {
            System.out.println("Inserted Recommendation ID: " + key);
            return new AVLNode(key);
        }

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // RR Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // LR Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void inorder(AVLNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    void printTree(AVLNode node, int depth) {
        if (node == null)
            return;

        printTree(node.right, depth + 1);

        for (int i = 0; i < depth; i++)
            System.out.print("    ");

        System.out.println(node.key);

        printTree(node.left, depth + 1);
    }

    public static void main(String[] args) {

        NetflixRecommendationAVL tree = new NetflixRecommendationAVL();

        int[] recommendations = {30, 20, 40, 10, 25, 35, 50, 5, 15};

        System.out.println("=================================");
        System.out.println("NETFLIX RECOMMENDATION SYSTEM");
        System.out.println("Using AVL Tree");
        System.out.println("=================================\n");

        for (int id : recommendations) {
            tree.root = tree.insert(tree.root, id);
        }

        System.out.println("\n---------------------------------");
        System.out.println("Sorted Recommendation IDs");
        System.out.println("---------------------------------");
        tree.inorder(tree.root);

        System.out.println("\n\n---------------------------------");
        System.out.println("AVL Tree Structure");
        System.out.println("---------------------------------");
        tree.printTree(tree.root, 0);

        System.out.println("\n---------------------------------");
        System.out.println("Statistics");
        System.out.println("---------------------------------");
        System.out.println("Root Node          : " + tree.root.key);
        System.out.println("Tree Height        : " + tree.height(tree.root));
        System.out.println("Balance Factor     : " + tree.getBalance(tree.root));
        System.out.println("Total Recommendations Stored : "
                + recommendations.length);

        System.out.println("\nAVL Tree ensures O(log n) search time.");
    }
}