import java.util.ArrayList;
import java.io.Serializable;

/**
 * Represents an email folder that stores and manages Email objects.
 * Provides functionality for adding, removing, and sorting emails by subject or date.
 * Maintains the current sorting method to ensure new emails are inserted in the correct order.
 * @author John Hartmann
 * @recitation R30
 * @version 1.0
 */

public class Folder implements Serializable {
    
    private ArrayList<Email> emails;
    private String name,currentSortingMethod;
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor - creates an empty folder with no sorting (insertion order)
     */
    public Folder() {
        this.emails = new ArrayList<Email>();
        this.name = null;
        this.currentSortingMethod = "None"; 
    }
    
    /**
     * Constructor with folder name - creates an empty folder with the specified name
     * @param name the name of the folder
     */
    public Folder(String name) {
        this.emails = new ArrayList<Email>();
        this.name = name;
        this.currentSortingMethod = "None"; 
    }
    
    /**
     * Gets the name of the folder
     * @return the folder name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the list of emails in the folder
     * @return ArrayList of emails
     */
    public ArrayList<Email> getEmails() {
        return emails;
    }
    
    /**
     * Gets the current sorting method
     * @return the current sorting method as a string
     */
    public String getSortingMethod() {
        return currentSortingMethod;
    }
    
    /**
     * Gets the number of emails in the folder
     * @return the count of emails in this folder
     */
    public int getEmailCount() {
        return emails.size();
    }
    
    /**
     * Sets the name of the folder
     * @param name the new folder name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the current sorting method
     * @param currentSortingMethod the new sorting method
     */
    public void setSortingMethod(String currentSortingMethod) {
        this.currentSortingMethod = currentSortingMethod;
    }
    
    /**
     * Adds an email to the folder according to the current sorting method.
     * The email is inserted in the correct position to maintain the current sort order.
     * @param email the email to add
     */
    public void addEmail(Email email) {
        if (email == null) {
            return; 
        }
        
        if (currentSortingMethod.equals("None")) {
            emails.add(email);
            return;
        }

        if (emails.isEmpty()) {
            emails.add(email);
            return;
        }
        
        // Find the correct position to insert based on current sorting method
        int insertPosition = findInsertPosition(email);
        emails.add(insertPosition, email);
    }
    
    /**
     * Finds the correct insertion position for a new email based on current sorting method
     * @param email the email to insert
     * @return the index where the email should be inserted
     */
    private int findInsertPosition(Email email) {
        switch (currentSortingMethod) {
            case "Subject Ascending":
                for (int i = 0; i < emails.size(); i++) {
                    if (compareSubjects(email, emails.get(i)) <= 0) {
                        return i;
                    }
                }
                break;
            case "Subject Descending":
                for (int i = 0; i < emails.size(); i++) {
                    if (compareSubjects(email, emails.get(i)) >= 0) {
                        return i;
                    }
                }
                break;
            case "Date Ascending":
                for (int i = 0; i < emails.size(); i++) {
                    if (compareDates(email, emails.get(i)) <= 0) {
                        return i;
                    }
                }
                break;
            case "Date Descending":
                for (int i = 0; i < emails.size(); i++) {
                    if (compareDates(email, emails.get(i)) >= 0) {
                        return i;
                    }
                }
                break;
        }
        return emails.size(); 
    }
    
    /**
     * Removes an email from the folder by index
     * @param index the index of the email to remove
     * @return the removed email, or null if index is invalid
     */
    public Email removeEmail(int index) {
        if (index < 0 || index >= emails.size()) {
            return null; // Invalid index
        }
        return emails.remove(index);
    }
    
    /**
     * Sorts the emails alphabetically by subject in ascending order using insertion sort
     */
    public void sortBySubjectAscending() {
        int n = emails.size();
        
        for (int i = 1; i < n; i++) {

            Email key = emails.get(i);
            int j = i - 1;
            
            while (j >= 0 && compareSubjects(emails.get(j), key) > 0) {
                emails.set(j + 1, emails.get(j));
                j = j - 1;
            }
            emails.set(j + 1, key);
        }
        
        currentSortingMethod = "Subject Ascending";
    }
    
    /**
     * Sorts the emails alphabetically by subject in descending order using insertion sort
     */
    public void sortBySubjectDescending() {
        int n = emails.size();
        
        for (int i = 1; i < n; i++) {
            Email key = emails.get(i);
            int j = i - 1;
            
            while (j >= 0 && compareSubjects(emails.get(j), key) < 0) {
                emails.set(j + 1, emails.get(j));
                j = j - 1;
            }
            emails.set(j + 1, key);
        }
        
        currentSortingMethod = "Subject Descending";
    }
    
    /**
     * Sorts the emails by date in ascending order (oldest first) using insertion sort
     */
    public void sortByDateAscending() {
        int n = emails.size();
        
        for (int i = 1; i < n; i++) {
            Email key = emails.get(i);
            int j = i - 1;
            
            while (j >= 0 && compareDates(emails.get(j), key) > 0) {
                emails.set(j + 1, emails.get(j));
                j = j - 1;
            }
            emails.set(j + 1, key);
        }
        
        currentSortingMethod = "Date Ascending";
    }
    
    /**
     * Sorts the emails by date in descending order (newest first) using insertion sort
     */
    public void sortByDateDescending() {
        int n = emails.size();
        
        for (int i = 1; i < n; i++) {
            Email key = emails.get(i);
            int j = i - 1;
            
            while (j >= 0 && compareDates(emails.get(j), key) < 0) {
                emails.set(j + 1, emails.get(j));
                j = j - 1;
            }
            emails.set(j + 1, key);
        }
        
        currentSortingMethod = "Date Descending";
    }
    
    /**
     * Compares two emails by their subjects, handling null values appropriately
     * @param email1 first email to compare
     * @param email2 second email to compare
     * @return negative if email1 < email2, positive if email1 > email2, 0 if equal
     */
    private static int compareSubjects(Email email1, Email email2) {
        String subject1 = email1.getSubject();
        String subject2 = email2.getSubject();
        
        if (subject1 == null && subject2 == null) {
            return 0;
        }
        if (subject1 == null) {
            return 1; 
        }
        if (subject2 == null) {
            return -1;
        }
        
        return compareStringsIgnoreCase(subject1, subject2);
    }
    
    /**
     * Compares two emails by their timestamps, handling null values appropriately
     * @param email1 first email to compare
     * @param email2 second email to compare
     * @return negative if email1 < email2, positive if email1 > email2, 0 if equal
     */
    private int compareDates(Email email1, Email email2) {

        if (email1.getTimestamp() == null && email2.getTimestamp() == null) {
            return 0;
        }
        if (email1.getTimestamp() == null) {
            return 1;
        }
        if (email2.getTimestamp() == null) {
            return -1;
        }
        
        return email1.getTimestamp().compareTo(email2.getTimestamp());
    }
    
    /**
     * Compares two strings ignoring case, using character-by-character comparison
     * @param str1 first string to compare
     * @param str2 second string to compare
     * @return negative if str1 < str2, positive if str1 > str2, 0 if equal
     */
    private static int compareStringsIgnoreCase(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        
        int minLength = Math.min(str1.length(), str2.length());
        
        for (int i = 0; i < minLength; i++) {
            char char1 = str1.charAt(i);
            char char2 = str2.charAt(i);
            
            if (char1 < char2) {
                return -1;
            } else if (char1 > char2) {
                return 1;
            }
        }
        
        return Integer.compare(str1.length(), str2.length());
    }
}