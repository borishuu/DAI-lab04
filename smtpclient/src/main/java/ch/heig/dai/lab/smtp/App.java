package ch.heig.dai.lab.smtp;

import java.util.ArrayList;

/**
 * Contains program logic
 * 
 * @author Ouadahi Yanis
 * @author Hutzli Boris
 */
public class App 
{
    public static void main( String[] args )
    {
        // IP and port to connect to
        final String ip = "localhost";
        final int port = 1025;

        // Retrieving arguments
        String victimsList = args[0];
        String messageListFile = args[1];
        int groups = Integer.parseInt(args[2]);

        // Class initialisation
        VictimsFinder victimsFinder = new VictimsFinder();
        MessageSelector selector = new MessageSelector();
        EmailSender smtpClient = new EmailSender(ip, port);

        // Data generation
        try {
            victimsFinder.generateVictimEmails(victimsList);
            selector.generateFileMessages(messageListFile);
        } catch (RuntimeException e) {
            System.out.println("Data generation error : " + e);
            return;
        }

        // Retrieve email groups
        ArrayList<ArrayList<String>> emailsGroups = victimsFinder.getEmailsGroups(groups);

        // Send a random email to each group
        for (int i = 0; i < emailsGroups.size(); ++i) {
            ArrayList<String> group = emailsGroups.get(i);
            Message message = selector.getRandoMessage();

            smtpClient.sendEmailToGroup(group, message);  

            System.out.println("Group " + (i + 1) + " pranked :");
            System.out.println("-----------------");

            for (String email : group) {
                System.out.println(email);
            }
            
            System.out.println("\n" + message + "\n");
        }
    }
}
