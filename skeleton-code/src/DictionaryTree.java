import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class DictionaryTree {

	private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
	// empty popularity value indicates the node is not the end of a word.
	Optional<Integer> popularity = Optional.empty();
	
	/**
	 * @return if the node signals the end of a word.
	 */
	private boolean isWord() {
		return popularity.isPresent();
	}

	/**
	 * Stops a node from being the end of a word.
	 */
	private void removeWord() {
		popularity = Optional.empty();
	}

	/**
	 * Sets a node to be a word with the given popularity. Overwrites old popularity
	 * value of given value is bigger.
	 * 
	 * @param p
	 *            the popularity of the word
	 * @return if the popularity was overwritten.
	 */
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
		// just call other insert method with minimum value for popularity.
		insert(word, Integer.MIN_VALUE);
	}

	/**
	 * Inserts the given word into this dictionary with the given popularity. If the
	 * word already exists, the popularity will be overriden by the given value.
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
			if (currentTree.getChildren().containsKey(c)) { // if the next character is in the tree move to that node.
				currentTree = currentTree.getChildren().get(c);
				i++;
			} else { // if not then we need to create these nodes.
				break;
			}
		}
		// create extra nodes and set the last node to be the end of the word.

		while (i < word.length()) {
			DictionaryTree newTree = new DictionaryTree();
			currentTree.getChildren().put(word.charAt(i), newTree);
			currentTree = newTree;
			i++;
		}
		currentTree.setWord(popularity);

	}

	/**
	 * Removes the specified word from this dictionary. Returns true if the caller
	 * can delete this node without losing part of the dictionary, i.e. if this node
	 * has no children after deleting the specified word.
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
	 * @return true if the specified word is stored in this tree; false otherwise
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
	 * @return a word that starts with the given prefix, or an empty optional if no
	 *         such word is found.
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
				// prefix has no words.
				return new ArrayList<String>();
			}
		}
		List<Word> predictions = new ArrayList<Word>();
		currentTree.allWordsRecur(prefix, predictions);
		Collections.sort(predictions);
		// for (Word w : predictions) System.out.println(w.getWord() + " " +
		// w.getPopularity());
		return wordToString(predictions).subList(0, n);
	}

	/**
	 * @return the number of leaves in this tree, i.e. the number of words which are
	 *         not prefixes of any other word.
	 */
	int numLeaves() {
		BiFunction<DictionaryTree, Collection<Integer>, Integer> leaves = new BiFunction<DictionaryTree, Collection<Integer>, Integer>() {

			@Override
			public Integer apply(DictionaryTree t, Collection<Integer> u) {
				if (t.isWord() && t.getChildren().isEmpty()) {
					return 1;
				} else {
					int total = 0;
					for (int i : u) {
						total += i;
					}
					return total;
				}
			}

		};
		return (int) fold(leaves);
	}

	/**
	 * @return the maximum number of children held by any node in this tree
	 */
	int maximumBranching() {
		BiFunction<DictionaryTree, Collection<Integer>, Integer> maxBranch = new BiFunction<DictionaryTree, Collection<Integer>, Integer>() {

			@Override
			public Integer apply(DictionaryTree t, Collection<Integer> u) {
				int mostBranches = t.getChildren().size();
				for (int i : u) {
					if (i > mostBranches) {
						mostBranches = i;
					}
				}
				return mostBranches;
			}

		};
		return (int) fold(maxBranch);
	}

	/**
	 * @return the height of this tree, i.e. the length of the longest branch
	 */
	int height() {
		BiFunction<DictionaryTree, Collection<Integer>, Integer> height = new BiFunction<DictionaryTree, Collection<Integer>, Integer>() {

			@Override
			public Integer apply(DictionaryTree t, Collection<Integer> u) {
				int deepest = -1;
				for (int i : u) {
					if (i > deepest) {
						deepest = i;
					}
				}
				return deepest + 1;
			}

		};
		return (int)fold(height);
	}

	/**
	 * @return the number of nodes in this tree
	 */
	int size() {
		BiFunction<DictionaryTree, Collection<Integer>, Integer> size = new BiFunction<DictionaryTree, Collection<Integer>, Integer>() {

			@Override
			public Integer apply(DictionaryTree t, Collection<Integer> u) {
				int total = 0;
				for (int i : u) {
					total += i;
				}
				return total + 1;
			}

		};
		return (int) fold(size);
	}

	private Map<Character, DictionaryTree> getChildren() {
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

	private void allWordsRecur(String soFar, List<Word> words) {
		//If the node is the end of a word add the complete word to the list
		if (isWord()) {
			words.add(new Word(soFar, this.popularity.get()));
		}
		//carry on the recursion.
		for (Character c : children.keySet()) {
			children.get(c).allWordsRecur(soFar + c, words);
		}
	}

	private List<String> wordToString(List<Word> words) {
		List<String> strings = new LinkedList<String>();
		for (Word w : words) {
			strings.add(w.word);
		}
		return strings;
	}

	/**
	 * Folds the tree using the given function. Each of this node's children is
	 * folded with the same function, and these results are stored in a collection,
	 * cResults, say, then the final result is calculated using f.apply(this,
	 * cResults).
	 *
	 * @param f
	 *            the summarising function, which is passed the result of invoking
	 *            the given function
	 * @param <A>
	 *            the type of the folded value
	 * @return the result of folding the tree using f
	 */
	<A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
		LinkedList<A> result = new LinkedList<A>();
		//create list of folded results of the children
		for (DictionaryTree d : getChildren().values()) {
			result.add(d.<A>fold(f));
		}
		//apply the function to this node.
		return f.apply(this, result);
	}
	
	private class Word implements Comparable<Word>{
			
			public String word;
			public int popularity;
			
			public Word(String word, int popularity) {
				this.word = word;
				this.popularity = popularity;
			}

			@Override
			public int compareTo(Word w) {
				return Integer.compare(w.popularity, popularity);
			}
			
		}

}
