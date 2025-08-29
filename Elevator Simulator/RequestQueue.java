import java.util.ArrayList;

/**
 * A queue implementation for managing Request objects using FIFO (First In, First Out) ordering.
 * This class extends ArrayList to provide queue-specific operations for elevator requests.
 * Elements are added to the end of the queue and removed from the front.
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 1.0
 * @see Request
 * @see ArrayList
 */
public class RequestQueue extends ArrayList<Request> {

    /**
     * Constructs an empty RequestQueue.
     * Initializes the underlying ArrayList with default capacity.
     */
    RequestQueue(){
        super();
    }

    /**
     * Adds a Request to the end of the queue (enqueue operation).
     * This follows the FIFO principle where new elements are added to the rear of the queue.
     * 
     * @param element the Request object to be added to the queue
     * @throws NullPointerException if the specified element is null (inherited from ArrayList)
     */
    public void enqueue(Request element){
        this.add(element);
    }

    /**
     * Removes and returns the Request at the front of the queue (dequeue operation).
     * This follows the FIFO principle where the oldest element is removed first.
     * 
     * @return the Request object that was at the front of the queue
     * @throws IllegalStateException if the queue is empty
     */
    public Request dequeue(){
        if(isEmpty()){
            throw new IllegalStateException("Queue is empty.");
        }
        return this.remove(0);
    }

    /**
     * Checks whether the queue is empty.
     * This method overrides the parent isEmpty() method for clarity and potential
     * future customization of empty-checking behavior.
     * 
     * @return true if the queue contains no elements, false otherwise
     */
    @Override
    public boolean isEmpty(){
        return super.isEmpty();
    }
}