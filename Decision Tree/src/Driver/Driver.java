package Driver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Data.DataRow;
import Data.DataTable;
import Data.DecisionTree;

/**
 * Driver of the ID3 Decision Tree
 * Updated 12/12/2021 16:50 EST
 * @author Jeffrey Tran
 *
 */
public class Driver {
	public static double entropy(int p, int n) {
		final double p1 = ((double) p)/((double) p + (double) n);
		final double p2 = ((double) n)/((double) p + (double) n);
		return 0.0 - p1*Math.log(p1)/Math.log(2) - p2*Math.log(p2)/Math.log(2);
	}
	
	public static void main(String[] args) throws Exception {
		// read in CSV
		final Scanner scan = 
				new Scanner(new BufferedReader(new FileReader("table1.csv")));
		final ArrayList<String> attributeNames = 
				new ArrayList<>(Arrays.asList(scan.nextLine().split(",")));
		attributeNames.remove(attributeNames.size() - 1);
		final DataTable dataTable = new DataTable(attributeNames);
		
		while(scan.hasNext()) {
			final ArrayList<String> row = 
					new ArrayList<>(Arrays.asList(scan.nextLine().split(",")));
			final boolean result = 
					row.remove(row.size() - 1).equalsIgnoreCase("T") ? 
							true : false;
			final DataRow r = new DataRow(attributeNames, row, result);
			dataTable.insertRow(r);
		}
		
		final DecisionTree tree = new DecisionTree(dataTable);
		System.out.println();
	}
}