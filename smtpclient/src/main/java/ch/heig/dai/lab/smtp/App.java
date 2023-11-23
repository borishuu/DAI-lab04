package ch.heig.dai.lab.smtp;

import java.io.File;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Temp, les récupérer despuis args par la suite
        String victimsListFile = "";
        String messageListFile = "C:\\Users\\boris\\Documents\\Etudes\\S3\\DAI\\labos\\labo4\\DAI-lab04\\smtpclient\\TestMessageList.txt";
        int groups = 5;

        // Test de VictimsFinder
        VictimsFinder victimsFinder = new VictimsFinder();
        File file = new File("C:\\Users\\yanis\\OneDrive\\HEIG-VD\\2eme-annee\\1er-semestre\\DAI\\LABOS\\DAI-lab04\\smtpclient\\src\\test\\testAddress");
        victimsFinder.getVictimsEmails(file);

        ArrayList<ArrayList<String>> emailsGroups = victimsFinder.getEmailsGroups(groups);
        for (ArrayList<String> group:emailsGroups)
            System.out.println(group);

        MessageSelector selector = new MessageSelector(messageListFile);
        selector.generateFileMessages();
        for (Message m : selector.getMessages()) {
            System.out.println(m);
        }

        System.out.println(selector.getRandoMessage());
        System.out.println(selector.getRandoMessage());
        System.out.println(selector.getRandoMessage());
    }
}
