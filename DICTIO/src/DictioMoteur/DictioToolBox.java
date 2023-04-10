package DictioMoteur;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * La classe DictioToolBox est un singleton qui fournit des méthodes
 * utilitaires pour interagir avec des fichiers de dictionnaire contenant
 * des mots et leurs définitions. Elle permet de lire le contenu d'un fichier,
 * d'ajouter un mot et sa définition au fichier et de mettre à jour la
 * définition d'un mot existant dans le fichier.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class DictioToolBox {
    private static DictioToolBox dictionary = null;
    public static String content;
    public static String filePath;

    /**
     * Retourne l'instance unique de la classe DictioToolBox (singleton).
     *
     * @return DictioToolBox - l'instance unique de DictioToolBox.
     */
    public static synchronized DictioToolBox Dictionary()
    {
        if (dictionary == null)
            dictionary = new DictioToolBox();

        return dictionary;
    }

    /**
     * Lit le contenu d'un fichier texte et retourne le contenu sous forme de chaîne de caractères.
     *
     * @param filePath - chemin du fichier à lire.
     * @return String - le contenu du fichier sous forme de chaîne de caractères.
     * @throws IOException si une erreur de lecture du fichier survient.
     */
    public String readFileContents(String filePath) throws IOException {
        DictioToolBox.filePath = filePath;
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder str = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            str.append(line);
            str.append("\n");
        }

        bufferedReader.close();
        fileReader.close();
        content = str.toString();
        return str.toString();
    }

    /**
     * Ajoute un mot et sa définition à la fin d'un fichier texte.
     *
     * @param filePath - chemin du fichier auquel ajouter le mot et sa définition.
     * @param word - mot à ajouter.
     * @param definition - définition du mot à ajouter.
     */
    public void addWordToFile(String filePath, String word, String definition) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

            if (!fileContent.endsWith("\n")) {
                word = "\n" + word;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(word + " & " + definition);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour la définition d'un mot existant dans un fichier texte.
     *
     * @param filePath - chemin du fichier contenant le mot dont la définition doit être mise à jour.
     * @param word - mot dont la définition doit être mise à jour.
     * @param newDefinition - nouvelle définition du mot.
     */
    public void updateWordDefinitionInFile(String filePath, String word, String newDefinition) {
        Path path = Paths.get(filePath);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            boolean firstLine = true;
            for (String line : lines) {
                if (!firstLine) {
                    bw.newLine();
                } else {
                    firstLine = false;
                }

                String[] section = line.split(" & ", 2);
                if (section.length >= 2 && section[0].equalsIgnoreCase(word)) {
                    bw.write(word + " & " + newDefinition);
                } else {
                    bw.write(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

