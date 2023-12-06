package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Victims email reader and selector
 * 
 * @author Ouadahi Yanis
 * @author Hutzli Boris
 */
public class VictimsFinder {

    /** List containing all emails to choose from */
    private final ArrayList<String> victimsEmails = new ArrayList<>();

    /**
     * Checks whether a given String is an email address
     * 
     * @param address String to check
     * @return true if it's a valid address and false if not
     */
    private boolean checkAddress(String address) {
        // Email formats accepted
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        return matcher.matches();
    }

    /**
     * Reads and stores all emails in a given file
     * 
     * @param fileName Name of file containing the email addresses
     */
    public void generateVictimEmails(String fileName) {
        File file = new File(fileName);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));) {

            String line;

            // Read all lines
            while ((line = reader.readLine()) != null && !line.isEmpty()) {

                // Add address to list if it's valid
                if (checkAddress(line.trim().toLowerCase()))
                    victimsEmails.add(line);              
                else
                   throw new RuntimeException("Invalid email format");
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Problem generating victimc emails");
        }
    }

    /**
     * Get a list of n amounts of groups containing [randomMin, randomMax] addresses
     * 
     * @param n Amount of groups to generate
     * @return List of all the groups
     */
    public ArrayList<ArrayList<String>> getEmailsGroups(int n) {

        ArrayList<ArrayList<String>> emailsGroups = new ArrayList<>();
        Random random = new Random();

        // Minimum and maximum amounts of emails in a group
        final int randomMin = 2, randomMax = 5;

        for (int i = 0; i < n; ++i) {

            // Generate a number in [0, 4( and add 2, so in [2, 6( = [2, 5]
            int randomNumberOfAdresses = random.nextInt(randomMax - 1) + randomMin;
            ArrayList<String> group = new ArrayList<>();

            // Add addresses
            for (int j = 0; j < randomNumberOfAdresses; ++j) {
                int randomIndex = random.nextInt(victimsEmails.size());
                group.add(victimsEmails.get(randomIndex));
            }

            emailsGroups.add(group);
        }

        return emailsGroups;
    }
}
