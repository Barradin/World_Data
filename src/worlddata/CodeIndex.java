/****************************************
 * Adam Tracy                           *
 * B - Tree Assign 5                    *
 * Code Index                           *
 * Searches through the BIN file to     *
 * create a big node, which searches    *
 * through the big node to look for a   *
 * potential match                      *
 ***************************************/

package worlddata;

import java.io.*;

public class CodeIndex {

	// declare variables
	private String filePath;
	private int m;
	private static RandomAccessFile bReader;
	private final int HEADER_REC = 6;
	private int NODE_SIZE;
	private short[] tpArr;
	private String[] kvArr;
	private short[] drpArr;
	private String temp = "";
	private short tempTP;
	private short tempDRP;
	private boolean found;
	private int count;

	// ****************************************************************
	/**
	 * code index constructor, builds arrays dynamically
	 * 
	 * @param fileNameSuffix
	 * @throws IOException
	 */
	public CodeIndex(String fileNameSuffix) throws IOException {

		filePath = "CodeIndex" + fileNameSuffix + ".bin";
		bReader = new RandomAccessFile(new File(filePath), "r");
		bReader.seek(0);
		m = bReader.readByte();
		NODE_SIZE = 2 * m + (3 * (m - 1)) + (2 * (m - 1));
		tpArr = new short[m];
		kvArr = new String[m - 1];
		drpArr = new short[m - 1];

	}

	// ****************************************************************
	/**
	 * method called when SC passed in userApp to select by code
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public short selectByCode(String code) throws IOException {
		count = 0;
		bReader.seek(2);
		readOneNode(swap(bReader.readShort()), NODE_SIZE, HEADER_REC);
		tempTP = searchOneNode(code);
		count++;
		while (getFound() == false) {
			readOneNode(tempTP, NODE_SIZE, HEADER_REC);
			tempTP = searchOneNode(code);
			count++;
		}
		if (tempTP == 0) {
			tempDRP = getDRP();
		} else {
			tempDRP = -1;
		}
		return tempDRP;
	}

	/**
	 * read in one node to the parallel arrays. JUST ONE
	 * 
	 * @param RRN
	 * @param NODE_S
	 * @param HEADER_SIZE
	 * @throws IOException
	 */
	private void readOneNode(int RRN, int NODE_S, int HEADER_SIZE)
			throws IOException {
		int offSet = ((RRN - 1) * NODE_S) + HEADER_SIZE;
		bReader.seek(offSet);
		for (int i = 0; i < m; i++) {
			tpArr[i] = swap(bReader.readShort());
		}
		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < 3; j++) {
				temp += (char) bReader.readByte();
			}
			kvArr[i] = temp;
			temp = "";
		}
		for (int i = 0; i < m - 1; i++) {
			drpArr[i] = swap(bReader.readShort());
		}
	}

	/**
	 * swap endians if needed
	 * 
	 * @param value
	 * @return
	 */
	private static short swap(short value) {
		int b1 = value & 0xff;
		int b2 = (value >> 8) & 0xff;

		return (short) (b1 << 8 | b2 << 0);
	}

	/**
	 * search the ONE NODE for a match. if found return flags if match or return
	 * other flags if no match
	 * 
	 * @param code
	 * @return
	 */
	private short searchOneNode(String code) {
		int i = 0;
		found = false;
		do {
			if (kvArr[i].equalsIgnoreCase(code)) {
				found = true;
				tempDRP = drpArr[i];
			} else if (code.compareTo(kvArr[i]) < 0) {
				if (tpArr[i] != -1) {
					return tpArr[i];
				}
				if (tpArr[i] == -1) {
					found = true;
					return 1;
				}
			} else if (code.compareTo(kvArr[i]) > 0) {
				i++;
				if (i == kvArr.length) {
					if (tpArr[i] != -1) {
						return tpArr[i];
					} else if (tpArr[i] == -1) {
						found = true;
						return 1;
					}
				}
			}

		} while (found == false);
		return 0;
	}

	/**
	 * getter for found flag
	 * 
	 * @return
	 */
	private boolean getFound() {
		return found;
	}

	/**
	 * getter for DRP
	 * 
	 * @return
	 */
	private short getDRP() {
		return tempDRP;
	}

	/**
	 * getter for count
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}

}
