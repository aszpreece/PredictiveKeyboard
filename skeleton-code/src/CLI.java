import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Kelsey McKenna
 */
public class CLI {

    /**
     * Loads words (lines) from the given file and inserts them into
     * a dictionary.
     *
     * @param f the file from which the words will be loaded
     * @return the dictionary with the words loaded from the given file
     * @throws IOException if there was a problem opening/reading from the file
     */
    static DictionaryTree loadWords(File f) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String word;
            DictionaryTree d = new DictionaryTree();
       
            List<String> words = new ArrayList<String>();
            int total = 0;
            while ((word = reader.readLine()) != null) {
                words.add(word);
                total++;
            }
            int i = 0;
            for (String w : words) {
            	d.insert(w, total - i);
            	i++;
            }
            
            return d;
        }
    }

    public static void main(String[] args) throws IOException {
    	
        System.out.print("Loading dictionary ... ");
        DictionaryTree d = loadWords(new File(args[0]));
        System.out.println("done");
    	System.out.println("Enter prefixes for prediction below.");
     	
        try (BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in))) {
        	System.out.println(d.size());
        	System.out.println(d.numLeaves());
        	System.out.println(d.height());
        	System.out.println(d.maximumBranching());
        	System.out.println(d.longestWord().length());
            for (int i = 0; i < 210; i++) {
            	Optional<String> out = d.predict(fromUser.readLine());
            	System.out.println(out.isPresent() ? out.get() : "No prediction");
            }
        }  
    }

}
