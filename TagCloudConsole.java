import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;

/**
 * Creates the user interface for the Tag Cloud program.
 * The program features a window for entering 
 * commands, and separate pop-up windows for the graph (creates
 * a new pop up after each command that alters the graph).
 * @author sophie_chou sbc2125 Dec 12, 2011 
 *
 */
public class TagCloudConsole {
	
	/**
	 * Creates a new console object.
	 */
	public TagCloudConsole(){
		URLs = new ArrayList<URL>();
	}
	
	/**
	 * Runs the menu.
	 * @throws IOException
	 */
	public void run() throws IOException {
	
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        //create components
        //input URL field 
        final JLabel urlLabel = new JLabel();
        urlLabel.setText("Enter URL");
        final JTextField urlInputField = new JTextField(WIDTH-10);
        
        //input Filter  
        final JLabel filterLabel = new JLabel();
        filterLabel.setText("Enter frequency filter #");
        final JTextField filterInputField = new JTextField(WIDTH-10);
      
        //search 
        final JLabel searchLabel = new JLabel();
        searchLabel.setText("Enter search term");
        final JLabel searchResultsLabel = new JLabel();
        searchResultsLabel.setText("Search results");
        
        final JTextField searchInputField = new JTextField(WIDTH-10);
        final JTextArea searchList = new JTextArea(WIDTH-10, HEIGHT/4);
        JScrollPane outputSearch = new JScrollPane(searchList);
        searchList.setEditable(false);
        outputSearch.setBackground(Color.LIGHT_GRAY);
        searchList.setSelectedTextColor(Color.GRAY);
        
      //load graphs
        final JLabel loadLabel = new JLabel();
        loadLabel.setText("Name of graph");
        final JTextField uploadTextField = new JTextField(WIDTH-10);
       
      //save graphs
        final JLabel saveLabel = new JLabel();
        saveLabel.setText("Save file as");
        final JTextField saveTextField = new JTextField(WIDTH-10);

        //Button to add URLs
       JButton URLButton = new JButton("Submit URL (repeat for multiple)");
        URLButton.addActionListener(new
        ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               String url = urlInputField.getText();
               
               URL myURL;
			try {
				myURL = new URL(url);
				URLs.add(myURL);
	            urlInputField.setText(" ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
         });
        

        //Button to graph
        JButton graphButton = new JButton("Graph!");
        graphButton.addActionListener(new
                ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
        	      myCloud = new TagCloud(URLs);
        	   if(!filterInputField.getText().isEmpty()){
	        	   int filter = Integer.parseInt(filterInputField.getText());	
            	  myCloud.filter(filter);	
            	  myCloud.draw();
        	   }
              else{
            	  myCloud.graph();
            	  myCloud.draw();
              }
           }
        });
        
        //Button to search
        JButton searchButton = new JButton("Enter search term");
        searchButton.addActionListener(new
                ActionListener()
        {
        	public void actionPerformed(ActionEvent event)
            {
               String term = searchInputField.getText();
               String results = myCloud.search(term);
               searchList.append(results + "\n" );
         
            }
         });
        
      //Button to upload
        JButton uploadButton = new JButton("Upload Graph");
        uploadButton.addActionListener(new
                ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
        	   if(!uploadTextField.getText().isEmpty()){
           		
	        	   try {
	        		 String fileName = uploadTextField.getText();
	       			FileInputStream fis = new FileInputStream(fileName);
	       			ObjectInputStream ois = new ObjectInputStream(fis);
	       			myCloud = (TagCloud)ois.readObject();
	       			ois.close();
	       			myCloud.draw();
	       			} catch (Exception e) {
	       				System.out.println(e);
	       				System.exit(0);
	       			}
        	   }
           }
        });
        
        //Button to save
        JButton saveButton = new JButton("Save Graph");
        saveButton.addActionListener(new
                ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
        	   if(!saveTextField.getText().isEmpty()){
	        	   try {
	        		   System.out.println("Entered try statement");
	        		   String fileName = saveTextField.getText();
		       			FileOutputStream fos = new FileOutputStream(fileName);
		       			ObjectOutputStream oos = new ObjectOutputStream(fos);
		       			oos.writeObject(myCloud);
		       			oos.close();
		       			saveTextField.setText("");
	       		} catch (Exception e) {
		       			System.out.println(e);
		       			System.exit(0);
	       		}
        	   }
           }
        });
   

        
         JPanel panel=new JPanel(new GridLayout(6,3));
 
         panel.add(urlLabel);
         panel.add(urlInputField);
     	  panel.add(URLButton);
		  panel.add(filterLabel);
		  panel.add(filterInputField);
		  panel.add(graphButton);
		  panel.add(searchLabel);
		  panel.add(searchInputField);
		  panel.add(searchButton);
		  
		  panel.add(loadLabel);
		  panel.add(uploadTextField);
		  panel.add(uploadButton);
		  panel.add(saveLabel);
		  panel.add(saveTextField);
		  panel.add(saveButton);
		  
		  panel.add(searchResultsLabel);
		  panel.add(outputSearch);
		  
		  frame.add(panel,BorderLayout.CENTER);
		  frame.setTitle("Tag Cloud Menu");
	
            
          frame.setSize(WIDTH, HEIGHT);
          frame.setVisible(true);

    }
		

    final int WIDTH = 1000;
    final int HEIGHT = 300;
    private ArrayList<URL> URLs;
    private TagCloud myCloud;

    
} 





