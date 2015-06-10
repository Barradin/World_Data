/****************************************
 * Adam Tracy                           *
 * B - Tree Assign 5                    *
 * UserApp                              *
 * Reads through the transaction file   *
 * and processes what's read to call    *
 * the appropriate methods              *
 ***************************************/

package worlddata;

import java.io.*;
import java.util.Scanner;

public class UserApp {

	public static void main(String[] args) throws IOException {
		// some variable declarations
		PrintWriter log = new PrintWriter(new BufferedWriter(new FileWriter(
				"Log.txt", true)));
		String fileNameSuffix = args[0];
		String filePath = "A5TransData" + fileNameSuffix + ".txt";
		File file = new File(filePath);
		Scanner cFile = new Scanner(file);
		CodeIndex ci = new CodeIndex(fileNameSuffix);
		ActualData ad = new ActualData(fileNameSuffix);
		short DRP;
		String actualData = "";

		// ****************************************************************
		// making it look nice
		log.printf("=====================================================================%n");
		log.printf("Processing " + filePath + "%n");

		// while the file has data
		while (cFile.hasNext()) {
			String line = cFile.nextLine();
			String temp[] = line.split(" ");
			log.printf(line + "  ==>>   ");

			// switch on transaction
			switch (temp[0]) {
			// if SC, search the B-tree for a match
			case "SC":
				DRP = ci.selectByCode(temp[1]);
				if (DRP != -1) {
					actualData = ad.getActualData(DRP);
					log.printf(actualData + "         " + "[Nodes read: "
							+ ci.getCount() + "]%n");
				} else {
					log.printf("ERROR - Code not in index.      "
							+ "[Nodes read: " + ci.getCount() + "]%n");
				}
				break;
			// else do nothing
			default:
				break;
			}
		}

		// finish up
		log.printf("=====================================================================%n");
		cFile.close();
		log.close();

	}

}
