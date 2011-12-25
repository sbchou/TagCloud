TagCloud.java
CS W3137 Data Structures
Sophie Chou sbc2125 12/19/11

User instructions:
Run CloudTester.java
A new Tag Cloud Menu will pop up.
Enter the URLs you would like to parse in the first box, one at a time, submitting separately. (If you enter multiple URLs before entering frequency filter # and the Graph! button, it will parse multiple URLs.)
Enter the number of vertices in the second box (by frequency  count). Press Graph!
To search a word, enter the term in the third box. It will print the results in the scrolling box at the bottom.
To upload a Graph, enter the name in the "Name of graph" window and press Upload Graph.
To save a current graph, enter the name you would like to use in "Save file as", and press save.
That's it!

PROGRAM FILES:
Tag.java
Interface for tag objects (WordTag, URL).
Contains the methods addChild, addParent, increment, getFrequency, getChildren, getParents, getWord, and equals.
Implemented in the WordTag and URL classes.

WordTag.java
This class represents a word tag; implements Tag, Comparable<Tag>, Serializable.
The method addChild adds a child to the ArrayList of children. A child is the following word in the phrase. Has runtime O(1), to append word to end of ArrayList.
The method addParent adds a parent to the ArrayList of parents. A parent is the preceding word in the phrase. Has runtime O(1), to append word to end of ArrayList.
The method increment increases the frequency count. getFrequency, getParents, getWord and getChildren return the respective fields (constant time).
The toString returns the word and frequency.
The method equals returns true if this tag equals the other,aka they represent the same word.Constant runtime for comparison.
The method compareTo overrides the API method, has constant time.

URL.java
This class reads an input url String, parses, and instantiates the WordTag objects out of the text body.Uses the JSoup library to parse and read URL.
We use the Java API hashtable to store the tags.
Parse() reads and parses the input url, removing punctuation and putting everything to lowercase. Creates tag objects out of the words on the page, setting appropriate child/parent relations, and also makes a tag out of the url itself.We store the tags in a hashtable.Hashtable operations are constant time. getTags returns the ArrayList of tags, constant time operation.

TagCloud.java
This class creates a cloud of tags, represented by a 
directed graph (uses the Jung Library). Supports
multiple URL entries, and draws the relation links.
Also, removes duplicates etc. 
Constructor creates a new cloud. HashTable performs at constant time for additions;total time depends on number of words and urls.
hash method adds a tag to the hashtable of words.
If there is a duplicate, increments the count. O(1) runtime for hash table operations.
graph method creates and draws a new graph of the cloud, using the Jung library. O(n) operation, where n is the number of tags, since each operation is constant time. Filter method filters out the top n tags and graphs them. It does so by putting all tags in a heap (PriorityQueue) and taking off the top n tags. Java API PriorityQueue takes linear time for remove() method; O(logn) for add() method. Hashtable takes constant time.Therefore, total operation takes O(n). The search method searches for given term and prints out its parents, children, and the URL it was found on. Constant run time for hashtable operations. The draw method draws the current graph using the Jung library.

TagCloudConsole.java
Creates the user interface for the Tag Cloud program.
The program features a window for entering commands, and separate pop-up windows for the graph (creates
a new pop up after each command that alters the graph). Constructor creates a new console, and run() runs the menu using Java Swing library for GUI.

CloudTester.java
Runs and tests the Cloud Tag program.


















