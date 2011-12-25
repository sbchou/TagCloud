import java.util.ArrayList;

/**
 * Tag.java
 * @author sophie_chou sbc2125 Dec 11, 2011 
 * Interface for tag objects (WordTag, URL)
 */
public interface Tag {
	
	/**
	 * Adds a child to the ArrayList of children
	 * A child is the following word in the phrase
	 * @param childsName the name of the following tag
	 */
	public void addChild(String childsName);
	
	/**
	 * Adds a parent to the ArrayList of parents
	 * A parent is the preceding word in the phrase
	 * @param parentsName the name of the following tag
	 */
	public void addParent(String parentsName);
	
	/**
	 * increments the count of the tag
	 */
	public void increment();
	
	/**
	 * Gets the frequency of the tag
	 * @return the count of the tag
	 */
	public int getFrequency();
	
	/**
	 * Gets the children (related words)
	 * @return the ArrayList of child tags
	 */
	public ArrayList<String> getChildren();
	
	/**
	 * Gets the parents (preceding words)
	 * @return the Arraylist of parent tags
	 */
	public ArrayList<String> getParents();
	
	/**
	 * Gets the word stored in the Tag object
	 * @return the word in the Tag
	 */
	public String getWord();
	
	/**
	 * Returns true if this tag equals the other 
	 * @param other the tag this one is compared to
	 * @return true or false if they are equal or not
	 */
	public boolean equals(Tag other);
	
}
