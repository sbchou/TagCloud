import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
/**
 * TagCloud.java
 * @author sophie_chou sbc2125 Dec 11, 2011 
 * This class creates a cloud of tags, represented by a 
 * directed graph (uses the Jung Library). Supports
 * multiple URL entries, and draws the relation links.
 * Also, removes duplicates etc.
 */

public class TagCloud implements Serializable {
	
	/**
	 * Creates a new cloud.
	 * HashTable performs at constant time for additions; 
	 * total time depends on number of words and urls 
	 * @param URLs the input ArrayList of URLs
	 */
	public TagCloud(ArrayList<URL> URLs){ 
		this.URLs = URLs;
		cloud = new Hashtable<String, Tag>();
		graph = new DirectedSparseGraph<Tag, Integer>();
		inGraph = new Hashtable<String, Tag>();				
		for(URL url : URLs){
			this.hash(url);	
			ArrayList<Tag> tags = url.getTags();
			for(Tag tag : tags){
				this.hash(tag);
			}			
		}
		System.out.println("HashTable");
		System.out.println(cloud);
	}
		
	/**
	 * Adds a tag to the hashtable of words.
	 * If there is a duplicate, increments the count.
	 * O(1)
	 * @param tag the tag to be hashed to the table
	 */
	public void hash(Tag tag){
		String name = tag.getWord();
		if(cloud.containsKey(name)){
			cloud.get(name).increment();
		}
		else{
			cloud.put(name, tag);
		}		
	}

	/**
	 * Creates and draws a new graph of the cloud, using
	 * the Jung library.
	 * O(n) operation, where n is the number of tags, since
	 * each operation is constant time.
	 */
	public void graph(){
		graph = new DirectedSparseGraph<Tag, Integer>(); //new graph
		inGraph = new Hashtable<String, Tag>();   //new tracker
		//always add the URLs to the graph
		for(URL url: URLs){
			graph.addVertex(url);
			inGraph.put(url.getWord(), url);
		}		
		Enumeration<Tag> e = cloud.elements();
		while(e.hasMoreElements()){
			Tag tag = e.nextElement();
			graph.addVertex(tag);
			inGraph.put(tag.getWord(), tag);
		}
		
		Enumeration<Tag> t = inGraph.elements();
		int i = 0;
		while(t.hasMoreElements()){
			Tag current = t.nextElement();
			for(String child: current.getChildren()){
				if(inGraph.containsKey(child)){
					graph.addEdge((Integer)i, current, inGraph.get(child));
					i++;
				}
			}
		}
	}
	
	/**
	 * Filters out the top n tags and graphs them.
	 * Does so by putting all tags in a heap (PriorityQueue)
	 * and taking off the top n tags.
	 * Java API PriorityQueue takes linear time for remove() method; O(logn) 
	 * for add() method. Hashtable takes constant time.
	 * Therefore, total operation takes O(n).
	 * @param n the number of tags to filter
	 */
	public void filter(int n){
		
		graph = new DirectedSparseGraph<Tag, Integer>();//new graph
		inGraph = new Hashtable<String, Tag>();  //new tracker
		//put all tags in a heap sorted by frequency
		PriorityQueue<Tag> heap  = new PriorityQueue<Tag>(cloud.size());
		Enumeration<Tag> e = cloud.elements();
		//put all the Tags in the heap
		while(e.hasMoreElements()){
			heap.add(e.nextElement());		
		}
				
		//always add the URLs to the graph
		for(URL url: URLs){
			graph.addVertex(url);
			inGraph.put(url.getWord(), url);
		}		
		for(int i=1; i<=n; i++){
			Tag tag = heap.remove();
			graph.addVertex(tag);
			inGraph.put(tag.getWord(), tag);
		}
		
		Enumeration<Tag> t = inGraph.elements();
		int i = 0;
		while(t.hasMoreElements()){
			Tag x = t.nextElement();
			for(String child: x.getChildren()){
				if(inGraph.containsKey(child)){
					graph.addEdge((Integer)i, x, inGraph.get(child));
					i++;
				}
			}
		}
	}
	

	/**
	 * Searches for given term and prints out its parents, children, 
	 * and the URL it was found on.
	 * @param term the search term
	 * @return results, a String description of results
	 * Constant run time for hashtable operations 
	 */
	public String search(String term){
		String results = "";
		if(inGraph.containsKey(term)){
			Tag word = inGraph.get(term);
			results += "Searched: " + "\n" + term;
			results += " \n + Child terms: ";
			ArrayList<String> children = word.getChildren();
			for(String child: children){
				if(inGraph.containsKey(child))
					results += "\n" + child;
			}
			results += "\n Parent terms: ";
			ArrayList<String> parents = word.getParents();
			for(String parent: parents){
				if(inGraph.containsKey(parent))
					results += "\n" + parent;
			}
		}
		return results;
	}
	
	/**
	 * Draws the current graph using the Jung library.
	 */
	public void draw(){
		// Layout<V, E>, VisualizationComponent<V,E>
			System.out.println("draw()");
	        Layout<String, String> layout = new CircleLayout(graph);
	        layout.setSize(new Dimension(500,500));
	        VisualizationViewer<String,String> vv = new VisualizationViewer<String,String>(layout);
	        vv.setPreferredSize(new Dimension(600,600));
	        // Show vertex and edge labels
	        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	        // Create a graph mouse and add it to the visualization component
	        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
	        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	        vv.setGraphMouse(gm); 
	        String title = "";
	        for(URL url:URLs){
	        	title += url.getWord() + " ";
	        }
	        JFrame frame = new JFrame(title);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().add(vv);
	        frame.pack();
	        frame.setVisible(true);  
	       
	}
	
	
	private Hashtable<String, Tag> cloud; //of everything
	private ArrayList<URL> URLs;
	private Graph<Tag, Integer> graph; //updated for ea. drawing
	private Hashtable<String, Tag> inGraph; //updated for ea. drawing
	private static final long serialVersionUID = 1L;
}











