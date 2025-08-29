/**
 * Represents information about an item with RFID tracking capabilities.
 * This class manages item details including name, price, RFID tag, and location tracking
 * with validation for proper format compliance.
 * 
 * RFID Tag Format: 9-character hexadecimal string (0-9, a-f, A-F)
 * Original Location Format: 6 characters starting with 's' followed by 5 digits (e.g., s90909)
 * Current Location Format: 4 characters starting with 'c' followed by 3 digits (e.g., c101), 
 *                          6 characters starting with 's' followed by 5 digits, or "out"
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 2025
 * Student ID: 115764215
 * Recitation: R30
 */
public class ItemInfo {
    
    /** The name of the item */
    private String name;
    
    /** The RFID tag number (9-character hexadecimal string) */
    private String rfidTagNumber;
    
    /** The original location where the item was first placed (format: s + 5 digits) */
    private String OriginalLocation;
    
    /** The current location of the item (shelf, cart, or "out") */
    private String CurrentLocation;
    
    /** The price of the item (must be positive) */
    private Double price;

    /**
     * Default constructor that initializes all fields to default values.
     * Sets strings to empty and price to 0.00.
     */
    ItemInfo() {
        name = "";
        rfidTagNumber = "";
        OriginalLocation = "";
        CurrentLocation = "";
        price = 0.00;
    }

    /**
     * Gets the name of the item.
     * 
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the item.
     * 
     * @return the price of the item as a Double
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Gets the RFID tag number of the item.
     * 
     * @return the 9-character hexadecimal RFID tag number
     */
    public String getTag() {
        return rfidTagNumber;
    }

    /**
     * Gets the original location where the item was first placed.
     * 
     * @return the original location in format s + 5 digits
     */
    public String getOrigin() {
        return OriginalLocation;
    }

    /**
     * Gets the current location of the item.
     * 
     * @return the current location (shelf, cart, or "out")
     */
    public String getLocation() {
        return CurrentLocation;
    }

    /**
     * Sets the name of the item.
     * 
     * @param name the name to set for the item
     * @throws IdTypeException if name is null
     */
    public void setName(String name) throws IdTypeException {
        if (name == null) {
            throw new IdTypeException("Name cannot be null");
        }
        this.name = name;
    }

    /**
     * Sets the price of the item.
     * 
     * @param price the price to set (must be positive)
     * @throws IdTypeException if price is less than or equal to 0
     */
    public void setPrice(double price) throws IdTypeException {
        if (price <= 0) {
            throw new IdTypeException("Price must be positive");
        }
        this.price = price;
    }

    /**
     * Sets the RFID tag number for the item.
     * The tag must be exactly 9 characters and contain only hexadecimal digits.
     * 
     * @param rfidTagNumber the RFID tag number to set
     * @throws IdTypeException if rfidTagNumber is null, not exactly 9 characters,
     *                        or contains non-hexadecimal characters
     */
    public void setTag(String rfidTagNumber) throws IdTypeException {
        if (rfidTagNumber == null) {
            throw new IdTypeException("RFID cannot be null");
        }
        if (rfidTagNumber.length() != 9) {
            throw new IdTypeException("RFID must be exactly 9 characters");
        }
        if (!rfidTagNumber.matches("^[0-9a-fA-F]+$")) {
            throw new IdTypeException("RFID must contain only 0-9, a-f, A-F");
        }
        this.rfidTagNumber = rfidTagNumber.toLowerCase();
    }

    /**
     * Sets the original location of the item.
     * The location must be exactly 6 characters starting with 's' followed by 5 digits.
     * Also sets the current location to the same value initially.
     * 
     * @param OriginalLocation the original location to set
     * @throws IdTypeException if OriginalLocation is null, not exactly 6 characters,
     *                        doesn't start with 's', or doesn't have 5 digits after 's'
     */
    public void setOrigin(String OriginalLocation) throws IdTypeException {
        if (OriginalLocation == null) {
            throw new IdTypeException("Original location cannot be null");
        }
        if (OriginalLocation.length() != 6) {
            throw new IdTypeException("Original location must be exactly 6 characters");
        }
        if (OriginalLocation.charAt(0) != 's') {
            throw new IdTypeException("Original location must start with 's'");
        }
        if (!OriginalLocation.substring(1).matches("^[0-9]{5}$")) {
            throw new IdTypeException("Original location must have 5 digits after 's'");
        }
        this.OriginalLocation = OriginalLocation;
        this.CurrentLocation = OriginalLocation;
    }

    /**
     * Sets the current location of the item.
     * Valid formats include:
     * - "out" (case-insensitive)
     * - Shelf location: 's' followed by 5 digits (6 characters total)
     * - Cart location: 'c' followed by 3 digits (4 characters total)
     * 
     * @param CurrentLocation the current location to set
     * @throws IdTypeException if CurrentLocation is null or doesn't match any valid format
     */
    public void setLocation(String CurrentLocation) throws IdTypeException {
        if (CurrentLocation == null) {
            throw new IdTypeException("Current location cannot be null");
        }
        
        if (CurrentLocation.equalsIgnoreCase("out")) {
            this.CurrentLocation = "out";
            return;
        }
        
        if (CurrentLocation.length() == 6 && CurrentLocation.charAt(0) == 's' && 
            CurrentLocation.substring(1).matches("^[0-9]{5}$")) {
            this.CurrentLocation = CurrentLocation;
            return;
        }
        
        if (CurrentLocation.length() == 4 && CurrentLocation.charAt(0) == 'c' && 
            CurrentLocation.substring(1).matches("^[0-9]{3}$")) {
            this.CurrentLocation = CurrentLocation;
            return;
        }
        
        throw new IdTypeException("Current location must be shelf (s+5 digits), cart (c+3 digits), or 'out'");
    }

    /**
     * Returns a formatted string representation of the ItemInfo object.
     * Standardized format: Item Name RFID Location Location Price
     * 
     * @return formatted string with item information
     */
    @Override
    public String toString() {
        return String.format("%-9s %-9s %-9s %-9s %6.2f",
                         name, rfidTagNumber, OriginalLocation, CurrentLocation, price);
    }
}