package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// The victims list: a file with a list of e-mail addresses

public class VictimsFinder {
    private final ArrayList<String> victimsEmails = new ArrayList<>();

    public ArrayList<String> getVictimsEmails(File file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (checkAddress(line))
                    victimsEmails.add(line);
                else;
                   // throw Exception? print le fait que l'adresse soit invalide et continuer ?
            }

            reader.close();
            return victimsEmails;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean checkAddress(String address) {
        // Emails are case-insensitive
        address = address.toLowerCase();

        // Address e-mail format accepted
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        return matcher.matches();
    }

    public ArrayList<ArrayList<String>> getEmailsGroups(int n) {
        // Boucle for jusqu'à n, chaque tour ajouter 2 à 5 adresses au bol de notre répertoire d'adresses
        ArrayList<ArrayList<String>> emailsGroups = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; ++i) {
            // Generate a number in [0, 4) and add 2, so in [2, 6) = [2, 5]
            int randomNumberOfAdresses = random.nextInt(4) + 2;
            ArrayList<String> group = new ArrayList<>();

            for (int j = 0; j < randomNumberOfAdresses; ++j) {
                int randomIndex = random.nextInt(victimsEmails.size());
                group.add(victimsEmails.get(randomIndex));
            }

            emailsGroups.add(group);
        }

        return emailsGroups;
    }
}
