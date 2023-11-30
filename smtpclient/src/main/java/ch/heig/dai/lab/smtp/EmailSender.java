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
    private Socket socket;

    public EmailSender(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void openConnection() {
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
    }

    public void sendEmailToGroup(ArrayList<String> group, Message message) {
        // first element of group is sender

        try(var in = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            var out = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                System.out.println(in.readLine());

                out.write("EHLO heig.ch\r\n");
                out.flush();
                
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    if (line.charAt(3) == ' ')
                        break;
                }

                out.write("MAIL FROM:<" + group.get(0) + ">\r\n");
                out.flush();

                if (!(line = in.readLine()).split(" ")[0].equals("250"))
                    throw new IOException("not ok");
                System.out.println(line);

                for (int i = 1; i < group.size(); ++i) {
                    out.write("RCPT TO:<" + group.get(i) + ">\r\n");
                    out.flush();

                    if (!(line = in.readLine()).split(" ")[0].equals("250"))
                        throw new IOException("not ok");
                    System.out.println(line);
                }

                out.write("DATA\r\n");
                out.flush();

                if (!(line = in.readLine()).split(" ")[0].equals("354"))
                    throw new IOException("not ok");
                System.out.println(line);
                
                StringBuilder sb = new StringBuilder();

                out.write("Date: " + LocalDateTime.now() + "\r\n");
                out.write("From: " + group.get(0) + "\r\n");
                out.write("Subject: " + message.getSubject() + "\r\n");
                out.write("To: " + group.get(1));
                for (int i = 2; i < group.size(); ++i) {
                    
                    out.write(", " + group.get(i));
                }
                
                out.write("\r\n\r\n" + message.getBody() + "\r\n");


                out.write(".\r\n");
                out.flush();

                if (!(line = in.readLine()).split(" ")[0].equals("250"))
                        throw new IOException("not ok");
                System.out.println(line);

                out.write("QUIT\r\n");
                out.flush();

                if (!(line = in.readLine()).split(" ")[0].equals("221"))
                    throw new IOException("not ok");
                System.out.println(line);
            } catch(IOException e) {
                System.out.println("Problem sending email : " + e);
            }
    }

    //public void sendEmailsToGroups(ArrayList<ArrayList<String>> groups, )
}
