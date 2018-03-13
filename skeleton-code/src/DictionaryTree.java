import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class DictionaryTree {

    private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
    private final static String WORD_PATTERN 	= "[A-Za-z]*";
    private final static char END_OF_WORD = '#';

    /**
     * Inserts the given word into this dictionary.
     * If the word already exists, nothing will change.
     *
     * @param word the word to insert
     */
    void insert(String word) {
    	assert(word != null);
    	assert(word.matches(WORD_PATTERN));
    	word = word.toLowerCase() + END_OF_WORD;
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
    	while (i < word.length()) {
    		DictionaryTree newTree = new DictionaryTree();		
    		currentTree.getChildren().put(word.charAt(i), newTree); 		
    		currentTree = newTree;
    		i++;
    	}
    	
    }

    /**
     * Inserts the given word into this dictionary with the given popularity.
     * If the word already exists, the popularity will be overriden by the given value.
     *
     * @param word       the word to insert
     * @param popularity the popularity of the inserted word
     */
    void insert(String word, int popularity) {
        throw new RuntimeException("DictionaryTree.insert not implemented yet");
    }

    /**
     * Removes the specified word from this dictionary.
     * Returns true if the caller can delete this node without losing
     * part of the dictionary, i.e. if this node has no children after
     * deleting the specified word.
     *
     * @param word the word to delete from this dictionary
     * @return whether or not the parent can delete this node from its children
     */
    boolean remove(String word) {
        throw new RuntimeException("DictionaryTree.remove not implemented yet");
    }

    /**
     * Determines whether or not the specified word is in this dictionary.
     *
     * @param word the word whose presence will be checked
     * @return true if the specified word is stored in this tree; false otherwise
     */
    boolean contains(String word) {
    	assert(word != null);
    	word = word + END_OF_WORD;
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
       	return true;
    }

    /**
     * @param prefix the prefix of the word returned
     * @return a word that starts with the given prefix, or an empty optional
     * if no such word is found.
     */
    Optional<String> predict(String prefix) {
    	assert(prefix != null);
    	assert(prefix.matches(WORD_PATTERN));
    	prefix = prefix.toLowerCase();
    	DictionaryTree currentTree = this;
    	int i = 0;
    	while (i < prefix.length()) {
    		char c = prefix.charAt(i);
    		if (currentTree.getChildren().containsKey(c)) {
    			currentTree = currentTree.getChildren().get(c);
    			i++;
    		} else {
    			return Optional.empty();
    		}
    	}
    	return Optional.of(prefix + currentTree.allWords().get(0));
    	
    }


    /**
     * Predicts the (at most) n most popular full English words based on the specified prefix.
     * If no word with the specified prefix is found, an empty list is returned.
     *
     * @param prefix the prefix of the words found
     * @return the (at most) n most popular words with the specified prefix
     */
    List<String> predict(String prefix, int n) {
        throw new RuntimeException("DictionaryTree.predict not implemented yet");
    }

    /**
     * @return the number of leaves in this tree, i.e. the number of words which are
     * not prefixes of any other word.
     */
    int numLeaves() {
        throw new RuntimeException("DictionaryTree.numLeaves not implemented yet");
    }

    /**
     * @return the maximum number of children held by any node in this tree
     */
    int maximumBranching() {
        throw new RuntimeException("DictionaryTree.maximumBranching not implemented yet");
    }

    /**
     * @return the height of this tree, i.e. the length of the longest branch
     */
    int height() {
        throw new RuntimeException("DictionaryTree.height not implemented yet");
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
        throw new RuntimeException("DictionaryTree.longestWord not implemented yet");
    }

    /**
     * @return all words stored in this tree as a list
     */
    List<String> allWords() {
    	List<String> words = new ArrayList<String>();
        allWordsRecur("", words);
        return words;
    }
    
    void allWordsRecur(String soFar, List<String> words) {
        for (Character c: children.keySet()) {
        	if (c == END_OF_WORD) {
        		words.add(soFar);
        	} else {
            	children.get(c).allWordsRecur(soFar + c, words);
        	}
        }
    }
    

    /**
     * Folds the tree using the given function. Each of this node's
     * children is folded with the same function, and these results
     * are stored in a collection, cResults, say, then the final
     * result is calculated using f.apply(this, cResults).
     *
     * @param f   the summarising function, which is passed the result of invoking the given function
     * @param <A> the type of the folded value
     * @return the result of folding the tree using f
     */
    <A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
        throw new RuntimeException("DictionaryTree.fold not implemented yet");
    }


}
