package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MessageSelector {

    private final Charset ENCODING = StandardCharsets.UTF_8;
    private final String MESSAGE_START = "MSG_START";
    private final String MESSAGE_END = "MSG_END";
    private final String SUBJECT = "subject";
    private final String BODY = "body";

    private String filePath;
    private File messagesFile;
    private ArrayList<Message> messages = new ArrayList<Message>();

    public MessageSelector(String filePath) {
        this.filePath = filePath;
        this.messagesFile = new File(filePath);
    }

    public void generateFileMessages() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(messagesFile), ENCODING))) {
            String currentLine;
            boolean readingEmail = false;
            boolean readingBody = false;
            String currentSubject = "";
            StringBuilder currentBody = new StringBuilder();

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().equals(MESSAGE_START)) {                        
                    readingEmail = true;
                    continue;
                } else if (currentLine.trim().equals(MESSAGE_END)) {
                    readingEmail = false;
                    readingBody = false;
                    messages.add(new Message(currentSubject, currentBody.toString()));
                    currentSubject = new String();
                    currentBody = new StringBuilder();
                    continue;
                }

                if (readingEmail) {
                    String[] splitLine = currentLine.split(":");
                    if (splitLine[0].trim().equals(SUBJECT)) {
                        currentSubject = splitLine[1].trim();
                    } else if (splitLine[0].trim().equals(BODY)) {
                        readingBody = true;
                        currentBody.append(splitLine[1].trim());
                    } else if (readingBody) {
                        currentBody.append("\n");
                        currentBody.append(currentLine);
                    } else {
                        throw new RuntimeException("Invalid file format");
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Problem reading file");
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
