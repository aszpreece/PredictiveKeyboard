import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * @author Kelsey McKenna
 */
public class DictionaryTreeTests {

    @Test
    public void heightOfRootShouldBeZero() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals(0, unit.height());
    }

    @Test
    public void heightOfWordShouldBeWordLength() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("word", 0);
        Assertions.assertEquals("word".length(), unit.height());
    }
    
    @Test
    public void testSize() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("hey");
        unit.insert("at");
        unit.insert("atlas");
        unit.insert("attention");
        Assertions.assertEquals(unit.size(), 19);
    }
    
    @Test
    public void testHeight() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("hey");
        unit.insert("at");
        unit.insert("atlas");
        unit.insert("attention");
        System.out.println(unit.size());
        Assertions.assertEquals(unit.height(), 9);
    }
    
    @Test
    public void testContains() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("hey");
        unit.insert("at");
        unit.insert("atlas");
        unit.insert("attention");
        System.out.println(unit.size());
        Assertions.assertEquals(unit.contains("attention"), true);
        Assertions.assertEquals(unit.contains("blam"), false);
    }
    
    @Test
    public void testRemove() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("hey");
        unit.insert("at");
        unit.insert("atlas");
        unit.insert("attention");
        System.out.println(unit.size());
        Assertions.assertEquals(unit.contains("atlas"), true);
        unit.remove("atlas");
        Assertions.assertEquals(unit.contains("atlas"), false);
    }
    

}
