import java.util.Scanner;

/**
 * Main entry point for the elevator simulation system.
 * This class provides a command-line interface for users to input simulation parameters
 * and run elevator simulations with customizable settings.
 * 
 * The program prompts users for simulation parameters including probability of request arrivals,
 * building configuration, and simulation duration, then executes the simulation and displays results.
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 1.0
 * @see Simulator
 * @see Scanner
 */
public class Analyzer {

    /**
     * Main method that serves as the entry point for the elevator simulation program.
     * 
     * This method:
     * 1. Displays a welcome message to the user
     * 2. Prompts for and collects simulation parameters via console input
     * 3. Validates input implicitly through the Simulator class
     * 4. Executes the simulation with the provided parameters
     * 5. Displays simulation results and performance metrics
     * 
     * Input parameters collected:
     * - Probability of request arrival (0.0 to 1.0)
     * - Number of floors in the building
     * - Number of elevators available
     * - Simulation duration in time units
     * 
     * @param args command-line arguments (not used in this implementation)
     * 
     * @throws java.util.InputMismatchException if user enters invalid data types
     *         (e.g., non-numeric input when numbers are expected)
     * @throws IllegalArgumentException if invalid parameter values are provided
     *         (handled by underlying simulation classes)
     * 
     * @see Simulator#simulate(double, int, int, int)
     */
    public static void main(String[] args){

        /** Scanner object for reading user input from the console */
        Scanner input = new Scanner(System.in);

        // Display welcome message and program introduction
        System.out.println("Welcome to the Elevator simulator!");

        // Collect simulation parameters from user input
        System.out.println("Please enter the probability of arrival for Requests:");
        /** Probability that a new request will arrive at each time step (0.0 to 1.0) */
        double probability = input.nextDouble();
        
        System.out.println("Please enter the number of floors:");
        /** Total number of floors in the building being simulated */
        int floors = input.nextInt();
        
        System.out.println("Please enter the number of elevators:");
        /** Number of elevators available to serve requests */
        int elevators = input.nextInt();
        
        System.out.println("Please enter the length of the simulation (in time units):");
        /** Duration of the simulation in discrete time units */
        int time = input.nextInt();

        // Execute the elevator simulation with the collected parameters
        Simulator.simulate(probability, floors, elevators, time);
    }
}