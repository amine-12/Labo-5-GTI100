package DictioMoteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * La classe LexiTree est une structure de données basée sur un arbre utilisée pour stocker
 * un dictionnaire de mots et leurs définitions. Elle implémente l'interface LexiInterface
 * et fournit des méthodes pour générer l'arbre à partir d'une chaîne de caractères, récupérer
 * les définitions des mots, ajouter ou mettre à jour des mots, et rechercher des mots basés sur une entrée donnée.
 *
 * Collaborateurs :
 * - LexiInterface : Interface implémentée par LexiTree, définissant les
 *                   méthodes requises pour interagir avec le dictionnaire.
 * - LexiNode : Classe interne représentant un nœud de l'arbre LexiTree,
 *              contenant des méthodes pour manipuler les mots et leurs
 *              définitions dans le dictionnaire.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class LexiTree implements LexiInterface {
    private final LexiNode root;

    /**
     * Constructeur LexiTree sans arguments.
     * Initialise un nouvel arbre LexiTree avec un nœud racine.
     */
    public LexiTree() {
        this.root = new LexiNode('\0', "");
    }

    /**
     * Méthode pour générer l'arbre LexiTree à partir d'une chaîne de caractères.
     * Préconditions : la chaîne de caractères doit contenir des mots et leurs définitions séparées
     *                 par des sauts de ligne.
     * Postconditions : l'arbre LexiTree est construit avec les mots et les définitions de la chaîne de caractères.
     *
     * @param content Chaîne de caractères contenant les mots et leurs définitions.
     */
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

    /**
     * Méthode pour obtenir les définitions d'un mot.
     * Préconditions : le mot doit être non nul.
     * Postconditions : retourne une liste de définitions pour le mot donné.
     *
     * @param word Le mot pour lequel obtenir les définitions.
     * @return Liste de définitions du mot.
     */
    public List<String> getWordDefinitions(String word) {
        return root.getWordDefinitions(word);
    }

    //methode pour tester
    public String displayAll() {
        return root.displayAll();
    }

    /**
     * Méthode pour afficher uniquement les mots dans l'arbre LexiTree.
     * Préconditions : aucune.
     * Postconditions : retourne une chaîne de caractères contenant tous les mots.
     *
     * @return Chaîne de caractères contenant tous les mots.
     */
    public String displayOnlyWords() {
        return root.displayOnlyWords();
    }

    /**
     * Méthode pour obtenir tous les mots contenant l'entrée donnée.
     * Préconditions : l'entrée doit être non nulle.
     * Postconditions : retourne une liste de mots contenant l'entrée donnée.
     *
     * @param input Chaîne de caractères à rechercher dans les mots.
     * @return Liste de mots contenant l'entrée donnée.
     */
    public ArrayList<String> getWordsWith(String input) {
        return root.getWordsWith(input);
    }

    /**
     * Méthode pour ajouter ou mettre à jour un mot et sa définition dans l'arbre LexiTree.
     * Préconditions : le mot et la définition doivent être non nuls.
     * Postconditions : le mot est ajouté ou mis à jour avec la nouvelle définition.
     *
     * @param word Le mot à ajouter ou mettre à jour.
     * @param definition La définition du mot.
     * @return Énumération WordStatus (ADDED ou UPDATED) indiquant si le mot a été ajouté ou mis à jour.
     */
    public WordStatus addOrUpdateWord(String word, String definition) {
        return root.addOrUpdateWord(word,definition);
    }

    /**
     * L'énumération WordStatus représente le statut d'un mot lorsqu'il est ajouté ou mis à jour
     * dans la structure de données LexiTree.
     *
     * Valeurs de l'énumération :
     * - ADDED : Le mot a été ajouté à la structure de données LexiTree.
     * - UPDATED : La définition du mot existant a été mise à jour dans la structure de données LexiTree.
     */
    public enum WordStatus {
        ADDED,
        UPDATED
    }

    /**
     * La classe LexiNode représente un nœud de l'arbre LexiTree.
     * Chaque nœud contient une lettre, un mot courant, une liste de définitions et un ensemble d'enfants.
     * La classe est responsable de la gestion des mots et de leurs définitions au sein du dictionnaire.
     *
     * Collaborateurs :
     * - LexiTree : La classe externe qui utilise LexiNode pour créer et gérer la structure de l'arbre.
     */
    private class LexiNode {
        private final char letter;
        private final String currentWord;
        private final List<String> definitions;
        private final HashMap<Character, LexiNode> children;

        /**
         * Constructeur LexiNode avec un caractère et une chaîne de caractères en arguments.
         * Initialise un nouveau nœud LexiNode avec un caractère et la chaîne de caractères représentant le mot courant.
         *
         * @param letter Caractère représentant la lettre du nœud.
         * @param currentWord Chaîne de caractères représentant le mot courant jusqu'à ce nœud.
         */
        public LexiNode(char letter, String currentWord) {
            this.letter = letter;
            this.currentWord = currentWord;
            this.definitions = new ArrayList<>();
            this.children = new HashMap<>();
        }

        /**
         * Méthode pour ajouter un mot et sa définition au nœud.
         * Préconditions : le mot et la définition doivent être non nuls.
         * Postconditions : le mot et sa définition sont ajoutés au nœud et à ses enfants.
         *
         * @param word Le mot à ajouter.
         * @param definition La définition du mot.
         */
        public void addWord(String word, String definition) {
            addWordRecursive(word, definition, 0);
        }

        /**
         * Méthode récursive pour ajouter un mot et sa définition au nœud.
         * Préconditions : le mot et la définition doivent être non nuls et l'index doit être valide.
         * Postconditions : le mot et sa définition sont ajoutés au nœud et à ses enfants.
         * Complexité : O(n), où n est la longueur du mot.
         *
         * @param word Le mot à ajouter.
         * @param definition La définition du mot.
         * @param index L'index courant dans le mot.
         */
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

        /**
         * Méthode pour obtenir les définitions d'un mot.
         * Préconditions : le mot doit être non nul.
         * Postconditions : retourne une liste de définitions pour le mot donné.
         *
         * @param word Le mot pour lequel obtenir les définitions.
         * @return Liste de définitions du mot.
         */
        public List<String> getWordDefinitions(String word) {
            return getWordDefinitionsRecursive(this, word, 0);
        }

        /**
         * Méthode récursive pour obtenir les définitions d'un mot.
         * Préconditions : le nœud, le mot et l'index doivent être valides.
         * Postconditions : retourne une liste de définitions pour le mot donné.
         * Complexité : O(n), où n est la longueur du mot.
         *
         * @param node Le nœud LexiNode courant.
         * @param word Le mot pour lequel obtenir les définitions.
         * @param index L'index courant dans le mot.
         * @return Liste de définitions du mot.
         */
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

        /**
         * Méthode pour obtenir la liste des définitions du nœud courant.
         * Préconditions : Aucune.
         * Postconditions : Retourne une liste de définitions associées au nœud courant.
         *
         * @return Liste de définitions du nœud courant.
         */
        public List<String> getDefinitions() {
            return definitions;
        }

        /**
         * Méthode pour obtenir la table de hachage des enfants du nœud courant.
         * Préconditions : Aucune.
         * Postconditions : Retourne la table de hachage des enfants du nœud courant,
         *                  avec la lettre en clé et le nœud LexiNode correspondant en valeur.
         *
         * @return Table de hachage des enfants du nœud courant.
         */
        public HashMap<Character, LexiNode> getChildren() {
            return children;
        }

        /**
         * Méthode pour obtenir tous les mots contenant l'entrée donnée.
         * Préconditions : l'entrée doit être non nulle.
         * Postconditions : retourne une liste de mots contenant l'entrée donnée.
         *
         * @param input Chaîne de caractères à rechercher dans les mots.
         * @return Liste de mots contenant l'entrée donnée.
         */
        public ArrayList<String> getWordsWith(String input) {
            ArrayList<String> results = new ArrayList<>();
            getWordsWithR(this, input, results);
            return results;
        }

        /**
         * Méthode récursive pour trouver le nœud d'un mot.
         * Préconditions : le nœud et le mot doivent être valides.
         * Postconditions : retourne le nœud correspondant au mot donné.
         * Complexité : O(n), où n est la longueur du mot.
         *
         * @param node Le nœud LexiNode courant.
         * @param word Le mot pour lequel trouver le nœud.
         * @return Nœud LexiNode correspondant au mot donné.
         */
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

        /**
         * Méthode pour ajouter ou mettre à jour un mot dans le dictionnaire.
         * Préconditions : le mot et sa définition doivent être non nuls.
         * Postconditions : si le mot n'existe pas déjà dans le dictionnaire, il est ajouté avec sa définition ;
         *                  sinon, la définition du mot existant est mise à jour.
         *
         * @param word Le mot à ajouter ou à mettre à jour dans le dictionnaire.
         * @param definition La définition associée au mot.
         * @return WordStatus.ADDED si le mot a été ajouté, WordStatus.UPDATED si la définition du mot a
         *         été mise à jour.
         */
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

        /**
         * Méthode pour mettre à jour la définition d'un mot.
         * Préconditions : le mot et la nouvelle définition doivent être non nuls.
         * Postconditions : la définition du mot est mise à jour avec la nouvelle définition.
         *
         * @param word Le mot dont la définition doit être mise à jour.
         * @param newDefinition La nouvelle définition du mot.
         */
        public void updateWordDefinition(String word, String newDefinition) {
            LexiNode targetNode = findWordNodeRecursive(this, word);

            if (targetNode != null) {
                targetNode.updateWordDefinition(newDefinition);
            }
        }

        /**
         * Méthode pour mettre à jour la définition d'un mot à partir du nœud courant.
         * Préconditions : la nouvelle définition doit être non nulle.
         * Postconditions : la définition du mot est mise à jour avec la nouvelle définition.
         *
         * @param newDefinition La nouvelle définition du mot.
         */
        public void updateWordDefinition(String newDefinition) {
            if (!definitions.isEmpty()) {
                definitions.clear();
                definitions.add(newDefinition);
            }
        }

        /**
         * Méthode récursive pour obtenir tous les mots contenant l'entrée donnée.
         * Préconditions : le nœud courant, l'entrée et la liste des résultats doivent être valides.
         * Postconditions : la liste des résultats contient tous les mots contenant l'entrée donnée.
         * Complexité : O(n*m), où n est le nombre total de nœuds et m est le nombre de mots contenant l'entrée.
         *
         * @param currentNode Le nœud LexiNode courant.
         * @param input Chaîne de caractères à rechercher dans les mots.
         * @param results Liste de mots contenant l'entrée donnée.
         */
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

        /**
         * Méthode pour afficher uniquement les mots de l'arbre LexiTree.
         * Préconditions : Aucune.
         * Postconditions : Retourne une chaîne de caractères contenant tous les mots de l'arbre, chacun
         *                  sur une nouvelle ligne.
         *
         * @return Chaîne de caractères contenant uniquement les mots de l'arbre LexiTree.
         */
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

