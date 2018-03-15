import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;

public class DictionaryTree {

	private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
	Optional<Integer> popularity = Optional.empty();

	private boolean isWord() {
		return popularity.isPresent();
	}

	private void removeWord() {
		popularity = Optional.empty();
	}

	private boolean setWord(int p) {
		if (!popularity.isPresent() || popularity.get() < p) {
			popularity = Optional.of(p);
			return true;
		}
		return false;
	}

	/**
	 * Inserts the given word into this dictionary. If the word already exists,
	 * nothing will change.
	 *
	 * @param word
	 *            the word to insert
	 */
	void insert(String word) {
		insert(word, 0);
	}

	/**
	 * Inserts the given word into this dictionary with the given popularity. If
	 * the word already exists, the popularity will be overriden by the given
	 * value.
	 *
	 * @param word
	 *            the word to insert
	 * @param popularity
	 *            the popularity of the inserted word
	 */
	void insert(String word, int popularity) {
		assert (word != null);
		word = word.toLowerCase();
		DictionaryTree currentTree = this;
		int i = 0;
		while (i < word.length()) {
			char c = word.charAt(i);
			if (currentTree.getChildren().containsKey(c)) {
				currentTree = currentTree.getChildren().get(c);
				i++;
			} else {
				break;
			}
		}
		if (i <= word.length()) {
			while (i < word.length()) {
				DictionaryTree newTree = new DictionaryTree();
				currentTree.getChildren().put(word.charAt(i), newTree);
				currentTree = newTree;
				// System.out.println(currentTree.isWord());
				i++;
			}
			currentTree.setWord(popularity);
			// System.out.println("Added " + word + " last character " +
			// word.charAt(i - 1));
		}
	}

	/**
	 * Removes the specified word from this dictionary. Returns true if the
	 * caller can delete this node without losing part of the dictionary, i.e.
	 * if this node has no children after deleting the specified word.
	 *
	 * @param word
	 *            the word to delete from this dictionary
	 * @return whether or not the parent can delete this node from its children
	 */
	boolean remove(String word) {
		assert (word != null);
		word = word.toLowerCase();
		DictionaryTree currentTree = this;
		int i = 0;
		while (i < word.length()) {
			char c = word.charAt(i);
			if (currentTree.getChildren().containsKey(c)) {
				DictionaryTree nextTree = currentTree.getChildren().get(c);
				if (nextTree.maximumBranching() <= 1) {
					currentTree.getChildren().remove(c);
					return true;
				}
				currentTree = nextTree;
				i++;
			} else {
				return false;
			}
		}
		currentTree.removeWord();
		return true;
	}

	/**
	 * Determines whether or not the specified word is in this dictionary.
	 *
	 * @param word
	 *            the word whose presence will be checked
	 * @return true if the specified word is stored in this tree; false
	 *         otherwise
	 */
	boolean contains(String word) {
		assert (word != null);
		DictionaryTree currentTree = this;
		int i = 0;
		while (i < word.length()) {
			char c = word.charAt(i);
			if (currentTree.getChildren().containsKey(c)) {
				currentTree = currentTree.getChildren().get(c);
				i++;
			} else {
				return false;
			}
		}
		return currentTree.isWord();
	}

	/**
	 * @param prefix
	 *            the prefix of the word returned
	 * @return a word that starts with the given prefix, or an empty optional if
	 *         no such word is found.
	 */
	Optional<String> predict(String prefix) {
		List<String> p = predict(prefix, 1);
		return p.isEmpty() ? Optional.empty() : Optional.of(p.get(0));
	}

	/**
	 * Predicts the (at most) n most popular full English words based on the
	 * specified prefix. If no word with the specified prefix is found, an empty
	 * list is returned.
	 *
	 * @param prefix
	 *            the prefix of the words found
	 * @return the (at most) n most popular words with the specified prefix
	 */
	List<String> predict(String prefix, int n) {
		assert (prefix != null);
		prefix = prefix.toLowerCase();
		DictionaryTree currentTree = this;
		int i = 0;
		while (i < prefix.length()) {
			char c = prefix.charAt(i);
			if (currentTree.getChildren().containsKey(c)) {
				currentTree = currentTree.getChildren().get(c);
				i++;
			} else {
				//prefix has no words.
				return new ArrayList<String>();
			}
		}
		List<Word> predictions = new ArrayList<Word>();
		currentTree.allWordsRecur(prefix, predictions);
		Collections.sort(predictions);
		return wordToString(predictions).subList(0, n);
	}

	/**
	 * @return the number of leaves in this tree, i.e. the number of words which
	 *         are not prefixes of any other word.
	 */
	int numLeaves() {
		return numLeavesRecur();
	}

	private int numLeavesRecur() {
		int n = 0;
		if (children.isEmpty() && isWord()) {
			return 1;
		} else {
			for (char c : children.keySet()) {
				n += children.get(c).numLeavesRecur();
			}
			return n;
		}
	}

	/**
	 * @return the maximum number of children held by any node in this tree
	 */
	int maximumBranching() {
		Queue<DictionaryTree> bfs = new LinkedBlockingQueue<DictionaryTree>();
		bfs.add(this);
		DictionaryTree currentTree;
		int largest = children.size();
		while (!bfs.isEmpty()) {
			currentTree = bfs.poll();
			if (currentTree.getChildren().keySet().size() > largest) {
				largest = currentTree.getChildren().keySet().size();
			}
			for (DictionaryTree t : currentTree.getChildren().values()) {
				bfs.add(t);
			}
		}
		return largest;
	}

	/**
	 * @return the height of this tree, i.e. the length of the longest branch
	 */
	int height() {
		Queue<DictionaryTree> bfs = new LinkedBlockingQueue<DictionaryTree>();
		bfs.add(this);
		DictionaryTree currentTree;
		int depth = -1;
		while (true) {
			if (bfs.isEmpty()) return depth;
			int levelSize = bfs.size();
			for (int i = 0;  i < levelSize; i++) {
				currentTree = bfs.poll();
				for (DictionaryTree t : currentTree.getChildren().values()) {
					bfs.add(t);
				}
			}
			depth++;
		}
	}

	/**
	 * @return the number of nodes in this tree
	 */
	int size() {
		int size = 1;
		for (DictionaryTree d : children.values()) {
			size += d.size();
		}
		return size;
	}

	Map<Character, DictionaryTree> getChildren() {
		return children;
	}

	/**
	 * @return the longest word in this tree
	 */
	String longestWord() {		
		return longestWordRecur("");
	}
	
	private String longestWordRecur(String prefix) {
		String longest = "";
		for (char c : children.keySet()) {
			String current = children.get(c).longestWordRecur(c + "");	
			if (current.length() > longest.length()) {
				longest = current;
			}
		}	
		return prefix + longest;
	}

	/**
	 * @return all words stored in this tree as a list
	 */
	List<String> allWords() {
		List<Word> words = new LinkedList<Word>();
		allWordsRecur("", words);
		return wordToString(words);	
	}

	void allWordsRecur(String soFar, List<Word> words) {
		if (isWord()) {
			words.add(new Word(soFar, this.popularity.get()));
		}
		for (Character c : children.keySet()) {
			children.get(c).allWordsRecur(soFar + c, words);
		}
	}
	
	List<String>wordToString(List<Word> words) {
		List<String> strings = new LinkedList<String>();
		for (Word w : words) {
			strings.add(w.getWord());
		}
		return strings;
	}

	/**
	 * Folds the tree using the given function. Each of this node's children is
	 * folded with the same function, and these results are stored in a
	 * collection, cResults, say, then the final result is calculated using
	 * f.apply(this, cResults).
	 *
	 * @param f
	 *            the summarising function, which is passed the result of
	 *            invoking the given function
	 * @param <A>
	 *            the type of the folded value
	 * @return the result of folding the tree using f
	 */
	<A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
		throw new RuntimeException("DictionaryTree.fold not implemented yet");
	}

}
