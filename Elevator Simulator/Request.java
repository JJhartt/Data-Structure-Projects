/**
 * Represents an elevator request with source and destination floors and entry time.
 * This class models a request made by a user to travel between floors in a building.
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 1.0
 */
public class Request {

    /** The floor where the request originates */
    private int sourceFloor;
    
    /** The floor where the user wants to go */
    private int destinationFloor;
    
    /** The time when the request was entered into the system */
    private int timeEntered;

    /**
     * Constructs a new Request with randomly generated source and destination floors.
     * Both floors are randomly selected between 1 and numFloors (inclusive).
     * The timeEntered is initialized to 0.
     * 
     * @param numFloors the total number of floors in the building
     * @throws IllegalArgumentException if numFloors is less than or equal to 1
     */
    Request(int numFloors){
         if (numFloors <= 1) {
            throw new IllegalArgumentException("Number of floors must be greater than 1");
        }
        sourceFloor = (int)((Math.random()) * numFloors) + 1;
        destinationFloor = (int)(Math.random() * numFloors) + 1;
    }

    /**
     * Gets the source floor of the request.
     * 
     * @return the floor number where the request originates
     */
    public int getSourceFloor(){
        return sourceFloor;
    }

    /**
     * Gets the destination floor of the request.
     * 
     * @return the floor number where the user wants to go
     */
    public int getDestinationFloor(){
        return destinationFloor;
    }

    /**
     * Gets the time when the request was entered.
     * 
     * @return the time the request was entered into the system
     */
    public int getTimeEntered(){
        return timeEntered;
    }

    /**
     * Sets the source floor for the request.
     * Any exceptions that occur during assignment are caught and their messages are printed.
     * 
     * @param sourceFloor the new source floor number
     */
    public void setSourceFloor(int sourceFloor){
        try{
            this.sourceFloor = sourceFloor;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets the destination floor for the request.
     * Any exceptions that occur during assignment are caught and their messages are printed.
     * 
     * @param destinationFloor the new destination floor number
     */
    public void setDestinationFloor(int destinationFloor){
        try{
            this.destinationFloor = destinationFloor;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets the time when the request was entered.
     * Any exceptions that occur during assignment are caught and their messages are printed.
     * 
     * @param timeEntered the time the request was entered into the system
     */
    public void setTimeEntered(int timeEntered){
        try{
            this.timeEntered = timeEntered;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}