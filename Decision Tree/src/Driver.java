import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Node.DataRow;
import Node.DataTable;

/**
 * Driver of the Decision Tree
 * Updated 12/12/2021 16:40 EST
 * @author Jeffrey Tran
 *
 */
public class Driver {
	public static double entropy(int p, int n) {
		double p1 = ((double) p)/((double) p + (double) n);
		double p2 = ((double) n)/((double) p + (double) n);
		return 0.0 - p1*Math.log(p1)/Math.log(2) - p2*Math.log(p2)/Math.log(2);
	}
	
	public static void main(String[] args) throws Exception {
		// read in CSV
		Scanner scan = new Scanner(new BufferedReader(new FileReader("C:\\Users\\jhtra\\Downloads\\table1.csv")));
		ArrayList<String> attributeNames = new ArrayList<String>(Arrays.asList(scan.nextLine().split(",")));
		attributeNames.remove(attributeNames.size() - 1);
		DataTable dataTable = new DataTable(attributeNames);
		
		while(scan.hasNext()) {
			ArrayList<String> row = new ArrayList<String>(Arrays.asList(scan.nextLine().split(",")));
			boolean result = row.remove(row.size() - 1).equalsIgnoreCase("T") ? true : false;
			DataRow r = new DataRow(attributeNames, row, result);
			dataTable.insertRow(r);
		}
		
		DecisionTree tree = new DecisionTree(dataTable);
		System.out.println();
	}
}
