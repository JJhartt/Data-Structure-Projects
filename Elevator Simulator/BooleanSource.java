/**
 * A probabilistic boolean generator that simulates random events based on a given probability.
 * This class is commonly used in simulations to model the arrival of requests or events
 * with a specified likelihood of occurrence.
 * 
 * @author John Hartmann
 * @version 1.0
 * @since 1.0
 */
public class BooleanSource {

    /** The probability value used to determine boolean outcomes (between 0.0 and 1.0) */
    public double probability;

    /**
     * Constructs a new BooleanSource with the specified probability.
     * The probability determines the likelihood that requestArrived() will return true.
     * 
     * @param probability the probability value that determines the likelihood of true outcomes,
     *                   must be between 0.0 (never true) and 1.0 (always true) inclusive
     * @throws IllegalArgumentException if probability is less than 0.0 or greater than 1.0
     */
    BooleanSource(double probability){
        if(probability < 0.0 || probability > 1.0){
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        this.probability = probability;
    }

    /**
     * Determines if a request has arrived based on the configured probability.
     * Uses a random number generator to simulate probabilistic events.
     * 
     * @return true if a random value is less than or equal to the probability,
     *         false otherwise. Higher probability values increase the likelihood of returning true.
     */
    public boolean requestArrived(){
        return Math.random() <= probability;
    }

    /**
     * Gets the current probability value used for generating boolean outcomes.
     * 
     * @return the probability value between 0.0 and 1.0
     */
    public double getProbability(){
        return probability;
    }
}