/**
 * Represents an elevator in a building with state management and request handling capabilities.
 * The elevator can be in one of three states: idle, moving to source floor, or moving to destination floor.
 * It tracks its current floor position and the request it is currently serving.
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 1.0
 * @see Request
 */
public class Elevator {

    /** Constant representing the idle state - elevator is not moving and has no active request */
    public static final int IDLE = 0;
    
    /** Constant representing the to-source state - elevator is moving to pick up a passenger */
    public static final int TO_SOURCE = 1;
    
    /** Constant representing the to-destination state - elevator is moving to drop off a passenger */
    public static final int TO_DESTINATION = 2;

    /** The current floor where the elevator is located */
    private int currentFloor;
    
    /** The current state of the elevator (IDLE, TO_SOURCE, or TO_DESTINATION) */
    private int elevatorState;
    
    /** The request currently being served by the elevator, null if no active request */
    private Request request;

    /**
     * Constructs a new Elevator with default initial values.
     * The elevator starts on floor 1, in IDLE state, with no active request.
     */
    Elevator(){
        request = null;
        elevatorState = IDLE;
        currentFloor = 1;
    }

    /**
     * Gets the current floor where the elevator is located.
     * 
     * @return the current floor number
     */
    public int getCurrentFloor(){
        return currentFloor;
    }

    /**
     * Gets the current state of the elevator.
     * 
     * @return the elevator state (IDLE, TO_SOURCE, or TO_DESTINATION)
     */
    public int getElevatorState(){
        return elevatorState;
    }

    /**
     * Gets the request currently being served by the elevator.
     * 
     * @return the current Request object, or null if no request is active
     */
    public Request getRequest(){
        return request;
    }

    /**
     * Sets the current floor of the elevator.
     * Used to update the elevator's position as it moves between floors.
     * 
     * @param currentFloor the new floor number where the elevator is located
     * @throws IllegalArgumentException if currentFloor is negative
     */
    public void setCurrentFloor(int currentFloor) {
        if (currentFloor < 0) {
            throw new IllegalArgumentException("Floor must be positive");
        }
        this.currentFloor = currentFloor;
    }

    /**
     * Sets the state of the elevator.
     * Used to control the elevator's behavior and movement patterns.
     * 
     * @param elevatorState the new state (must be IDLE, TO_SOURCE, or TO_DESTINATION)
     * @throws IllegalArgumentException if elevatorState is not a valid state constant
     */
    public void setElevatorState(int elevatorState) {
        if (elevatorState < IDLE || elevatorState > TO_DESTINATION) {
            throw new IllegalArgumentException("Invalid elevator state");
        }
        this.elevatorState = elevatorState;
    }

    /**
     * Sets the request that the elevator should serve.
     * When a request is assigned, the elevator will move to serve that request.
     * 
     * @param request the Request object to be served, or null to clear the current request
     */
    public void setRequest(Request request) {
        this.request = request;
    }

}