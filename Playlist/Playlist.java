public class Playlist {
    
    /** Maximum number of songs allowed in the playlist */
    private static final int MAX_SONGS = 50;
    
    /** Array to store SongRecord objects */
    private SongRecord[] songs;
    
    /** Current number of songs in the playlist */
    private int currentSize;
    
    /**
     * Construct an instance of the Playlist class with no SongRecord objects in it.
     * Postcondition: This Playlist has been initialized to an empty list of SongRecords.
     */
    public Playlist() {
        songs = new SongRecord[MAX_SONGS];
        currentSize = 0;
    }
    
    /**
     * Generate a copy of this Playlist.
     * @return The return value is a copy of this Playlist. Subsequent changes to the copy 
     *         will not affect the original, nor vice versa.
     */
    public Object clone() {
        Playlist copy = new Playlist();
        copy.currentSize = this.currentSize;
        
        // Deep copy each SongRecord
        for (int i = 0; i < currentSize; i++) {
            copy.songs[i] = new SongRecord();
            copy.songs[i].setTitle(this.songs[i].getTitle());
            copy.songs[i].setArtist(this.songs[i].getArtist());
            copy.songs[i].setMinutes(this.songs[i].getMinutes());
            copy.songs[i].setSeconds(this.songs[i].getSeconds());
        }
        
        return copy;
    }
    
    /**
     * Compare this Playlist to another object for equality.
     * @param obj an object in which this Playlist is compared
     * @return A return value of true indicates that obj refers to a Playlist object with 
     *         the same SongRecords in the same order as this Playlist. Otherwise, false.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Playlist)) {
            return false;
        }
        
        Playlist other = (Playlist) obj;
        
        if (this.currentSize != other.currentSize) {
            return false;
        }
        
        for (int i = 0; i < currentSize; i++) {
            if (!songsEqual(this.songs[i], other.songs[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Helper method to compare two SongRecord objects for equality
     * @param song1 first song to compare
     * @param song2 second song to compare
     * @return true if songs have same title, artist, and length
     */
    private boolean songsEqual(SongRecord song1, SongRecord song2) {
        if (song1 == null && song2 == null) return true;
        if (song1 == null || song2 == null) return false;
        
        return song1.getTitle().equals(song2.getTitle()) &&
               song1.getArtist().equals(song2.getArtist()) &&
               song1.getMinutes() == song2.getMinutes() &&
               song1.getSeconds() == song2.getSeconds();
    }
    
    /**
     * Determines the number of SongRecords currently in this Playlist.
     * @return The number of SongRecords in this Playlist.
     */
    public int size() {
        return currentSize;
    }
    
    /**
     * Add a new SongRecord to the playlist at the specified position.
     * @param song the new SongRecord object to add to this Playlist
     * @param position the position in the playlist where the song will be inserted (1-based)
     * @throws IllegalArgumentException if position is not within the valid range
     * @throws FullPlaylistException if there is no more room in the Playlist
     */
    public void addSong(SongRecord song, int position) throws IllegalArgumentException, FullPlaylistException {
        if (position < 1 || position > currentSize + 1) {
            throw new IllegalArgumentException("Invalid position for adding the new song");
        }
        
        if (currentSize >= MAX_SONGS) {
            throw new FullPlaylistException("Playlist is full. Cannot add more songs.");
        }
        
        // Convert to 0-based index
        int index = position - 1;
        
        // Shift elements to the right
        for (int i = currentSize; i > index; i--) {
            songs[i] = songs[i - 1];
        }
        
        // Insert the new song
        songs[index] = song;
        currentSize++;
    }
    
    /**
     * Remove the SongRecord at the specified position.
     * @param position the position in the playlist where the song will be removed from (1-based)
     * @throws IllegalArgumentException if position is not within the valid range
     */
    public void removeSong(int position) throws IllegalArgumentException {
        if (position < 1 || position > currentSize) {
        throw new IllegalArgumentException("No song at position " + position + " to remove");
    }
        
        // Convert to 0-based index
        int index = position - 1;
        
        // Shift elements to the left
        for (int i = index; i < currentSize - 1; i++) {
            songs[i] = songs[i + 1];
        }
        
        // Clear the last element
        songs[currentSize - 1] = null;
        currentSize--;
    }
    
    /**
     * Get the SongRecord at the given position in this Playlist object.
     * @param position position of the SongRecord to retrieve (1-based)
     * @return The SongRecord at the specified position in this Playlist object.
     * @throws IllegalArgumentException if position is not within the valid range
     */
    public SongRecord getSong(int position) throws IllegalArgumentException {
        if (position < 1 || position > currentSize) {
            throw new IllegalArgumentException("Position must be between 1 and " + currentSize);
        }
        
        // Convert to 0-based index
        return songs[position - 1];
    }
    
    /**
     * Prints a neatly formatted table of each SongRecord in the Playlist on its own line
     * with its position number.
     */
    public void printAllSongs() {
        System.out.println(toString());
    }
    
    /**
     * Generates a new Playlist containing all SongRecords in the original Playlist
     * performed by the specified artist.
     * @param originalList the original Playlist
     * @param artist the name of the artist
     * @return A new Playlist object containing all SongRecords performed by the specified artist,
     *         or null if originalList or artist is null
     */
    public static Playlist getSongsByArtist(Playlist originalList, String artist) {
        if (originalList == null || artist == null) {
            return null;
        }
        
        Playlist filteredList = new Playlist();
        
        for (int i = 0; i < originalList.currentSize; i++) {
            if (originalList.songs[i].getArtist().equalsIgnoreCase(artist)) {
                try {
                    filteredList.addSong(originalList.songs[i], filteredList.currentSize + 1);
                } catch (Exception e) {
                    System.err.println("Error adding song to filtered list: " + e.getMessage());
                }
            }
        }
        
        return filteredList;
    }
    
    /**
     * Gets the String representation of this Playlist object, which is a neatly formatted
     * table of each SongRecord in the Playlist on its own line with its position number.
     * @return The String representation of this Playlist object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Position | Title                | Artist                    | Length\n");
        sb.append("---------|----------------------|---------------------------|--------\n");
        
        for (int i = 0; i < currentSize; i++) {
            sb.append(String.format("%-9d%-21s%-26s%d:%02d\n",
                                   i + 1,  // Position (1-based)
                                   songs[i].getTitle(),
                                   songs[i].getArtist(),
                                   songs[i].getMinutes(),
                                   songs[i].getSeconds()));
        }
        
        return sb.toString();
    }
}