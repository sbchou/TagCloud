import java.io.Serializable;
import java.util.ArrayList;

/**
 * WordTag.java
 * @author sophie December 2011
 * This class represents a word tag.
 *
 */
public class WordTag implements Tag, Comparable<Tag>, Serializable{
	
	/**
	 * Creates a new WordTag out of given word
	 * @param word the word, as a String
	 */
	public WordTag(String word){
		this.word = word;
		frequency = 1;
		children = new ArrayList<String>();
		parents = new ArrayList<String>();
	}
	
	/**
	 * Adds a child to the ArrayList of children
	 * A child is the following word in the phrase
	 * Has runtime O(1), to append word to end of ArrayList.
	 * @param childsName the name of the following tag
	 */
	public void addChild(String childsName){
		children.add(childsName);
	}
	
	/**
	 * Adds a parent to the ArrayList of parents
	 * A parent is the preceding word in the phrase
	 * Has runtime O(1), to append word to end of ArrayList.
	 * @param parentsName the name of the following tag
	 */
	public void addParent(String parentsName){
		parents.add(parentsName);
	}

	/**
	 * increments the count of the tag
	 * Constant runtime
	 */
	public void increment(){
		frequency++;
	}
	

	/**
	 * Gets the frequency of the tag
	 * Runtime constant.
	 * @return the count of the tag
	 */
	public int getFrequency(){
		return frequency;
	}

	/**
	 * Gets the parents (preceding words)
	 * Constant runtime.
	 * @return the Arraylist of parent tags
	 */
	public ArrayList<String> getParents(){
		return parents;
	}
	
	/**
	 * Gets the children (related words)
	 * Constant runtime.
	 * @return the ArrayList of child tags
	 */
	public ArrayList<String> getChildren(){
		return children;
	}
	
	public String toString(){
		return word + "(" + frequency + ")";		
	}
	
	/**
	 * Gets the word stored in the Tag object
	 * Constant runtime.
	 * @return the word in the Tag
	 */
	public String getWord(){
		return word;
	}
	
	/**
	 * Returns true if this tag equals the other,
	 * aka they represent the same word.
	 * @param other the tag this one is compared to
	 * @return true or false if they are equal or not
	 * Constant runtime for comparison
	 */
	public boolean equals(Tag other){
		if(this.getWord().equals(other.getWord()))
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * Constant runtime
	 */
	@Override
	public int compareTo(Tag other) {
		if(this.getFrequency() > other.getFrequency()){
			return -1; 
		}
		if(this.getFrequency() < other.getFrequency()){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	private String word;
	private int frequency;
	private ArrayList<String> children;
	private ArrayList<String> parents;
	private static final long serialVersionUID = 1L;
	
}
	