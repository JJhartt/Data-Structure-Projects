/**
 * A discrete-time elevator simulation system that models elevator operations in a building.
 * This class simulates the arrival of requests, elevator movement, and calculates performance metrics
 * such as total wait time and average wait time for passengers.
 * 
 * The simulation runs for a specified duration and tracks multiple elevators serving requests
 * in a building with a given number of floors.
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 1.0
 * @see BooleanSource
 * @see RequestQueue
 * @see Elevator
 * @see Request
 */
public class Simulator {

    /**
     * Runs an elevator simulation with the specified parameters.
     * 
     * The simulation operates in discrete time steps, where at each time unit:
     * 1. New requests may arrive based on the given probability
     * 2. Idle elevators are assigned to pending requests
     * 3. Elevators move one floor closer to their targets
     * 4. Wait times are calculated when elevators reach source floors
     * 
     * Performance metrics including total requests served, total wait time,
     * and average wait time are calculated and displayed at the end.
     * 
     * @param probability the probability (0.0 to 1.0) that a new request arrives at each time step
     * @param numFloors the total number of floors in the building (must be greater than 1)
     * @param numElevators the number of elevators available to serve requests
     * @param length the duration of the simulation in time units
     * 
     * @throws IllegalArgumentException if probability is outside [0.0, 1.0] range (from BooleanSource),
     *                                 if numFloors is less than or equal to 1 (from Request constructor),
     *                                 or if any parameter is negative
     */
    static void simulate(double probability, int numFloors, int numElevators, int length){
        
        // Initialize simulation components
        /** Source for generating random request arrivals based on probability */
        BooleanSource arrival = new BooleanSource(probability);
        
        /** Queue to store pending elevator requests in FIFO order */
        RequestQueue requests = new RequestQueue();
        
        /** Array of elevators available to serve requests */
        Elevator[] elevators = new Elevator[numElevators];
        
        // Initialize all elevators to starting position and state
        for (int i = 0; i < numElevators; i++) {
            elevators[i] = new Elevator();
            System.out.println(elevators[i].getCurrentFloor());
        }

        /** Counter for total number of requests that have been served */
        int totalRequests = 0;
        
        /** Accumulator for total wait time across all served requests */
        int totalWaitTime = 0;

        // Main simulation loop - runs for specified duration
        for(int time = 1; time <= length; time++){
            
            // Check if a new request arrives at this time step
            if(arrival.requestArrived()){
                Request newRequest = new Request(numFloors);
                newRequest.setTimeEntered(time);
                requests.enqueue(newRequest);
                System.out.println("Source: "+ newRequest.getSourceFloor() + " Destination: " + newRequest.getDestinationFloor());
            }

            // Process each elevator's current state and movement
            for(Elevator elevator : elevators){
                
                // Assign pending requests to idle elevators
                if(elevator.getElevatorState() == Elevator.IDLE && !requests.isEmpty()){
                    Request request = requests.dequeue();
                    elevator.setRequest(request);
                    elevator.setElevatorState(Elevator.TO_SOURCE);
                }

                // Handle elevator movement based on current state
                switch (elevator.getElevatorState()){
                    
                    case Elevator.TO_SOURCE:
                        // Move elevator one floor closer to the source floor
                        int current = elevator.getCurrentFloor();
                        int source = elevator.getRequest().getSourceFloor();

                        if(current != source){
                            elevator.setCurrentFloor(current + (source > current ? 1 : -1));
                        }

                        // Check if elevator has reached the source floor
                        if(elevator.getCurrentFloor() == source){
                            totalWaitTime += time - elevator.getRequest().getTimeEntered();
                            totalRequests++;
                            elevator.setElevatorState(Elevator.TO_DESTINATION);
                        }
                        break;

                    case Elevator.TO_DESTINATION:
                        // Move elevator one floor closer to the destination floor
                        current = elevator.getCurrentFloor();
                        int dest = elevator.getRequest().getDestinationFloor();

                        if(current != dest){
                            elevator.setCurrentFloor(current + (dest > current ? 1 : -1));
                        }

                        // Check if elevator has reached the destination floor
                        if(elevator.getCurrentFloor() == dest){
                            elevator.setElevatorState(Elevator.IDLE);
                            elevator.setRequest(null);
                        }
                        break;

                    case Elevator.IDLE:
                        // Elevator remains stationary when idle
                        break;
                }
            }
        }

        // Display simulation results and performance metrics
        System.out.println("Elevator Results:");
        System.out.println("Total Wait Time: " + totalWaitTime);
        System.out.println("Total Requests: " + totalRequests);

        // Calculate and display average wait time
        if(totalRequests > 0) {
            double avgWaitTime = (double)totalWaitTime / totalRequests;
            System.out.println("Average Wait Time: " + String.format("%.2f", avgWaitTime));
        } else {
            System.out.println("Average Wait Time: 0.00");
        }
    }
}