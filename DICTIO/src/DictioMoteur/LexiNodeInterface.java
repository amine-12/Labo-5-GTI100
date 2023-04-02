package DictioMoteur;

import java.util.ArrayList;
import java.util.List;

public interface LexiNodeInterface {
    public void addWord(String word, String definition);

    public List<String> getWordDefinitions(String word);


    public boolean updateWordDefinitionInFile(String word, String newDefinition);

    public String displayAll();

    public String displayOnlyWords();

}
