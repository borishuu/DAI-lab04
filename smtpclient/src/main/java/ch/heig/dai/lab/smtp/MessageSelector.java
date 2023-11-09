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
    private final String MESSAGE_end = "MSG_END";

    private String filePath;
    private File messagesFile;
    private ArrayList<Message> messages = new ArrayList<Message>();

    public MessageSelector(String filePath) {
        this.filePath = filePath;
        this.messagesFile = new File(filePath);
        getFileMessages();
    }

    private void getFileMessages() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(messagesFile), ENCODING))) {
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim() == MESSAGE_START) {
                    // début d'email
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Problem reading file");
        }
    }

}
