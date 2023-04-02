package DictioMoteur;

import java.util.ArrayList;
import java.util.List;

public class LexiTree{
    private LexiNode root;

    public LexiTree() {
        this.root = new LexiNode('\0', "");
    }
    public void generateTreeFromString(String content) {
        String[] lines = content.split("\n");

        for (String line : lines) {
            String[] wordAndDefinition = line.split("&");

            if (wordAndDefinition.length == 2) {
                String word = wordAndDefinition[0].trim();
                String definition = wordAndDefinition[1].trim();

                root.addWord(word, definition);
            }
        }
    }
    public LexiNode getRoot() {
        return root;
    }

    public void addWord(String word, String definition) {
        root.addWord(word, definition);
    }

    public List<String> getWordDefinitions(String word) {
        return root.getWordDefinitions(word);
    }

    public boolean updateWordDefinitionInFile(String word, String newDefinition) {
        return root.updateWordDefinition(word, newDefinition);
    }

    public boolean wordExists(String word) {
        return wordExistsRecursive(root, word);
    }

    private boolean wordExistsRecursive(LexiNode node, String word) {
        if (word.isEmpty()) {
            return !root.getDefinitions().isEmpty();
        }

        char currentChar = word.charAt(0);
        String remainingWord = word.substring(1);

        if (node.getChildren().containsKey(currentChar)) {
            return wordExistsRecursive(node.getChildren().get(currentChar), remainingWord);
        } else {
            return false;
        }
    }

    public String displayAll() {
        return root.displayAll();
    }

    public String displayOnlyWords() {
        return root.displayOnlyWords();
    }

    public ArrayList<String>getWordsWith(String input) {
        return root.getWordsWith(input);
    }


}
