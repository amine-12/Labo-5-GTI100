package DictioMoteur;

import java.util.ArrayList;
import java.util.List;

public interface LexiInterface {
    public LexiTree.WordStatus addOrUpdateWord(String word, String definition);

    public List<String> getWordDefinitions(String word);

    public ArrayList<String> getWordsWith(String input);

    public String displayOnlyWords();

}
