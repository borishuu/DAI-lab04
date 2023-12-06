package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/**
 * Messages file reader and selector
 * 
 * @author Ouadahi Yanis
 * @author Hutzli Boris
 */
public class MessageSelector {

    /** Random used to select a random message */
    private final Random random = new Random();

    /** List containing all the messages */
    private final ArrayList<Message> messages = new ArrayList<>();

    /**
     * Reads and stores all messages in a given file
     * 
     * @param fileName Name of file containing the messages
     */
    public void generateFileMessages(String fileName) {
        File messagesFile = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(messagesFile), StandardCharsets.UTF_8))) {

            // Strings representing the START and END of a message, the SUBJECT and BODY definitions in the file
            String MESSAGE_START = "MSG_START", MESSAGE_END = "MSG_END", SUBJECT = "subject", BODY = "body";

            // Line currently read
            String currentLine;

            // Determines whether a message is currently being read
            boolean readingEmail = false;

            // Determines whether a message body is being read
            boolean readingBody = false;

            // Subject read of the current message
            String currentSubject = "";

            StringBuilder currentBody = new StringBuilder();

            // Read the whole file
            while ((currentLine = reader.readLine()) != null) {
                // Start of message
                if (currentLine.trim().equals(MESSAGE_START)) {
                    readingEmail = true;
                    continue;
                
                // End of message
                } else if (currentLine.trim().equals(MESSAGE_END)) {
                    readingEmail = false;
                    readingBody = false;
                    messages.add(new Message(currentSubject, currentBody.toString()));
                    currentSubject = new String();
                    currentBody = new StringBuilder();
                    continue;
                }

                // In message
                if (readingEmail) {
                    String[] splitLine = currentLine.split(":");

                    // Read subject
                    if (splitLine[0].trim().equals(SUBJECT)) {
                        currentSubject = splitLine[1].trim();
                    
                    // Start reading body
                    } else if (splitLine[0].trim().equals(BODY)) {
                        readingBody = true;
                        currentBody.append(splitLine[1].trim());

                    // Still reading body in a different line
                    } else if (readingBody) {
                        currentBody.append("\n");
                        currentBody.append(currentLine);
                    
                    // Problem if none of these conditions are met
                    } else {
                        throw new RuntimeException("Invalid message format");
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Problem reading emails file : " + e);
        }
    }

    /**
     * Get all messages
     * 
     * @return List of all messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Get random message
     * 
     * @return random message from list
     */
    public Message getRandoMessage() {
        return messages.get(random.nextInt(messages.size()));
    }
}
