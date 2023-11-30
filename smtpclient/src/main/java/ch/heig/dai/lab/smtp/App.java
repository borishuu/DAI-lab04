package ch.heig.dai.lab.smtp;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final String ip = "localhost";
        final int port = 1025;

        // Temp, les récupérer despuis args par la suit
        String victimsList = args[1];
        String messageListFile = args[2];
        int groups = Integer.parseInt(args[3]);

        // Test de VictimsFinder
        VictimsFinder victimsFinder = new VictimsFinder();
        MessageSelector selector = new MessageSelector(messageListFile);
        EmailSender smtpClient = new EmailSender(ip, port);

        //File file = new File("C:\\Users\\yanis\\OneDrive\\HEIG-VD\\2eme-annee\\1er-semestre\\DAI\\LABOS\\DAI-lab04\\smtpclient\\src\\test\\testAddress");
        //File file = new File("smtpclient/testAddress");
        victimsFinder.getVictimsEmails(victimsList);
        selector.generateFileMessages();

        ArrayList<ArrayList<String>> emailsGroups = victimsFinder.getEmailsGroups(groups);

        smtpClient.openConnection();
        for (ArrayList<String> group : emailsGroups) {
            smtpClient.sendEmailToGroup(group, selector.getRandoMessage());
        }
        smtpClient.closeConnection();
    }
}
