/****************************************
 * Adam Tracy                           *
 * B - Tree Assign 5                    *
 * Test Driver                          *
 * Deletes existing files and runs a new*
 * instance of the program              *
 ***************************************/

package worlddata;

import java.io.*;

public class TestDriver {

	public static void main(String[] args) throws IOException {
		//getting ready to run the program
		String[] fileNameSuffix = new String[] { "1", "2", "3" };
		File log = new File("Log.txt");
		if (log.exists()) {
			log.delete();
		}

		//loop through the various files
		for (int i = 0; i < 3; i++) {
			UserApp.main(new String[] { fileNameSuffix[i] });
		}
	}

}
