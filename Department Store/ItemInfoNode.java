/**
 * Represents a node in a doubly-linked list that contains ItemInfo data.
 * This class provides the structure for building linked lists of items
 * with forward and backward traversal capabilities.
 * 
 * Each node contains:
 * - An ItemInfo object with item details
 * - A reference to the next node in the list
 * - A reference to the previous node in the list
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 2025
 * Student ID: 115764215
 * Recitation: R30
 */
public class ItemInfoNode {
    
    /** The ItemInfo object containing the item's data */
    private ItemInfo itemInfo;
    
    /** Reference to the next node in the linked list */
    private ItemInfoNode nextlink;
    
    /** Reference to the previous node in the linked list */
    private ItemInfoNode prevlink;

    /**
     * Constructs a new ItemInfoNode with default values.
     * Initializes all instance variables to null, creating an empty node
     * that can be used as a starting point in a doubly linked list structure.
     * 
     */

    public ItemInfoNode() {
        this.itemInfo = null;
        this.nextlink = null;  
        this.prevlink = null;
    }
    /**
     * Constructs a new ItemInfoNode with the specified ItemInfo.
     * Initializes next and previous links to null, creating an isolated node.
     * 
     * @param itemInfo the ItemInfo object to be stored in this node
     * @throws IllegalArgumentException if itemInfo is null
     */
    public ItemInfoNode(ItemInfo itemInfo) {
        if (itemInfo == null) {
            throw new IllegalArgumentException("ItemInfo cannot be null");
        }
        this.itemInfo = itemInfo;
        this.nextlink = null;
        this.prevlink = null;
    }

    /**
     * Gets the ItemInfo object stored in this node.
     * 
     * @return the ItemInfo object containing item details
     */
    public ItemInfo getInfo() {
        return itemInfo;
    }

    /**
     * Gets the RFID tag ID from the ItemInfo object stored in this node.
     * This is a convenience method that retrieves the tag from the contained ItemInfo.
     * 
     * @return the RFID tag number as a String, or null if ItemInfo is null
     */
    public String getID() {
        return itemInfo != null ? itemInfo.getTag() : null;
    }

    /**
     * Gets the reference to the next node in the linked list.
     * 
     * @return the next ItemInfoNode, or null if this is the last node
     */
    public ItemInfoNode getNext() {
        return nextlink;
    }

    /**
     * Gets the reference to the previous node in the linked list.
     * 
     * @return the previous ItemInfoNode, or null if this is the first node
     */
    public ItemInfoNode getPrev() {
        return prevlink;
    }

    /**
     * Sets the reference to the previous node in the linked list.
     * 
     * @param prevlink the ItemInfoNode to set as the previous node (can be null)
     */
    public void setPrev(ItemInfoNode prevlink) {
        this.prevlink = prevlink;
    }

    /**
     * Sets the reference to the next node in the linked list.
     * 
     * @param nextlink the ItemInfoNode to set as the next node (can be null)
     */
    public void setNext(ItemInfoNode nextlink) {
        this.nextlink = nextlink;
    }

    /**
     * Sets the ItemInfo object for this node.
     * Replaces the current ItemInfo with the provided one.
     * 
     * @param info the new ItemInfo object to store in this node
     * @throws IllegalArgumentException if info is null
     */
    public void setItemInfo(ItemInfo info) {
        if (info == null) {
            throw new IllegalArgumentException("ItemInfo cannot be null");
        }
        this.itemInfo = info;
    }

    /**
     * Returns a string representation of this node.
     * Delegates to the toString method of the contained ItemInfo object.
     * 
     * @return a formatted string representation of the ItemInfo, 
     *         or "null" if ItemInfo is null
     */
    @Override
    public String toString() {
        return itemInfo != null ? itemInfo.toString() : "null";
    }

    /**
     * Checks if this node has a next node.
     * 
     * @return true if there is a next node, false otherwise
     */
    public boolean hasNext() {
        return nextlink != null;
    }

    /**
     * Checks if this node has a previous node.
     * 
     * @return true if there is a previous node, false otherwise
     */
    public boolean hasPrev() {
        return prevlink != null;
    }
}