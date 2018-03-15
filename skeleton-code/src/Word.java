
public class Word implements Comparable<Word>{
	
	String word;
	int popularity;
	
	public Word(String word, int popularity) {
		this.word = word;
		this.popularity = popularity;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getPopularity() {
		return popularity;
	}

	@Override
	public int compareTo(Word w) {
		return Integer.compare(w.getPopularity(), popularity);
	}
	
}
