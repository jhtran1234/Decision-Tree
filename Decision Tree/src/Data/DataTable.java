package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to represent the entire data table, with methods to modify it
 * @author Jeffrey Tran
 */
public class DataTable {
	
	/**
	 * Class to assist in the weighted entropy calculations of the whole attribute
	 */
	private class EntropyCountAttr {
		
		/**
		 * Class to calculate the entropy of a specific attribute's option
		 */
		private class EntropyCount {
			private int trueCount = 0;
			private int falseCount = 0;
			
			private void add(final boolean result) {
				if (result) {
					trueCount++;
				}
				else {
					falseCount++;
				}
			}
			
			/**
			 * @return the entropy of a particular branch
			 */
			private double entropy() {
				final double p1 = ((double) trueCount)/((double) trueCount +
						(double) falseCount);
				final double p2 = ((double) falseCount)/((double) trueCount +
						(double) falseCount);
								
				return (p1 == 0.0 || p2 == 0.0) ? 0.0 : 0.0 -
						p1*Math.log(p1)/Math.log(2) -
						p2*Math.log(p2)/Math.log(2);
			}
			
			private int total() {
				return trueCount + falseCount;
			}
		}

		final HashMap<String, EntropyCount> countResults = new HashMap<>();
		double total = 0.0;
		
		/**
		 * Adds an attribute option and its corresponding result to entropy consideration
		 * @param option
		 * @param result
		 */
		private void add(final String option, final boolean result) {
			if (!countResults.containsKey(option)) {
				countResults.put(option, new EntropyCount());
			}
			countResults.get(option).add(result);
			total += 1.0;
		}
		
		/**
		 * @return weighted entropy of a whole attribute (with all of its options weighted)
		 */
		private double weightedEntropy() {
			double hBar = 0.0;
			for (EntropyCount e : countResults.values()) {
				final double weight = (((double) e.total())/total);
				final double entropy = e.entropy();
				hBar +=  weight * entropy;
			}
			
			return hBar;
		}
	}
	
	public ArrayList<DataRow> table;
	public ArrayList<String> listAttributeNames;
	
	
	public DataTable(final ArrayList<String> listAttributeNames) {
		table = new ArrayList<>();
		this.listAttributeNames = cloneArrayList(listAttributeNames);
	}
	
	public DataTable(final ArrayList<DataRow> table,
			final ArrayList<String> listAttributeNames) {
		this.table = table;
		this.listAttributeNames = cloneArrayList(listAttributeNames);
	}
	
	/**
	 * Inserts a premade DataRow into the table
	 * @param row
	 * @return boolean if successful
	 */
	public boolean insertRow(final DataRow row) {
		return table.add(row);
	}
	
	/**
	 * Inserts a new DataRow into the table
	 * @param listAttributes
	 * @param result
	 * @return boolean if successful
	 */
	public boolean insertRow(final ArrayList<String> listAttributes,
			final boolean result) {
		try {
			return table.add(new DataRow(cloneArrayList(listAttributeNames),
					listAttributes, result));
		} catch (final Exception e) {
			return false;
		}
	}
	
	/**
	 * Chooses next attribute in decision tree based on lowest entropy
	 * @return ArrayList of the chosen attribute's options, with the attribute in index 0
	 */
	public ArrayList<String> chooseAttribute() {
		double minEntr = Double.MAX_VALUE;
		String minAttr = "";
		Set<String> options = new HashSet<>();
		
		for (String attr : listAttributeNames) {
			EntropyCountAttr c = new EntropyCountAttr();
			
			for (DataRow r : table) {
				c.add(r.attributes.get(attr), r.result);
			}
			
			final double weightedEntropy = c.weightedEntropy();
			if(weightedEntropy < minEntr) {
				minEntr = weightedEntropy;
				minAttr = attr;
				options = c.countResults.keySet();
			}
		}
		
		final ArrayList<String> output = new ArrayList<String>();
		output.add(minAttr);
		output.addAll(options);
		return output;
	}
	
	/**
	 * @return boolean to indicate if all elements in the table have the same result
	 * and thus the Final child node can be added with 1.0 certainty
	 */
	public boolean isSameResult() {
		final boolean b = table.get(0).result;
		
		for(DataRow r : table) {
			if(r.result != b) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Removes a specified attribute from consideration in future children
	 * @param attr for the specified attribute
	 */
	public void removeAttribute(final String attr) {
		this.listAttributeNames.remove(attr);
		/*for(DataRow r : table) {
			r.attributes.remove(attr);
		}*/
	}
	
	/**
	 * @param list
	 * @return a deep copy of an ArrayList<String>
	 */
	public static ArrayList<String> cloneArrayList(
			final ArrayList<String> list){
		final ArrayList<String> out = new ArrayList<>();
		for(String s : list) {
			out.add(s);
		}
		return out;
	}
	
	/**
	 * @param attr for the attribute to analyze
	 * @param option for the attribute option to narrow the table down to
	 * @return deep copy of the DataTable that only includes one @option of said @attr
	 */
	public DataTable singleOption(final String attr, final String option) {
		final ArrayList<DataRow> tableClone = new ArrayList<>();
		for (DataRow r : table) {
			if (r.attributes.get(attr).equals(option)) {
				tableClone.add(r.clone());
			}
		}

		return new DataTable(tableClone, cloneArrayList(listAttributeNames));
	}
	
	/**
	 * @return a deep copy of the DataTable
	 */
	public DataTable clone() {
		final ArrayList<DataRow> tableClone = new ArrayList<>();
		for(DataRow r : table) {
			tableClone.add(r.clone());
		}
		
		return new DataTable(tableClone, cloneArrayList(listAttributeNames));
	}
}