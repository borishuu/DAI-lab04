package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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

    private void sendEmailToGroup(ArrayList<String> group, Message message) {
        // first element of group is sender
        if (socket == null || socket.isClosed())
            return;

        try(var in = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            var out = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                for (int i = 0; i < 10; i++) {
                    out.write("Hello " + i);
                    System.out.println("Echo: " + in.readLine());
                }
            } catch(IOException e) {

            }
    }

    //public void sendEmailsToGroups(ArrayList<ArrayList<String>> groups, )
}
