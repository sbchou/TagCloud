import java.io.IOException;

/**
 * Tests the Cloud Tag program.
 * @author sophie
 *
 */
public class CloudTester {

	/**
	 * Creates and runs a new menu.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		TagCloudConsole myConsole = new TagCloudConsole();
		myConsole.run();
	}

}
