import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * The Email class represents an email message with standard email fields.
 * This class stores information about an email including recipient, sender fields,
 * subject, body content, and creation timestamp.
 * 
 * This class implements Serializable to allow Email objects to be saved to files.
 * 
 * @author John Hartmann
 * @recitation R30
 * @version 1.0
 */

public class Email implements Serializable {
    

    private String to,cc,bcc,subject,body;
    private GregorianCalendar timestamp;
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor for the Email class.
     * Initializes all instance variables to their default values.
     */
    public Email() {
        this.to = null;
        this.cc = null;
        this.bcc = null;
        this.subject = null;
        this.body = null;
        this.timestamp = null;
    }
    
    /**
     * Parameterized constructor for the Email class.
     * Initializes all instance variables with the provided values.
     * 
     * @param to The recipient's email address
     * @param cc The carbon copy recipients
     * @param bcc The blind carbon copy recipients
     * @param subject The subject line of the email
     * @param body The main content of the email
     * @param timestamp The time when this email was created
     */
    public Email(String to, String cc, String bcc, String subject, String body, GregorianCalendar timestamp) {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
    }

    /**
     * Accessor method for the to field.
     * 
     * @return The String stored in the "to" field
     */
    public String getTo() {
        return to;
    }
    
    /**
     * Accessor method for the cc field.
     * 
     * @return The String stored in the "cc" field
     */
    public String getCC() {
        return cc;
    }
    
    /**
     * Accessor method for the bcc field.
     * 
     * @return The String stored in the "bcc" field
     */
    public String getBCC() {
        return bcc;
    }
    
    /**
     * Accessor method for the subject field.
     * 
     * @return The String stored in the "subject" field
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * Accessor method for the body field.
     * 
     * @return The String stored in the "body" field containing all email text
     */
    public String getBody() {
        return body;
    }
    
    /**
     * Accessor method for the timestamp field.
     * 
     * @return The GregorianCalendar representing when this email was created
     */
    public GregorianCalendar getTimestamp() {
        return timestamp;
    }
    
    // Mutator methods (setters)
    
    /**
     * Mutator method for the to field.
     * 
     * @param to The String to store in the "to" field
     */
    public void setTo(String to) {
        this.to = to;
    }
    
    /**
     * Mutator method for the cc field.
     * 
     * @param cc The String to store in the "cc" field
     */
    public void setCC(String cc) {
        this.cc = cc;
    }
    
    /**
     * Mutator method for the bcc field.
     * 
     * @param bcc The String to store in the "bcc" field
     */
    public void setBCC(String bcc) {
        this.bcc = bcc;
    }
    
    /**
     * Mutator method for the subject field.
     * 
     * @param subject The String to store in the "subject" field
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    /**
     * Mutator method for the body field.
     * 
     * @param body The String to store in the "body" field containing email text
     */
    public void setBody(String body) {
        this.body = body;
    }
    
    /**
     * Mutator method for the timestamp field.
     * 
     * @param timestamp The GregorianCalendar representing when this email was created
     */
    public void setTimestamp(GregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }
}