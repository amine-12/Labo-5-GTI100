package DictioMoteur;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictioToolBox {
    private static DictioToolBox dictionary = null;
    public static String content;
    public static String filePath;
    public static synchronized DictioToolBox Dictionairy()
    {
        if (dictionary == null)
            dictionary = new DictioToolBox();

        return dictionary;
    }

    public String readFileContents(String filePath) throws IOException {
        this.filePath = filePath;
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

    public static void updateOrAddWord(String filePath, LexiNode lexiTreeRoot, String word, String definition) throws IOException {
        boolean updated = lexiTreeRoot.updateWordDefinition(word, definition);

        if (updated) {
            updateWordDefinitionInFile(filePath, word, definition);
        } else {
            lexiTreeRoot.addWord(word, definition);
            appendWordToFile(filePath, word, definition);
        }
    }

    public static void updateWordDefinitionInFile(String filePath, String word, String definition) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File("tempfile.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordAndDefinition = line.split(" & ");

                if (wordAndDefinition[0].equals(word)) {
                    writer.write(word + " & " + definition + System.lineSeparator());
                } else {
                    writer.write(line + System.lineSeparator());
                }
            }
        }

        // Replace the original file with the updated file
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    private static void appendWordToFile(String filePath, String word, String definition) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(word + " & " + definition + System.lineSeparator());
        }
    }

}
