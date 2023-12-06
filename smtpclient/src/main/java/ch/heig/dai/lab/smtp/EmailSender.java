package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * SMTP Client sending an email to a group of email addresses
 * 
 * @author Ouadahi Yanis
 * @author Hutzli Boris
 */
public class EmailSender {
    /** SMTP server IP */
    private final String ip;

    /** SMTP server port */
    private final int port;

    /**
     * Constructor
     * 
     * @param ip   SMTP server IP address to connect to
     * @param port SMTP server port to connect to
     */
    public EmailSender(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Sends an email to email addresses in a group
     * 
     * @param group   List containing email addresses (the first one is the sender)
     * @param message Email to be sent
     */
    public void sendEmailToGroup(ArrayList<String> group, Message message) {
        try (Socket socket = new Socket(ip, port);
                var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            // Reading connection message (220)
            in.readLine();

            // Sending ehlo
            out.write("EHLO heig.ch\r\n");
            out.flush();

            String line;

            // Reading responses until the character at index 3 is a whitespace (end of message)
            while ((line = in.readLine()) != null) {
                if (line.charAt(3) == ' ')
                    break;
            }

            // Setting sender email
            out.write("MAIL FROM:<" + group.get(0) + ">\r\n");
            out.flush();

            // Reading server's 250 message if everything goes well
            if (!in.readLine().split(" ")[0].equals("250"))
                throw new IOException("Problem reading socket input stream");

            // Setting receiver emails
            for (int i = 1; i < group.size(); ++i) {
                out.write("RCPT TO:<" + group.get(i) + ">\r\n");
                out.flush();

                // Reading server's 250 message if everything goes well
                if (!in.readLine().split(" ")[0].equals("250"))
                    throw new IOException("Problem reading socket input stream");
            }

            // Sending data
            out.write("DATA\r\n");
            out.flush();

            // Reading server's 354 message if everything goes well
            if (!in.readLine().split(" ")[0].equals("354"))
                throw new IOException("Problem reading socket input stream");

            // Date of sending
            out.write("Date: " + LocalDateTime.now() + "\r\n");

            // Sender
            out.write("From: " + group.get(0) + "\r\n");

            // Email subject
            out.write("Subject: " + message.getSubject() + "\r\n");

            // Receivers
            out.write("To: " + group.get(1));
            for (int i = 2; i < group.size(); ++i)
                out.write(", " + group.get(i));

            // Email body
            out.write("\r\n\r\n" + message.getBody() + "\r\n");

            out.write(".\r\n");
            out.flush();

            // Reading server's 250 message if everything goes well
            if (!in.readLine().split(" ")[0].equals("250"))
                throw new IOException("Problem reading socket input stream");

            // Connection termination
            out.write("QUIT\r\n");
            out.flush();

            // Reading server's 221 message if everything goes well
            if (!in.readLine().split(" ")[0].equals("221"))
                throw new IOException("Problem reading socket input stream");

        } catch (IOException e) {
            System.out.println("Problem sending email : " + e);
        }
    }
}