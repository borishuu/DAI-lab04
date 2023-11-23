package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

public class EmailSender {
    private final String ip;
    private final int port;
    //private Socket socket;

    public EmailSender(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /*public void openConnection() {
        try {
            socket = new Socket(ip, port);            
        } catch(IOException e) {
            System.out.println("Problem openning connection : " + e);
        }
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch(IOException e) {
            System.out.println("Problem closing connection : " + e);
        }
    }*/

    public void sendEmailToGroup(ArrayList<String> group, Message message) {
        // first element of group is sender

        try(Socket socket = new Socket(ip, port);
            var in = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            var out = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                System.out.println(in.readLine());

                out.write("EHLO heig.ch\r\n");
                out.flush();
                
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.charAt(3) == ' ')
                        break;
                }

                out.write("MAIL FROM:<" + group.get(0) + ">\r\n");
                out.flush();

                if (in.readLine() != "250 OK")
                    throw new IOException("not ok");

                for (int i = 1; i < group.size(); ++i) {
                    out.write("RCPT TO:<" + group.get(i) + ">\r\n");
                    out.flush();

                    if (in.readLine() != "250 OK")
                        throw new IOException("not ok");
                }

                out.write("DATA\r\n");
                out.flush();

                if (in.readLine().substring(0, 3) != "354")
                    throw new IOException("not ok");
                
                out.write("Date: " + LocalDateTime.now() + "\r\n");
                out.write("From: " + group.get(0) + "\r\n");
                out.write("Subject: " + message.getSubject() + "\r\n");
                out.write("To: " + group.get(1) + "\r\n");
                out.write(message.getBody());            

            } catch(IOException e) {
                System.out.println("Problem sending email : " + e);
            }
    }

    //public void sendEmailsToGroups(ArrayList<ArrayList<String>> groups, )
}
