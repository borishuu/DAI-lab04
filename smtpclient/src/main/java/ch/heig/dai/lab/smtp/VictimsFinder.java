package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// The victims list: a file with a list of e-mail addresses

public class VictimsFinder {
    public String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                content.append(line);
            reader.close();

            return content.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getVictims(String content) {
        // Boucler sur content en parsant les addresses e-mail et vérifiant le format, sinon lever une exception (pas de @, pas format nom@entreprise.domaine)
        // Ou bien est-ce que l'on part du principe que le fichier est un fichier avec sur chaque ligne une adresse?

        return null;
    }
}

/*  adresse0@emiel.com
    adresse1@emiel.com
    adresse2@emiel.com
    adresse3@emiel.com
    adresse4@emiel.com

    ou

    adresse0@emiel.com, adresse1@emiel.com, adresse1@emiel.com adresse1@emiel.com
* */