package TestPackage;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LexiTreeTest {

    @Test
    void testGetWordDefinitions() {

        LexiTree lexi = new LexiTree();
        lexi.generateTreeFromString("apple&fruit\nbanana&yellow fruit");

        List<String> definitions = lexi.getWordDefinitions("apple");
        assertEquals(1, definitions.size());
        assertTrue(definitions.contains("fruit"));
    }

    @Test
    void testGenerateTreeFromString() {
        LexiTree lexi = new LexiTree();
        lexi.generateTreeFromString("apple&fruit\nbanana&yellow fruit");

        List<String> appleDefinitions = lexi.getWordDefinitions("apple");
        List<String> bananaDefinitions = lexi.getWordDefinitions("banana");

        assertEquals(1, appleDefinitions.size());
        assertTrue(appleDefinitions.contains("fruit"));

        assertEquals(1, bananaDefinitions.size());
        assertTrue(bananaDefinitions.contains("yellow fruit"));
    }

    @Test
    void testDisplayOnlyWords() {
        LexiTree lexi = new LexiTree();
        lexi.generateTreeFromString("apple&fruit\nbanana&yellow fruit");

        String words = lexi.displayOnlyWords();
        assertTrue(words.contains("apple"));
        assertTrue(words.contains("banana"));
    }

    @Test
    void testGetWordsWith() {
        LexiTree lexi = new LexiTree();
        lexi.generateTreeFromString("apple&fruit\nbanana&yellow fruit");

        List<String> wordsWithA = lexi.getWordsWith("a");
        assertEquals(2, wordsWithA.size());
        assertTrue(wordsWithA.contains("apple"));
        assertTrue(wordsWithA.contains("banana"));
    }

    @Test
    void testAddOrUpdateWord() {
        LexiTree lexi = new LexiTree();
        String content= "apple&fruit\nbanana&yellow fruit";
        lexi.generateTreeFromString(content);
        DictioToolBox.content = content;

        LexiTree.WordStatus status = lexi.addOrUpdateWord("apple", "new definition");
        assertEquals(LexiTree.WordStatus.UPDATED, status);

        List<String> updatedDefinitions = lexi.getWordDefinitions("apple");
        assertEquals(1, updatedDefinitions.size());
        assertTrue(updatedDefinitions.contains("new definition"));
    }
}