import java.util.Scanner;

/**
 * SongRecord class represents information about a particular audio file.
 * Contains title, artist, and song length in minutes and seconds.
 * 
 * @author John Hartmann
 * @version 1.0
 */
public class SongRecord {
    
    // Member variables for song information
    private String title;
    private String artist;
    private int minutes;
    private int seconds;
    private int position;

    /**
     * Default constructor initializes all fields to default values.
     * Title and artist are set to empty strings.
     * Minutes and seconds are set to 0.
     */
    public SongRecord() {
        this.title = "";
        this.artist = "";
        this.minutes = 0;
        this.seconds = 0;
        this.position = 0;
    }

    // Accessor methods
    
    /**
     * Returns the title of the song.
     * @return the song title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the artist of the song.
     * @return the artist name as a String
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Returns the minutes component of the song length.
     * @return the minutes as an int
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Returns the seconds component of the song length.
     * @return the seconds as an int
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Returns the position component of the song.
     * @return the position as an int
     */
    public int getPosition(){
        return position;
    }

    // Mutator methods (setters)
    
    /**
     * Sets the title of the song.
     * @param title the new title for the song
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the artist of the song.
     * @param artist the new artist name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

     /**
     * Sets the position of the song.
     * @param position the new position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Sets the minutes component of the song length.
     * @param minutes the new minutes value
     * @throws IllegalArgumentException if minutes is negative
     */
    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Invalid song length");
        }
        this.minutes = minutes;
    }

    /**
     * Sets the seconds component of the song length.
     * @param seconds the new seconds value
     * @throws IllegalArgumentException if seconds is less than 0 or greater than 59
     */
    public void setSeconds(int seconds) {
        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException("Invalid song length");
        }
        this.seconds = seconds;
    }

    /**
     * Returns a formatted string representation of the song record.
     * Displays title, artist, and length in mm:ss format on a single line.
     * @return formatted string containing song information
     */
    @Override
    public String toString() {
        return String.format("Title: %s, Artist: %s, Length: %d:%02d", 
                           title, artist, minutes, seconds);
    }
}