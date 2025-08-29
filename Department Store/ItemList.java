/**
 * A doubly-linked list implementation for managing ItemInfo objects.
 * The list maintains items in sorted order by RFID tag number (parsed as hexadecimal).
 * Supports operations for insertion, removal, movement, and various display methods.
 * 
 * The list maintains references to head, tail, and current nodes for efficient operations.
 * Items are automatically inserted in the correct sorted position based on their RFID tags.
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 2025
 * Student ID: 115764215
 * Recitation: R30
 */
public class ItemList {

    /** Reference to the first node in the list */
    private ItemInfoNode head;
    
    /** Reference to the current node for traversal operations */
    private ItemInfoNode current;
    
    /** Reference to the last node in the list */
    private ItemInfoNode tail;
    
    /** The number of nodes currently in the list */
    private int size;

    /**
     * Constructs an empty ItemList.
     * Initializes all references to null and size to 0.
     * 
     * Complexity: O(1) - Constant time initialization of instance variables.
     */
    public ItemList() {
        head = null;
        tail = null;
        current = null;
        size = 0;
    }

    /**
     * Gets the head node of the list.
     * 
     * @return the first ItemInfoNode in the list, or null if empty
     */
    public ItemInfoNode getHead() {
        return head;
    }

    /**
     * Gets the current node used for traversal.
     * 
     * @return the current ItemInfoNode being referenced
     */
    public ItemInfoNode getCurrent() {
        return current;
    }

    /**
     * Gets the tail node of the list.
     * 
     * @return the last ItemInfoNode in the list, or null if empty
     */
    public ItemInfoNode getTail() {
        return tail;
    }
    
    /**
     * Gets the number of items currently in the list.
     * 
     * @return the size of the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Inserts an item into the list in sorted order based on RFID tag number.
     * The RFID tag is treated as a hexadecimal number for comparison purposes.
     * Duplicate RFID numbers are allowed and will be grouped together.
     * 
     * Complexity: O(N) - Must traverse the list to find the correct insertion position.
     * In worst case, traverses entire list when inserting at the tail.
     * 
     * @param name the name of the item
     * @param rfidTag the RFID tag number (9-character hexadecimal)
     * @param initPosition the initial position (original location)
     * @param price the price of the item (must be positive)
     */
    public void insertInfo(String name, String rfidTag, double price, String initPosition) {
        ItemInfo info = new ItemInfo();
        try {
            info.setName(name);
            info.setTag(rfidTag);
            info.setOrigin(initPosition);
            info.setPrice(price);
        } catch (IdTypeException e) {
            System.out.println(e.getMessage());
            return;
        }

        ItemInfoNode newNode = new ItemInfoNode(info);
        System.out.println(newNode.getInfo());

        // Case 1: Empty list → insert at head/tail
        if (head == null) {
            head = tail = newNode;
            size++;
            System.out.println("Item inserted at Head.");
            return;
        }

        long newNodeRFID = Long.parseLong(newNode.getID(), 16);
        long headRFID = Long.parseLong(head.getID(), 16);

        // Case 2: New node is smaller than head → insert at head
        if (newNodeRFID < headRFID) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            size++;
            System.out.println("Item inserted at Head.");
            return;
        }

        // Case 3: Traverse to find correct position
        ItemInfoNode current = head;
        while (current != null) {
            Long currentRFID = Long.parseLong(current.getID(), 16);

            if (newNodeRFID > currentRFID) {
                if (current.getNext() == null) { // Insert at tail
                    current.setNext(newNode);
                    newNode.setPrev(current);
                    tail = newNode;
                    size++;
                    System.out.println("Item inserted at Tail.");
                    return;
                }
                current = current.getNext();
            } else { // Insert before current (middle or equal case)
                newNode.setNext(current);
                newNode.setPrev(current.getPrev());
                if (current.getPrev() != null) {
                    current.getPrev().setNext(newNode);
                } else {
                    // If current.getPrev() is null, current is head, so update head
                    head = newNode;
                }
                current.setPrev(newNode);
                size++;
                System.out.println("Item inserted in Middle.");
                return;
            }
        }
    }

    /**
     * Prints all items in the list in a formatted table.
     * Items are displayed in sorted order by RFID tag number.
     * Does not modify or destroy the list structure.
     * 
     * Complexity: O(N) - Must visit each node exactly once to print all items.
     */
    public void printAll() {
        if (head == null) {
            System.out.println("No Elements to print");
            return;
        }
        current = head;
        System.out.println("Item Name RFID      Location  Location  Price");
        System.out.println("--------- --------- --------- --------- ------");
        for (int i = 0; i < size; i++) {
            System.out.println(current.getInfo());
            current = current.getNext();
        }
    }

    /**
     * Removes all items that have been purchased (location = "out") from the list.
     * Displays information about each removed item before removal.
     * Does not destroy the list structure, only removes specific nodes.
     * 
     * Complexity: O(N) - Must examine each node to check location, and removal
     * operations are O(1) for each node when we have direct reference.
     */
    public void removeAllPurchased() {
        current = head;
        boolean foundItems = false;
        
        while (current != null) {
            ItemInfo item = current.getInfo();
            
            if (item.getLocation().equalsIgnoreCase("out")) {
                if (!foundItems) {
                    foundItems = true;
                }
                
                // Display the item before removing it using toString()
                System.out.println(item.toString());
                
                // Store the next node before removing current
                ItemInfoNode nextNode = current.getNext();
                
                // Remove the current node - handle all cases inline
                if (current == head) {
                    head = current.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    }
                    if (current == tail) {
                        tail = null;
                    }
                } else if (current == tail) {
                    tail = current.getPrev();
                    tail.setNext(null);
                } else {
                    ItemInfoNode prevNode = current.getPrev();
                    ItemInfoNode nextNodeTemp = current.getNext();
                    
                    prevNode.setNext(nextNodeTemp);
                    nextNodeTemp.setPrev(prevNode);
                }
                
                // Clean up the removed node's references
                current.setNext(null);
                current.setPrev(null);
                size--; // Don't forget to decrement size
                
                // Move to the next node
                current = nextNode;
            } else {
                // Only advance if we didn't remove the current node
                current = current.getNext();
            }
        }
        
        if (!foundItems) {
            System.out.println("No items to remove.");
        }
    }
    
    /**
     * Moves an item from a source location to a destination location.
     * Searches for an item with the specified RFID tag at the source location
     * and updates its location to the destination.
     * 
     * Complexity: O(N) - Must potentially traverse entire list to find item
     * with matching RFID tag and source location.
     * 
     * @param rfidTag the RFID tag of the item to move
     * @param source the current location of the item
     * @param dest the destination location for the item
     * @return true if item was found and moved, false otherwise
     * @throws IllegalArgumentException if parameters are null, destination format is invalid,
     *                                or source is "out"
     */
    public boolean moveItem(String rfidTag, String source, String dest) throws IllegalArgumentException {
        if (source == null || dest == null || rfidTag == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        
        if (source.equalsIgnoreCase("out")) {
            throw new IllegalArgumentException("Cannot move item from 'out' location");
        }
        
        current = head; 
        
        while (current != null) {
            ItemInfo item = current.getInfo(); 
            
            if (item.getTag().equalsIgnoreCase(rfidTag)) {
                if (item.getLocation().equals(source)) {
                    // Move the item by updating its current location
                    try {
                        item.setLocation(dest);
                        return true; // Successfully found and moved the item
                    } catch (Exception e) {
                        // Re-throw as IllegalArgumentException to match method signature
                        throw new IllegalArgumentException("Invalid destination format: " + e.getMessage());
                    }
                } else {
                    // Item found but not at the specified source location
                    return false;
                }
            }
            
            current = current.getNext(); // Move to next node
        }
        
        // Item with given RFID not found in the list
        return false;
    }

    /**
     * Prints all items currently at a specified location in a formatted table.
     * Items are displayed in sorted order by RFID tag number.
     * Does not modify or destroy the list structure.
     * 
     * Complexity: O(N) - Must examine each node to check if it matches the specified location.
     * 
     * @param location the location to search for (case-insensitive)
     */
    public void printByLocation(String location) {
        if (location == null) {
            System.out.println("Location cannot be null");
            return;
        }
        
        boolean found = false;
        ItemInfoNode temp = head; // Use local variable instead of instance variable
        
        // Print header first in standardized format
        System.out.println("Item Name RFID      Location  Location  Price");
        System.out.println("--------- --------- --------- --------- ------");
        
        while (temp != null) {
            ItemInfo item = temp.getInfo();
            
            // Check if item is at the specified location (case insensitive)
            if (item.getLocation().equalsIgnoreCase(location)) {
                System.out.println(item.toString());
                found = true;
            }
            temp = temp.getNext();
        }
        
        if (!found) {
            System.out.println("No items found at location: " + location);
        }
    }

    /**
     * Moves all items that are in the store but on the wrong shelf back to their original locations.
     * Only affects items with shelf locations (starting with 's') that don't match their origin.
     * Items that are "out" or in carts are not affected.
     * Displays information about moved items.
     * 
     * Complexity: O(N) - Must examine each node to check if it needs to be moved,
     * but the actual move operation is O(1) per item.
     */
    public void cleanStore() {
        current = head;
        boolean foundItems = false;
        
        while (current != null) {
            ItemInfo item = current.getInfo();
            
            // Check if item is in store (starts with 's'), not "out", not in cart, and in wrong location
            if (item.getLocation().length() == 6 && 
            item.getLocation().charAt(0) == 's' && 
            item.getLocation().substring(1).matches("^[0-9]{5}$") && 
            !(item.getLocation().equals(item.getOrigin()))) {
                
                if (!foundItems) {
                    foundItems = true;
                }
                
                // Print item info BEFORE moving using toString()
                System.out.println(item.toString());
                
                // Move item back to original location
                try {
                    item.setLocation(item.getOrigin());
                } catch (IdTypeException e) {
                    System.out.println("Error moving item: " + e.getMessage());
                }
            }
            
            current = current.getNext();
        }
        
        if (!foundItems) {
            System.out.println("No items to move.");
        }
    }

    /**
     * Checks out all items in a specified cart by changing their location to "out".
     * Calculates and returns the total cost of all items in the cart.
     * Items are processed in the order they appear in the sorted list.
     * Displays items in a formatted table before checking them out.
     * 
     * Complexity: O(N) - Must examine each node to check if it's in the specified cart.
     * The location update operation is O(1) per matching item.
     * 
     * @param cartNumber the cart number to check out (format: c + 3 digits)
     * @return the total cost of all items that were in the cart
     * @throws IllegalArgumentException if cart number format is invalid
     */
    public double checkOut(String cartNumber) {
        if (cartNumber == null) {
            throw new IllegalArgumentException("Cart number cannot be null");
        }
        
        // Validate cart number format
        if (cartNumber.length() != 4 || cartNumber.charAt(0) != 'c' || 
            !cartNumber.substring(1).matches("^[0-9]{3}$")) {
            throw new IllegalArgumentException("Invalid cart number format. Must be 'c' followed by 3 digits.");
        }

        double total = 0.00;
        current = head; // Start from the beginning of the list
        boolean foundItems = false;
        
        while (current != null) {
            ItemInfo item = current.getInfo(); 
            
            // Check if item is in the specified cart
            if (item.getLocation().length() == 4 && 
                item.getLocation().charAt(0) == 'c' && 
                item.getLocation().substring(1).matches("^[0-9]{3}$") && 
                item.getLocation().equalsIgnoreCase(cartNumber)) { 
                
                // Print header only when first item is found
                if (!foundItems) {
                    System.out.println("Original Current");
                    System.out.println("Item Name RFID      Location  Location  Price");
                    System.out.println("--------- --------- --------- --------- ------");
                    foundItems = true;
                }
                
                // Display item before checking out using toString()
                System.out.println(item.toString());
                
                total += item.getPrice();
                
                // Check out the item immediately
                try {
                    item.setLocation("out");
                } catch (Exception e) {
                    System.out.println("Error checking out item: " + e.getMessage());
                }
            }
            
            current = current.getNext(); // Always move to next node
        }
        
        // Print total cost message
        if (foundItems) {
            String cartNum = cartNumber.substring(1); // Remove 'c' prefix for display
            System.out.printf("The total cost for all merchandise in cart %s was $%.2f%n", cartNum, total);
        }
        
        return total;
    }
}