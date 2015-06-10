/****************************************
 * Adam Tracy                           *
 * B - Tree Assign 5                    *
 * Actual Data                          *
 * Pulls the relevant information from  *
 * the fake actual data files and       *
 * returns it to the userApp            *
 ***************************************/

package worlddata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ActualData {
	// declare some stuff
	private String filePath;
	private static RandomAccessFile bReader;
	private int SizeOfRec = 25;
	private String temp;

	// ****************************************************************
	/**
	 * constructor for Actual Data (opens binary reader)
	 * 
	 * @param fileNameSuffix
	 * @throws FileNotFoundException
	 */
	public ActualData(String fileNameSuffix) throws FileNotFoundException {
		filePath = "FakeActualData" + fileNameSuffix + ".txt";
		bReader = new RandomAccessFile(new File(filePath), "r");
	}

	// ****************************************************************
	/**
	 * goes DIRECTLY to the information in actualData and returns it to userapp
	 * 
	 * @param DRP
	 * @return
	 * @throws IOException
	 */
	public String getActualData(short DRP) throws IOException {
		int offSet = ((DRP - 1) * SizeOfRec);
		bReader.seek(offSet);
		temp = bReader.readLine();
		return temp;

	}

}
