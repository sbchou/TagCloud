import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * URL.java
 * @author sophie_chou sbc2125 Dec 11, 2011 
 * This class reads an input url String, parses, and instantiates
 * the WordTag objects out of the text body.
 * Uses the JSoup library to parse and read URL.
 * We use the Java API hashtable to store the tags.
 *
 */
public class URL extends WordTag implements Serializable {
	
	/**
	 * Creates a new URL object, with given url String
	 * @param input the input web address
	 * @throws IOException 
	 */
	public URL(String input) throws IOException{
		super(input);
		URL = input;
		tags = new ArrayList<Tag>();		
		parse();
	}
	
	/**
	 * Reads and parses the input url, removing punctuation and putting everything
	 * to lowercase. Creates tag objects out of the words on the page, setting
	 * appropriate child/parent relations, and also makes a tag out of the url itself.
	 * We store the tags in a hashtable.
	 * Hashtable operations are constant time.
	 * @throws IOException
	 */
	public void parse() throws IOException{
		Document webpage = Jsoup.connect(URL).get();
		String body = webpage.text(); 
		//Remove all non-letters
		body = body.replaceAll("[^\\p{L}]", " ");
		body = body.toLowerCase(); //make everything lowercase
		Scanner input = new Scanner(body);
		WordTag current = new WordTag(input.next().trim());
		while(input.hasNext()){
			WordTag next = new WordTag(input.next().trim());
			current.addChild(next.getWord()); //set the next word as a child of the current word.
			next.addParent(current.getWord()); //set this word as the parent of the next word.
			this.addChild(current.getWord()); //all the words are a child of the URL.
			current.addParent(this.getWord()); //the URL is a parent of all words.
			tags.add(current);
			current = next;	//set the current word to the next one.
		}	
	}

	/**
	 * Returns the ArrayList of tags.
	 * Constant runtime.
	 * @return the tags
	 */
	public ArrayList<Tag> getTags(){
		return tags;
	}
	
	private ArrayList<Tag> tags;
	private String URL;
	private static final long serialVersionUID = 1L;

}







