package DictioMoteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LexiTree implements LexiInterface {
    private final LexiNode root;

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

    public List<String> getWordDefinitions(String word) {
        return root.getWordDefinitions(word);
    }

    //methode pour tester
    public String displayAll() {
        return root.displayAll();
    }

    public String displayOnlyWords() {
        return root.displayOnlyWords();
    }

    public ArrayList<String> getWordsWith(String input) {
        return root.getWordsWith(input);
    }

    public WordStatus addOrUpdateWord(String word, String definition) {
        return root.addOrUpdateWord(word,definition);
    }

    public enum WordStatus {
        ADDED,
        UPDATED
    }

    public class LexiNode {
        private final char letter;
        private String currentWord;
        private final List<String> definitions;
        private final HashMap<Character, LexiNode> children;

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
            return getWordDefinitionsRecursive(this, word, 0);
        }

        private List<String> getWordDefinitionsRecursive(LexiNode node, String word, int index) {
            if (index == word.length()) {
                return node.definitions;
            }

            char currentLetter = word.charAt(index);
            LexiNode child = node.children.get(currentLetter);

            if (child == null) {
                return new ArrayList<>();
            }

            return getWordDefinitionsRecursive(child, word, index + 1);
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

        public WordStatus addOrUpdateWord(String word, String definition) {
            generateTreeFromString(DictioToolBox.content);
            ArrayList<String> wordDefinitions = (ArrayList<String>) getWordDefinitions(word);

            if (wordDefinitions.isEmpty()) {
                addWord(word, definition);
                return WordStatus.ADDED;
            } else {
                updateWordDefinition(word, definition);
                return WordStatus.UPDATED;
            }
        }

        public void updateWordDefinition(String word, String newDefinition) {
            LexiNode targetNode = findWordNodeRecursive(this, word);

            if (targetNode != null) {
                targetNode.updateWordDefinition(newDefinition);
            }
        }

        public void updateWordDefinition(String newDefinition) {
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

        //methode pour tester
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
}

