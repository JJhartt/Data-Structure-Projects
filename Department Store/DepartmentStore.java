import java.util.Scanner;

/**
 * Main driver class for the Department Store inventory management system.
 * Provides a command-line interface for managing items in a store using
 * an ItemList data structure that maintains items in sorted order by RFID tag.
 * 
 * The system supports the following operations:
 * - Insert new items into inventory
 * - Move items between locations (shelves, carts, out)
 * - Print items by location or all items
 * - Clean store (return misplaced items to original locations)
 * - Checkout items from carts
 * - Update inventory (remove purchased items)
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 2025
 */
public class DepartmentStore {
    
    /**
     * Main method that runs the department store inventory management system.
     * Displays a menu of options and processes user input until the user chooses to quit.
     * Uses a Scanner for input and an ItemList to manage the inventory.
     * 
     * Menu Options:
     * - C: Clean store (return misplaced items to original shelves)
     * - I: Insert a new item into the inventory
     * - L: List all items at a specific location
     * - M: Move an item from one location to another
     * - O: Checkout all items from a specific cart
     * - P: Print all items in the store
     * - R: Print by RFID tag number (currently not implemented)
     * - U: Update inventory system (remove all purchased items)
     * - Q: Exit the program
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ItemList list = new ItemList();
        boolean run = true;

        System.out.println("Welcome!");
        
        while (run) {
            // Display menu options
            System.out.println("C - Clean store");
            System.out.println("I - Insert an item into the list");
            System.out.println("L - List by location");
            System.out.println("M - Move an item in the store");
            System.out.println("O - Checkout");
            System.out.println("P - Print all items in store");
            System.out.println("R - Print by RFID tag number");
            System.out.println("U - Update inventory system");
            System.out.println("Q - Exit the program.");
            System.out.println("Please select an option:");

            String choice = scanner.nextLine();

            switch (choice) {
                case "C":
                    // Clean store - return misplaced items to original locations
                    System.out.println("Items moved back to original locations:");
                    list.cleanStore();
                    break;

                case "I":
                    // Insert new item into inventory
                    try {
                        System.out.println("Enter the name:");
                        String name = scanner.nextLine();

                        System.out.println("Enter the RFID:");
                        String RFID = scanner.nextLine();

                        System.out.println("Enter the original location:");
                        String location = scanner.nextLine();

                        System.out.println("Enter the price:");
                        double price = Double.parseDouble(scanner.nextLine());

                        list.insertInfo(name, RFID, price, location);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid price format. Please enter a valid number.");
                    } catch (Exception e) {
                        System.out.println("Error inserting item: " + e.getMessage());
                    }
                    break;

                case "L":
                    // List items by location
                    System.out.println("Enter the location:");
                    String location = scanner.nextLine();
                    list.printByLocation(location);
                    break;

                case "M":
                    // Move item from one location to another
                    try {
                        System.out.println("Enter the RFID:");
                        String RFID = scanner.nextLine();
                        
                        System.out.println("Enter the original location:");
                        String source = scanner.nextLine();
                        
                        System.out.println("Enter the desired destination:");
                        String destination = scanner.nextLine();
                        
                        boolean moved = list.moveItem(RFID, source, destination);
                        if (moved) {
                            System.out.println("Item successfully moved from " + source + " to " + destination);
                        } else {
                            System.out.println("Item with RFID " + RFID + " not found at location " + source);
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected error during move operation: " + e.getMessage());
                    }
                    break;

                case "O":
                    // Checkout items from cart
                    try {
                        System.out.println("Enter the cart number:");
                        String cartNumber = scanner.nextLine();
                        
                        double total = list.checkOut(cartNumber);
                        System.out.printf("Total cost: $%.2f%n", total);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error during checkout: " + e.getMessage());
                    }
                    break;

                case "P":
                    // Print all items in store
                    list.printAll();
                    break;

                case "R":
                    // Print by RFID tag number
                    System.out.println("Feature not yet implemented.");
                    break;

                case "U":
                    // Update inventory system - remove purchased items
                    System.out.println("The following item(s) have been removed from the system:");
                    list.removeAllPurchased();
                    break;

                case "Q":
                    // Exit the program
                    System.out.println("Thank you for using the Department Store system!");
                    run = false;
                    break;

                default:
                    // Invalid menu option
                    System.out.println("Invalid option. Please select a valid menu option.");
                    break;
            }
            
            // Add a blank line for better readability between menu iterations
            if (run) {
                System.out.println();
            }
        }
        
        scanner.close();
    }

}
