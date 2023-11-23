package ch.heig.dai.lab.smtp;

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
