//package other;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.BiFunction;
//
//import DictionaryTree;
//
//public class DictionaryWord implements DictionaryNode {
//
//	@Override
//	public void insert(String word) {
//		
//	}
//
//	@Override
//	public void insert(String word, int popularity) {
//		
//	}
//
//	@Override
//	public boolean remove(String word) {
//		return false;
//	}
//
//	@Override
//	public boolean contains(String word) {
//		return false;
//	}
//
//	@Override
//	public Optional<String> predict(String prefix) {
//		return Optional.empty();
//	}
//
//	@Override
//	public List<String> predict(String prefix, int n) {
//		return new ArrayList<String>();
//	}
//
//	@Override
//	public int numLeaves() {
//		return 0;
//	}
//
//	@Override
//	public int maximumBranching() {
//		return 0;
//	}
//
//	@Override
//	public int height() {
//		return 0;
//	}
//
//	@Override
//	public int size() {
//		return 1;
//	}
//
//	@Override
//	public Map<Character, DictionaryTree> getChildren() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String longestWord() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<String> allWords() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void allWordsRecur(String soFar, List<String> words) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//}
