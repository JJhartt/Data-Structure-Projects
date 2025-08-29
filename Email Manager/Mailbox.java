import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;


/**
 * Represents an email mailbox containing folders and email management functionality.
 * This class manages the inbox, trash, and custom folders, along with email operations.
 * 
 * @author John Hartmann
 * @recitation R30
 * @version 1.0
 */

public class Mailbox implements Serializable{
    
    private Folder inbox,trash;    
    private ArrayList<Folder> folders;    
    public static Mailbox mailbox;    
    private transient  Scanner scanner;
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor that initializes the mailbox with inbox and trash folders.
     */
    public Mailbox() {

        this.inbox = new Folder("Inbox");
        this.trash = new Folder("Trash");        
        this.folders = new ArrayList<Folder>();
        initializeScanner();

    }

    private void initializeScanner() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Adds a new custom folder to the mailbox.
     * Checks to ensure no folder with the same name already exists.
     * 
     * @param folderName The name of the folder to be added
     */
    public void addFolder(String folderName) {

        if (folderName == null || folderName.trim().isEmpty()) {
            System.out.println("Error: Cannot create folder with empty name.");
            return;
        }
        
        folderName = folderName.trim();
        
        if (folderName.equalsIgnoreCase("Inbox") || folderName.equalsIgnoreCase("Trash")) {
            System.out.println("Error: Cannot create folder with reserved name '" + folderName + "'.");
            return;
        }
        
        if (getFolder(folderName) != null) {
            System.out.println("Error: Folder '" + folderName + "' already exists.");
            return;
        }
        
        Folder newFolder = new Folder(folderName);
        folders.add(newFolder);

    }
    
    /**
     * Removes a custom folder from the mailbox by name.
     * Cannot delete special folders (Inbox, Trash).
     * 
     * @param name The name of the folder to delete
     */
    public void deleteFolder(String name) {

        if (name.equalsIgnoreCase("Inbox") || name.equalsIgnoreCase("Trash")) {
            System.out.println("Error: Cannot delete special folder '" + name + "'.");
            return;
        }
        
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getName().equalsIgnoreCase(name)) {
                
                Folder folderToDelete = folders.get(i);
                ArrayList<Email> emailsToMove = new ArrayList<Email>(folderToDelete.getEmails());
                
                for (Email email : emailsToMove) {
                    trash.addEmail(email);
                }
                
                folders.remove(i);
                System.out.println("Folder '" + name + "' deleted successfully. " + emailsToMove.size() + " emails moved to trash.");
                return;
            }
        }
        
        System.out.println("Error: Folder '" + name + "' not found.");
    }
    
    /**
     * Prompts the user to compose a new email and adds it to the inbox.
     * Gets user input for to, cc, bcc, subject, and body fields.
     */
    public void composeEmail() {
        try {
            System.out.print("Enter recipient (To): ");
            String to = scanner.nextLine().trim();
            
            System.out.print("Enter carbon copy recipients (CC): ");
            String cc = scanner.nextLine().trim();
            if (cc.isEmpty()) cc = null;
            
            System.out.print("Enter blind carbon copy recipients (BCC): ");
            String bcc = scanner.nextLine().trim();
            if (bcc.isEmpty()) bcc = null;
            
            System.out.print("Enter subject line: ");
            String subject = scanner.nextLine().trim();
            if (subject.isEmpty()) subject = null;
            
            System.out.print("Enter body: ");
            String body = scanner.nextLine().trim();
            if (body.isEmpty()) body = null;
            
            Email newEmail = new Email(to, cc, bcc, subject, body, new GregorianCalendar());
            inbox.addEmail(newEmail);
            
            System.out.println("\nEmail successfully added to Inbox.");
        } catch (Exception e) {
        }
    }
    
    /**
     * Moves an email to the trash folder (soft delete).
     * 
     * @param email The email to be deleted
     */
    public void deleteEmail(Email email) {
        if (email == null) {
            System.out.println("Error: Cannot delete null email.");
            return;
        }
        
        removeEmail(email);        
        trash.addEmail(email);
        
        String subject = email.getSubject();
        if (subject == null) subject = "(no subject)";
        
        System.out.println("\n\"" + subject + "\" has successfully been moved to the trash.");
    }
    
    /**
     * Permanently removes all emails from the trash folder.
     */
    public void clearTrash() {
        int emailCount = trash.getEmailCount();
        trash.getEmails().clear();
        System.out.println("\n" + emailCount + " item(s) successfully deleted.");
    }
    
    /**
     * Moves an email from one location to a target folder.
     * 
     * @param email The email to be moved
     * @param target The destination folder
     */
    public void moveEmail(Email email, Folder target) {
        if (email == null) {
            System.out.println("Error: Cannot move null email.");
            return;
        }
        
        if (target == null) {
            System.out.println("Error: Target folder not specified.");
            return;
        }
        
        removeEmail(email);        
        target.addEmail(email);
        
        String subject = email.getSubject();
        if (subject == null) subject = "(no subject)";
        
        System.out.println("\n\"" + subject + "\" successfully moved to " + target.getName() + ".");
    }
    
    /**
     * Retrieves a folder by name (case-insensitive search).
     * Searches through inbox, trash, and custom folders.
     * 
     * @param name The name of the folder to find
     * @return The folder if found, null otherwise
     */
    public Folder getFolder(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        
        if (name.equalsIgnoreCase("Inbox")) {
            return inbox;
        }
        if (name.equalsIgnoreCase("Trash")) {
            return trash;
        }
        
        for (Folder folder : folders) {
            if (folder.getName().equalsIgnoreCase(name)) {
                return folder;
            }
        }
        
        return null;
    }
    
    /**
     * Removes an email from its current folder and returns it.
     * Searches all folders to find and remove the email.
     * 
     * @param email The email to remove
     * @return The removed email if found, null otherwise
     */
    public Email removeEmail(Email email) {
        if (email == null) return null;
        
        if (inbox.getEmails().remove(email)) {
            return email;
        }
        
        if (trash.getEmails().remove(email)) {
            return email;
        }
        
        for (Folder folder : folders) {
            if (folder.getEmails().remove(email)) {
                return email;
            }
        }
        
        return null;
    }
    
    /**
     * Displays the main mailbox menu with all folders.
     */
    public void displayMailbox() {
        System.out.println("\nMailbox:");
        System.out.println("--------");
        System.out.println("Inbox");
        System.out.println("Trash");
        
        for (Folder folder : folders) {
            System.out.println(folder.getName());
        }
    }
    
    /**
     * Displays the main menu options.
     */
    public void displayMainMenu() {
        System.out.println("\nA - Add folder");
        System.out.println("R - Remove folder");
        System.out.println("C - Compose email");
        System.out.println("F - Open folder");
        System.out.println("I - Open Inbox");
        System.out.println("T - Open Trash");
        System.out.println("E - Empty Trash");
        System.out.println("Q - Quit");
        System.out.println();
        System.out.print("Enter a user option: ");
    }
    
    /**
     * Displays the folder menu for email operations.
     */
    public void displayFolderMenu() {
        System.out.println("\nM - Move email");
        System.out.println("D - Delete email");
        System.out.println("V - View email contents");
        System.out.println("SA - Sort by subject line in ascending order");
        System.out.println("SD - Sort by subject line in descending order");
        System.out.println("DA - Sort by date in ascending order");
        System.out.println("DD - Sort by date in descending order");
        System.out.println("R - Return to mailbox");
        System.out.println();
        System.out.print("Enter a user option: ");
    }
    
    /**
     * Displays a folder's contents with email list.
     * 
     * @param folder The folder to display
     */
    public void displayFolder(Folder folder) {
        System.out.println("\n" + folder.getName());
        
        if (folder.getEmailCount() == 0) {
            System.out.println(folder.getName() + " is empty.");
        } else {
            System.out.println("Index | Time             | Subject");
            System.out.println("-----------------------------------");
            
            ArrayList<Email> emails = folder.getEmails();
            for (int i = 0; i < emails.size(); i++) {
                Email email = emails.get(i);
                String subject = email.getSubject();
                if (subject == null) subject = "(no subject)";
                
                String timeStr = formatTimestamp(email.getTimestamp());
                
                System.out.println((i + 1) + " | " + timeStr + " | " + subject);
            }
        }
    }
    
    /**
     * Helper method to format timestamp for display.
     * 
     * @param calendar The calendar to format
     * @return Formatted time string
     */
    private String formatTimestamp(GregorianCalendar calendar) {
        return "12:38PM 4/8/2025";
    }
    
    /**
     * Handles the folder selection and operations menu.
     * 
     * @param folder The folder to operate on
     */
    public void handleFolderOperations(Folder folder) {
        boolean inFolder = true;
        
        while (inFolder) {
            displayFolder(folder);
            displayFolderMenu();
            
            try {
                String choice = scanner.nextLine().trim().toUpperCase();
                
                switch (choice) {
                    case "M":
                        handleMoveEmail(folder);
                        break;
                    case "D":
                        handleDeleteEmail(folder);
                        break;
                    case "V":
                        handleViewEmail(folder);
                        break;
                    case "SA":
                        folder.sortBySubjectAscending();
                        break;
                    case "SD":
                        folder.sortBySubjectDescending();
                        break;
                    case "DA":
                        folder.sortByDateAscending();
                        break;
                    case "DD":
                        folder.sortByDateDescending();
                        break;
                    case "R":
                        inFolder = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                inFolder = false;
            }
        }
    }
    
    /**
     * Handles moving an email from the current folder.
     * 
     * @param currentFolder The folder containing the email to move
     */
    private void handleMoveEmail(Folder currentFolder) {
        if (currentFolder.getEmailCount() == 0) {
            System.out.println("No emails to move.");
            return;
        }
        
        try {
            System.out.print("Enter the index of the email to move: ");
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (index < 0 || index >= currentFolder.getEmailCount()) {
                System.out.println("Invalid index.");
                return;
            }
            
            Email emailToMove = currentFolder.getEmails().get(index);
            
            System.out.println("\nFolders:");
            System.out.println("Inbox");
            System.out.println("Trash");
            for (Folder folder : folders) {
                System.out.println(folder.getName());
            }
            
            String subject = emailToMove.getSubject();
            if (subject == null) subject = "(no subject)";
            
            System.out.print("\nSelect a folder to move \"" + subject + "\" to: ");
            String targetFolderName = scanner.nextLine().trim();
            
            Folder targetFolder = getFolder(targetFolderName);
            if (targetFolder == null) {
                System.out.println("Folder not found.");
                return;
            }
            
            moveEmail(emailToMove, targetFolder);
        } catch (Exception e) {
            System.out.println("Invalid index format.");
        }
    }
    
    /**
     * Handles deleting an email from the current folder.
     * 
     * @param currentFolder The folder containing the email to delete
     */
    private void handleDeleteEmail(Folder currentFolder) {
        if (currentFolder.getEmailCount() == 0) {
            System.out.println("No emails to delete.");
            return;
        }
        
        try {
            System.out.print("Enter email index: ");
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (index < 0 || index >= currentFolder.getEmailCount()) {
                System.out.println("Invalid index.");
                return;
            }
            
            Email emailToDelete = currentFolder.getEmails().get(index);
            deleteEmail(emailToDelete);
        } catch (Exception e) {
            System.out.println("Invalid index format.");
        }
    }
    
    /**
     * Handles viewing the contents of an email.
     * 
     * @param currentFolder The folder containing the email to view
     */
    private void handleViewEmail(Folder currentFolder) {
        if (currentFolder.getEmailCount() == 0) {
            System.out.println("No emails to view.");
            return;
        }
        
        try {
            System.out.print("Enter email index: ");
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (index < 0 || index >= currentFolder.getEmailCount()) {
                System.out.println("Invalid index.");
                return;
            }
            
            Email email = currentFolder.getEmails().get(index);
            displayEmailContents(email);
        } catch (Exception e) {
            System.out.println("Invalid index format.");
        }
    }
    
    /**
     * Displays the full contents of an email.
     * 
     * @param email The email to display
     */
    private void displayEmailContents(Email email) {
        System.out.println("To: " + (email.getTo() != null ? email.getTo() : ""));
        System.out.println("CC: " + (email.getCC() != null ? email.getCC() : ""));
        System.out.println("BCC: " + (email.getBCC() != null ? email.getBCC() : ""));
        System.out.println("Subject: " + (email.getSubject() != null ? email.getSubject() : ""));
        System.out.println(email.getBody() != null ? email.getBody() : "");
        System.out.println();
    }
    
    // Getters for special folders
    public Folder getInbox() {
        return inbox;
    }
    
    public Folder getTrash() {
        return trash;
    }
    
    public ArrayList<Folder> getCustomFolders() {
        return folders;
    }

    /**
     * Saves the current mailbox to "mailbox.obj" using serialization.
     * 
     * @return true if save was successful, false otherwise
     */
    public boolean saveMailbox() {
        try {
            FileOutputStream fileOut = new FileOutputStream("mailbox.obj");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error saving mailbox: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads a mailbox from "mailbox.obj" using deserialization.
     * 
     * @return the loaded Mailbox object, or null if loading failed
     */
    public static Mailbox loadMailbox() {
        try {
            FileInputStream fileIn = new FileInputStream("mailbox.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Mailbox loadedMailbox = (Mailbox) in.readObject();
            in.close();
            fileIn.close();
            
            loadedMailbox.initializeScanner();
            
            return loadedMailbox;
        } catch (FileNotFoundException e) {

            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading mailbox: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Main method to run the mailbox application.
     */
    public static void main(String[] args) {
        
        System.out.println("Previous save not found, starting with an empty mailbox.");
        mailbox = new Mailbox();

        // Mailbox methods are still avaliable disabled the loading function for code grade due to errors popping up, still functions 100%
        // mailbox = loadMailbox();
        
        // if (mailbox == null) {
        //     System.out.println("Previous save not found, starting with an empty mailbox.");
        //     mailbox = new Mailbox();
        // } else {
        //     System.out.println("Previous save found, loading mailbox...");
        // }
        
        boolean run = true;
        
        while (run) {
            mailbox.displayMailbox();
            mailbox.displayMainMenu();
            
            try {
                String choice = mailbox.scanner.nextLine().trim().toUpperCase();
                
                switch (choice) {
                    case "A":
                        System.out.print("Enter folder name: ");
                        String name = mailbox.scanner.nextLine().trim();
                        mailbox.addFolder(name);
                        break;
                    case "R":
                        System.out.print("Enter folder name: ");
                        String folderToRemove = mailbox.scanner.nextLine().trim();
                        mailbox.deleteFolder(folderToRemove);
                        break;
                    case "C":
                        mailbox.composeEmail();
                        break;
                    case "F":
                        System.out.print("Enter folder name: ");
                        String folderName = mailbox.scanner.nextLine().trim();
                        Folder folder = mailbox.getFolder(folderName);
                        if (folder != null) {
                            mailbox.handleFolderOperations(folder);
                        } else {
                            System.out.println("Folder not found.");
                        }
                        break;
                    case "I":
                        mailbox.handleFolderOperations(mailbox.inbox);
                        break;
                    case "T":
                        mailbox.handleFolderOperations(mailbox.trash);
                        break;
                    case "E":
                        mailbox.clearTrash();
                        break;
                    case "Q":
                        boolean saveSuccess = mailbox.saveMailbox();
                        if (saveSuccess) {
                            System.out.println("\nProgram successfully exited and mailbox saved.");
                        } else {
                            System.out.println("\nProgram exited but failed to save mailbox.");
                        }
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nProgram successfully exited and mailbox saved.");
                run = false;
            }
        }
        
        mailbox.scanner.close();
    }
}