import java.util.Scanner;

/**
 * PlaylistOperations class provides a menu-driven interface for managing a playlist.
 * Users can add songs, remove songs, search by artist, and perform other playlist operations.
 */
public class PlaylistOperations {

    /**
     * Main method that runs the playlist management application.
     * Provides a menu-driven interface for various playlist operations.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        Playlist myplaylist = new Playlist();
        boolean run = true;

        while(run){
            System.out.println("A) Add Song ");
            System.out.println("B) Print Songs by Artist ");
            System.out.println("G) Get Song ");
            System.out.println("R) Remove Song ");
            System.out.println("P) Print All Songs ");
            System.out.println("S) Size ");
            System.out.println("Q) Quit ");
            System.out.println("Select a menu option:");

            String option = scanner.nextLine();

            switch (option.toUpperCase()){
                case "A":
                    try {
                        SongRecord song = new SongRecord();
                        System.out.println("Enter the song title:");
                        String title = scanner.nextLine();
                        song.setTitle(title);
                        
                        System.out.println("Enter the song artist:");
                        String artist = scanner.nextLine();
                        song.setArtist(artist);
                        
                        System.out.println("Enter the song length (minutes):");
                        int minutes = scanner.nextInt();
                        
                        System.out.println("Enter the song length (seconds):");
                        int seconds = scanner.nextInt();
                        
                        // Try to set minutes and seconds - catch song length errors here
                        try {
                            song.setMinutes(minutes);
                            song.setSeconds(seconds);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                            scanner.nextLine(); // clear the newline
                            break; // Exit the case
                        }
                        
                        System.out.println("Enter the position:");
                        int position = scanner.nextInt();
                        scanner.nextLine(); // consume newline BEFORE calling addSong
                        
                        // Try to add song - catch position errors here
                        try {
                            myplaylist.addSong(song, position);
                            System.out.println("Song Added: " + title + " By " + artist);
                            System.out.println("Song successfully added at position " + position);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid position for adding the new song");
                        }
                        
                    } catch (FullPlaylistException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Invalid input for adding the new song");
                        if (scanner.hasNextLine()) {
                            scanner.nextLine(); // clear invalid input
                        }
                    }
                    break;

                case "B":
                    System.out.println("Enter the artist name: ");
                    String artGet = scanner.nextLine();
                    Playlist artistSongs = Playlist.getSongsByArtist(myplaylist, artGet);
                    
                    if (artistSongs != null && artistSongs.size() > 0) {
                        System.out.println("Songs by " + artGet + ":");
                        artistSongs.printAllSongs();
                    } else {
                        System.out.println("No songs found by artist: " + artGet);
                    }
                    break;

                case "G":
                    int posGet = 0;
                    try {
                        System.out.println("Enter the position: ");
                        posGet = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        
                        SongRecord retrievedSong = myplaylist.getSong(posGet);
                        System.out.println("Song at position " + posGet + ": " + retrievedSong.toString());
                        
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid position.");
                        if (scanner.hasNextLine()) {
                            scanner.nextLine(); // clear invalid input
                        }
                    }
                    break;

                case "R":
                    int posRemove = 0;
                    try {
                        System.out.println("Enter the position: ");
                        posRemove = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        
                        myplaylist.removeSong(posRemove);
                        System.out.println("Song Removed at position " + posRemove);
                        
                    } catch (IllegalArgumentException e) {
                        System.out.println("No song at position " + posRemove + " to remove");
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid position.");
                        if (scanner.hasNextLine()) {
                            scanner.nextLine(); // clear invalid input
                        }
                    }
                    break;

                case "P":
                    if (myplaylist.size() > 0) {
                        myplaylist.printAllSongs();
                    } else {
                        System.out.println("The playlist is empty.");
                    }
                    break;

                case "S":
                    System.out.println("There are " + myplaylist.size() + " song(s) in the current playlist.");
                    break;

                case "Q":
                    run = false;
                    System.out.println("Program terminating normally...");
                    break;
                    
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }    
}