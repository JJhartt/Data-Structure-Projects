/**
 * The TreeNode class represents a node in a tree data structure.
 * Each node can have up to three children (left, middle, right),
 * a reference to its parent node, and contains label, prompt, and message information.
 * 
 * @author John Hartmann
 * @id 115764215
 * @recitation R30
 */
public class TreeNode {

    private TreeNode left, right, middle, parent;
    private String label, message, prompt;

    /**
     * Default constructor that initializes all fields to null.
     */
    TreeNode() {
        left = null;
        right = null;
        middle = null;
        parent = null;
        label = null;
        message = null;
        prompt = null;
    }

    /**
     * Constructs a TreeNode with specified label, prompt, and message.
     * Child nodes and parent are initialized to null.
     * 
     * @param label the identifying label for this node
     * @param prompt the prompt text associated with this node
     * @param message the message text associated with this node
     */
    public TreeNode(String label, String prompt, String message) {
        this();
        this.label = label;
        this.prompt = prompt;
        this.message = message;
    }

    /**
     * Returns the left child of this node.
     * 
     * @return the left child TreeNode, or null if no left child exists
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Returns the right child of this node.
     * 
     * @return the right child TreeNode, or null if no right child exists
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Returns the middle child of this node.
     * 
     * @return the middle child TreeNode, or null if no middle child exists
     */
    public TreeNode getMiddle() {
        return middle;
    }

    /**
     * Returns the parent of this node.
     * 
     * @return the parent TreeNode, or null if this is the root node
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * Returns the label of this node.
     * 
     * @return the label String, or null if no label is set
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the message of this node.
     * 
     * @return the message String, or null if no message is set
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the prompt of this node.
     * 
     * @return the prompt String, or null if no prompt is set
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Sets the left child of this node.
     * Note: This method does not automatically set this node as the parent of the left child.
     * 
     * @param left the TreeNode to set as left child
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * Sets the right child of this node.
     * Note: This method does not automatically set this node as the parent of the right child.
     * 
     * @param right the TreeNode to set as right child
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * Sets the middle child of this node.
     * Note: This method does not automatically set this node as the parent of the middle child.
     * 
     * @param middle the TreeNode to set as middle child
     */
    public void setMiddle(TreeNode middle) {
        this.middle = middle;
    }

    /**
     * Sets the parent of this node.
     * Note: This method does not automatically add this node as a child of the parent.
     * 
     * @param parent the TreeNode to set as parent
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * Sets the label of this node.
     * 
     * @param label the String to set as label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Sets the message of this node.
     * 
     * @param message the String to set as message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the prompt of this node.
     * 
     * @param prompt the String to set as prompt
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Checks if this node is a leaf node (has no children).
     * 
     * @return true if this node has no children (left, middle, and right are all null),
     *         false otherwise
     */
    public boolean isLeaf() {
        return left == null && middle == null && right == null;
    }

    /**
     * Searches for and returns a reference to a node with the specified label
     * in the subtree rooted at this node. Uses depth-first search.
     * 
     * @param label the label of the node to find (case-sensitive)
     * @return reference to the found TreeNode, or null if not found
     */
    public TreeNode getNodeReference(String label) {
        if (this.label != null && this.label.equals(label)) {
            return this;
        }
        
        TreeNode found = null;
        
        if (left != null) {
            found = left.getNodeReference(label);
            if (found != null) return found;
        }
        if (middle != null) {
            found = middle.getNodeReference(label);
            if (found != null) return found;
        }
        if (right != null) {
            found = right.getNodeReference(label);
            if (found != null) return found;
        }
        return null;
    }

    /**
     * Performs a pre-order traversal of the tree rooted at this node,
     * printing the label, prompt, and message of each node to standard output.
     * The output format for each node is:
     * Label: [label]
     * Prompt: [prompt]
     * Message: [message]
     * 
     * followed by a blank line.
     */
    public void preOrder() {
        System.out.println("Label: " + label);
        System.out.println("Prompt: " + prompt);
        System.out.println("Message: " + message);
        System.out.println();
        
        if (left != null) {
            left.preOrder();
        }
        if (middle != null) {
            middle.preOrder();
        }
        if (right != null) {
            right.preOrder();
        }
    }
}