import java.util.Scanner; 

/**
 * The Tree class represents a tree data structure composed of TreeNode objects.
 * It provides functionality to build and traverse the tree, as well as
 * conduct interactive sessions navigating through the tree nodes with
 * parent-child navigation support.
 * 
 * @author John Hartmann
 * @id 115764215
 * @recitation R30
 */
public class Tree {

    private TreeNode root;

    /**
     * Constructs an empty Tree with no root node.
     */
    public Tree() {
        root = null;
    }

    /**
     * Adds a new node to the tree with the specified properties.
     * The new node is added as a child of the node with parentLabel,
     * and the parent-child relationship is properly maintained.
     * 
     * @param label the unique identifier for the new node
     * @param prompt the user prompt text for the new node
     * @param message the help message text for the new node
     * @param parentLabel the label of the parent node (null if adding root)
     * @return true if the node was successfully added, false if:
     *         - Parent node wasn't found (when not adding root)
     *         - All child slots are full (max 3 children per node)
     *         - Invalid parameters were provided
     */
    public boolean addNode(String label, String prompt, String message, String parentLabel) {
        TreeNode newnode = new TreeNode(label, prompt, message);

        if (root == null && parentLabel == null) {
            root = newnode;  
            return true;      
        }
    
        if (root == null) {
            return false;
        }

        TreeNode parentNode = root.getNodeReference(parentLabel);
        if (parentNode == null) {
            return false;
        }

        newnode.setParent(parentNode);

        if (parentNode.getLeft() == null) {
            parentNode.setLeft(newnode);
            return true;
        }
        else if (parentNode.getMiddle() == null) {
            parentNode.setMiddle(newnode);
            return true;
        }
        else if (parentNode.getRight() == null) {
            parentNode.setRight(newnode);
            return true;
        }

        return false; 
    }

    /**
     * Performs a pre-order traversal of the entire tree,
     * printing each node's information (label, prompt, and message)
     * to standard output in the following format for each node:
     * Label: [label]
     * Prompt: [prompt]
     * Message: [message]
     * 
     * Prints "Tree is empty." if the tree has no nodes.
     */
    public void preOrder() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        System.out.println("Traversing the tree in preorder");
        root.preOrder();
    }

    /**
     * Begins an interactive help session that navigates through the tree nodes.
     * At each node:
     * 1. Displays the node's message
     * 2. Lists available options (child nodes)
     * 3. Provides navigation options:
     *    - Numbered options for child nodes
     *    - 'B' to go back to parent (when available)
     *    - '0' to exit the session
     * 
     * The session continues until:
     * - A leaf node is reached (automatically ends with thank you message)
     * - User chooses to exit
     * - The tree is empty
     * 
     * @param scanner the Scanner instance to use for user input
     */
    public void beginSession(Scanner scanner) {
        if (root == null) {
            System.out.println("Tree does not exist");
            return;
        }

        TreeNode current = root;
        System.out.println("Help Session Starting...");
        
        while (current != null) {
            System.out.println(current.getMessage());

            if (current.isLeaf()) {
                System.out.println("Thank you for using this automated help service!");
                break;
            }
            
            int optionNumber = 1;
            
            if (current.getLeft() != null) {
                System.out.println(optionNumber + " " + current.getLeft().getPrompt());
                optionNumber++;
            }
            if (current.getMiddle() != null) {
                System.out.println(optionNumber + " " + current.getMiddle().getPrompt());
                optionNumber++;
            }
            if (current.getRight() != null) {
                System.out.println(optionNumber + " " + current.getRight().getPrompt()); 
                optionNumber++;
            }
            if (current.getParent() != null) {
                System.out.println("B Go Back.");
            }
            
            System.out.println("0 Exit Session."); 
            System.out.print("Choice> "); 
            
            String choiceStr = scanner.nextLine().trim().toUpperCase();
            
            if (choiceStr.equals("0")) {
                System.out.println("Returning to main menu");
                break;
            } else if (choiceStr.equals("B") && current.getParent() != null) {
                current = current.getParent();
            } else {
                try {
                    int choice = Integer.parseInt(choiceStr);
                    TreeNode nextNode = null;
                    int currentOption = 1;
                    
                    if (current.getLeft() != null) {
                        if (choice == currentOption) {
                            nextNode = current.getLeft();
                        }
                        currentOption++;
                    }
                    if (current.getMiddle() != null && nextNode == null) {
                        if (choice == currentOption) {
                            nextNode = current.getMiddle();
                        }
                        currentOption++;
                    }
                    if (current.getRight() != null && nextNode == null) {
                        if (choice == currentOption) {
                            nextNode = current.getRight();
                        }
                    }
                    
                    if (nextNode != null) {
                        current = nextNode;
                    } else {
                        System.out.println("Invalid Input");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input");
                }
            }
        }
    }

    /**
     * Begins an interactive help session using a new Scanner instance.
     * This is a convenience method that creates its own Scanner for System.in.
     * Implemented due to codegrade having issues with scannners in 2 different classes
     * @deprecated Use beginSession(Scanner) instead to avoid Scanner conflicts
     */
    public void beginSession() {
        beginSession(new Scanner(System.in));
    }
}