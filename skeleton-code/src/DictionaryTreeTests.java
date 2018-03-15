import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * @author Kelsey McKenna
 */
public class DictionaryTreeTests {

	private static final String DICTIONARY_LOCATION = "word-popularity.txt";

	static DictionaryTree loadWords(File f) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
			String word;
			DictionaryTree d = new DictionaryTree();
			int i = 1;
			while ((word = reader.readLine()) != null) {
				d.insert(word, i);
				i++;
			}

			return d;
		}
	}

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
		System.out.println(unit.size());
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
		Assertions.assertEquals(unit.height(), 9);
		DictionaryTree unit2 = new DictionaryTree();
		Assertions.assertEquals(unit2.height(), 0);
	}

	@Test
	public void testContains() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("hello");
		unit.insert("hey");
		unit.insert("at");
		unit.insert("atlas");
		unit.insert("attention");
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
		Assertions.assertEquals(unit.contains("atlas"), true);
		unit.remove("atlas");
		Assertions.assertEquals(unit.contains("atlas"), false);
	}

	@Test
	public void testMaxBranches() {
		DictionaryTree unit = new DictionaryTree();

		unit.insert("hello");
		unit.insert("hey");
		unit.insert("at");
		unit.insert("atlas");
		unit.insert("attention");
		unit.insert("aty");
		Assertions.assertEquals(unit.maximumBranching(), 3);
	}

	@Test
	public void testPopularity() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("hello", 10);
		unit.insert("hey", 4);
		unit.insert("at", 200);
		unit.insert("atlas", 100);
		unit.insert("attention", 0);
		unit.insert("aty", 1);
		Assertions.assertEquals(unit.predict("a").get(), "attention");
	}

	@Test
	public void testBranches() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("hello", 10);
		unit.insert("hey", 4);
		unit.insert("at", 200);
		unit.insert("atlas", 100);
		unit.insert("attention", 0);
		unit.insert("aty", 1);
		Assertions.assertEquals(unit.numLeaves(), 5);
	}

	@Test
	public void nPredict() {

		try {
			DictionaryTree unit = loadWords(new File(DICTIONARY_LOCATION));
			List<String> expected = new ArrayList<String>(
					Arrays.asList("phone", "photo", "photos", "phones", "physical"));
			List<String> actual = unit.predict("ph", 5);
			Assertions.assertIterableEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void insertWithPopularity() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("hello", 10);
		unit.insert("hey", 4);
		Assertions.assertEquals(unit.predict("he"), "hey");
		unit.insert("hello", 3);
		Assertions.assertEquals(unit.predict("he"), "hello");
	}
}
