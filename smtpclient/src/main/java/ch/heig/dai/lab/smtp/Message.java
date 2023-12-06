package ch.heig.dai.lab.smtp;

/**
 * Represents an email
 * 
 * @author Ouadahi Yanis
 * @author Hutzli Boris
 */
public class Message {

    /** Email subject */
    private final String subject;

    /** Email body */
    private final String body;

    /**
     * Constructor
     * 
     * @param subject subject
     * @param body body
     */
    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;        
    }

    /**
     * Gets subject
     * 
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Gets body
     * 
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * Convert email to String
     * 
     * @return email as String
     */
    public String toString() {
        return "Subject : " + subject + "\nBody : " + body;
    }
}
