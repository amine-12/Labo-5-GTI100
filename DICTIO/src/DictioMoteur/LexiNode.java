package DictioMoteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LexiNode{
    private char letter;
    private String currentWord;
    private List<String> definitions;
    private HashMap<Character, LexiNode> children;

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public LexiNode(char letter, String currentWord) {
        this.letter = letter;
        this.currentWord = currentWord;
        this.definitions = new ArrayList<>();
        this.children = new HashMap<>();
    }

    public void addWord(String word, String definition) {
        addWordRecursive(word, definition, 0);
    }

    private void addWordRecursive(String word, String definition, int index) {
        if (index == word.length()) {
            this.definitions.add(definition);
            return;
        }

        char currentLetter = word.charAt(index);
        LexiNode child = children.get(currentLetter);
        if (child == null) {
            child = new LexiNode(currentLetter, currentWord + currentLetter);
            children.put(currentLetter, child);
        }
        child.addWordRecursive(word, definition, index + 1);
    }

    public List<String> getWordDefinitions(String word) {
        return getWordDefinitionsRecursive(word, 0);
    }

    private List<String> getWordDefinitionsRecursive(String word, int index) {
        if (index == word.length()) {
            return definitions;
        }

        char currentLetter = word.charAt(index);
        LexiNode child = children.get(currentLetter);
        if (child == null) {
            return new ArrayList<>();
        }
        return child.getWordDefinitionsRecursive(word, index + 1);
    }


    public List<String> getDefinitions() {
        return definitions;
    }

    public HashMap<Character, LexiNode> getChildren() {
        return children;
    }


    public ArrayList<String> getWordsWith(String input) {
        ArrayList<String> results = new ArrayList<>();
        getWordsWithR(this, input, results);
        return results;
    }

    private LexiNode findWordNodeRecursive(LexiNode node, String word) {
        if (word.isEmpty()) {
            return node;
        }

        char currentChar = word.charAt(0);
        String remainingWord = word.substring(1);

        if (node.children.containsKey(currentChar)) {
            return findWordNodeRecursive(node.children.get(currentChar), remainingWord);
        } else {
            return null;
        }
    }

    public boolean updateWordDefinition(String word, String newDefinition) {
        LexiNode targetNode = findWordNodeRecursive(this, word);

        if (targetNode != null) {
            targetNode.updateWordDefinition(newDefinition);
            return true;
        } else {
            return false;
        }
    }

    // Helper method to update the word definition
    private void updateWordDefinition(String newDefinition) {
        if (!definitions.isEmpty()) {
            definitions.clear();
            definitions.add(newDefinition);
        }
    }


    private void getWordsWithR(LexiNode currentNode, String input, ArrayList<String> results) {
        for (LexiNode child : currentNode.children.values()) {
            if (child.currentWord.toLowerCase().contains(input.toLowerCase())) {
                if (!child.definitions.isEmpty()) {
                    results.add(child.currentWord);
                }
            }
            getWordsWithR(child, input, results);
        }
    }

    public String displayAll() {
        StringBuilder output = new StringBuilder();

        if (!definitions.isEmpty()) {
            output.append("Word: ").append(currentWord).append("\n");
            for (int i = 0; i < definitions.size(); i++) {
                output.append("Definition ").append(i + 1).append(": ").append(definitions.get(i)).append("\n");
            }
            output.append("\n");
        }

        for (LexiNode child : children.values()) {
            output.append(child.displayAll());
        }

        return output.toString();
    }

    public String displayOnlyWords() {
        StringBuilder output = new StringBuilder();

        if (!definitions.isEmpty()) {
            output.append(currentWord).append("\n");
        }

        for (LexiNode child : children.values()) {
            output.append(child.displayOnlyWords());
        }

        return output.toString();
    }
}
