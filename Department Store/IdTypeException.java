/**
 * Custom exception class for handling ID type-related errors.
 * This exception is thrown when there are issues with ID validation,
 * formatting, or type mismatches.
 * 
 * @author John Hartmann
 * @id 115764215
 * @recitation R30
 */
public class IdTypeException extends Exception {
    
    /**
     * Constructs a new IdTypeException with the specified detail message.
     * The message provides information about the specific ID type error
     * that occurred.
     * 
     * @param message the detail message explaining the cause of the exception
     */
    public IdTypeException(String message) {
        super(message);
    }
}