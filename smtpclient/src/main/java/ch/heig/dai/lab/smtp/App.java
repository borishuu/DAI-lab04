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
        String messageListFile = "";
        int groups = 5;

        // Test de VictimsFinder
        VictimsFinder victimsFinder = new VictimsFinder();
        File file = new File("C:\\Users\\yanis\\OneDrive\\HEIG-VD\\2eme-annee\\1er-semestre\\DAI\\LABOS\\DAI-lab04\\smtpclient\\src\\test\\testAddress");
        victimsFinder.getVictimsEmails(file);

        ArrayList<ArrayList<String>> emailsGroups = victimsFinder.getEmailsGroups(groups);
        for (ArrayList<String> group:emailsGroups)
            System.out.println(group);
    }
}
