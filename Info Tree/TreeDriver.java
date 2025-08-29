import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The TreeDriver class provides a command-line interface for interacting with
 * a Tree structure. It serves as the main driver program for the tree-based
 * help system, offering options to load trees from files, conduct help sessions,
 * traverse trees, and quit the program.
 * 
 * <p>The class maintains a single Tree instance and provides file loading
 * capabilities with robust error checking for proper file format.
 * 
 * @author John Hartmann
 * @id 115764215
 * @recitation R30
 */
public class TreeDriver {

    private static Scanner input = new Scanner(System.in);
    private static Tree mainTree = new Tree();

    /**
     * Main entry point for the program that displays a menu and processes user input.
     * Continues running until the user selects the quit option.
     * 
     * <p>Menu options:
     * <ul>
     * <li>L - Load a Tree from file</li>
     * <li>H - Begin a Help Session</li>
     * <li>T - Traverse the Tree in preorder</li>
     * <li>Q - Quit the program</li>
     * </ul>
     * 
     * @param args command line arguments (not currently used)
     */
    public static void main(String[] args) {
        boolean run = true;
        
        while (run) {
            System.out.println("L - Load a Tree.");
            System.out.println("H - Begin a Help Session.");
            System.out.println("T - Traverse the Tree in preorder.");
            System.out.println("Q - Quit");
            System.out.print("Choice>");

            String choice = input.nextLine().trim().toUpperCase();
            System.out.println();

            switch (choice) {
                case "L":
                    loadTree();
                    break;
                case "H":
                    mainTree.beginSession(input);
                    break;
                case "T":
                    mainTree.preOrder();
                    break;
                case "Q":
                    run = false;
                    System.out.println("Thank you for using our automated help services!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println();
        }
    }

    /**
     * Loads a tree structure from a specified text file with strict format requirements.
     * 
     * <p>File format requirements:
     * <ol>
     * <li>First line must be "root"</li>
     * <li>Next two lines contain root prompt and message</li>
     * <li>Subsequent lines specify parent-child relationships in format:
     *     [parentLabel] [childCount]</li>
     * <li>After each parent line, childCount sets of three lines follow (label, prompt, message)</li>
     * </ol>
     * 
     * <p>Performs extensive error checking and provides detailed error messages for:
     * <ul>
     * <li>File not found</li>
     * <li>Invalid file format</li>
     * <li>Missing or malformed data</li>
     * <li>Invalid child counts</li>
     * </ul>
     */
    private static void loadTree() {
        System.out.print("Enter the file name> ");
        String filename = input.nextLine().trim();
        
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            mainTree = new Tree();
            
            if (!fileScanner.hasNextLine()) {
                System.out.println("Error: Empty file.");
                fileScanner.close();
                return;
            }
            
            // Read root node
            String firstLine = fileScanner.nextLine().trim();
            if (!firstLine.equals("root")) {
                System.out.println("File must start with root");
                fileScanner.close();
                return;
            }
            
            if (!fileScanner.hasNextLine()) {
                System.out.println("Error: Missing root prompt.");
                fileScanner.close();
                return;
            }
            String rootPrompt = fileScanner.nextLine().trim();
            
            if (!fileScanner.hasNextLine()) {
                System.out.println("Error: Missing root message.");
                fileScanner.close();
                return;
            }
            String rootMessage = fileScanner.nextLine().trim();
            
            if (!mainTree.addNode("root", rootPrompt, rootMessage, null)) {
                System.out.println("Error: Could not create root node.");
                fileScanner.close();
                return;
            }
            
            // Read rest of the tree
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    String parentLabel = parts[0];
                    int childCount;
                    
                    try {
                        childCount = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid child count format: " + line);
                        fileScanner.close();
                        return;
                    }
                    
                    if (childCount < 0 || childCount > 3) {
                        System.out.println("Cannot have more than 3 children for a node");
                        fileScanner.close();
                        return;
                    }
                    
                    for (int i = 0; i < childCount; i++) {
                        if (!readNodeData(fileScanner, parentLabel)) {
                            System.out.println("Error: Could not read child node data for parent: " + parentLabel);
                            fileScanner.close();
                            return;
                        }
                    }
                } else {
                    System.out.println("Error: Unexpected line format: " + line);
                    fileScanner.close();
                    return;
                }
            }
            
            fileScanner.close();
            System.out.println("Tree created successfully!");
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filename);
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Helper method that reads and validates node data from the input file.
     * 
     * <p>Reads exactly three lines from the scanner:
     * <ol>
     * <li>Node label</li>
     * <li>Prompt text</li>
     * <li>Message text</li>
     * </ol>
     * 
     * @param scanner the Scanner object reading the input file
     * @param parentLabel the label of the parent node for the new node
     * @return true if node was successfully read and added to tree, false if:
     *         - Required data is missing
     *         - Data is malformed
     *         - Node could not be added to tree
     * @throws IllegalStateException if scanner fails during reading
     */
    private static boolean readNodeData(Scanner scanner, String parentLabel) {
        try {
            if (!scanner.hasNextLine()){
                return false;
            } 
            String label = scanner.nextLine().trim();
            if (label.isEmpty()) {
                return false;
            }
            if (!scanner.hasNextLine()){
                return false;
            } 
            String prompt = scanner.nextLine().trim();
            if (!scanner.hasNextLine()){
                 return false;
            }
            String message = scanner.nextLine().trim();
            
            boolean success = mainTree.addNode(label, prompt, message, parentLabel);
            if (!success) {
                System.out.println("Error: Could not add node with label: " + label + " to parent: " + parentLabel);
                return false;
            }
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
}