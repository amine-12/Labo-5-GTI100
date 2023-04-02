package DictioMoteur;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

