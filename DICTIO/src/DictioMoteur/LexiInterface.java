package DictioMoteur;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface LexiInterface définissant les méthodes essentielles pour interagir avec l'arbre LexiTree.
 * Cette interface décrit les responsabilités de la classe qui l'implémente, notamment l'ajout ou la mise à jour de mots,
 * la récupération de définitions, la recherche de mots contenant une chaîne de caractères et l'affichage des mots.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public interface LexiInterface {
    LexiTree.WordStatus addOrUpdateWord(String word, String definition);

    List<String> getWordDefinitions(String word);

    ArrayList<String> getWordsWith(String input);

    String displayOnlyWords();

}
