package Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataTable {
	private class EntropyCountAttr {
		private class EntropyCount {
			private int trueCount = 0;
			private int falseCount = 0;
			
			private void add(boolean b) {
				if(b) {
					trueCount++;
				}
				else {
					falseCount++;
				}
			}
			
			private double entropy() {
				double p1 = ((double) trueCount)/((double) trueCount + (double) falseCount);
				double p2 = ((double) falseCount)/((double) trueCount + (double) falseCount);
				
				if(p1 == 0.0 || p2 == 0.0) {
					return 0;
				}
				
				return 0.0 - p1*Math.log(p1)/Math.log(2) - p2*Math.log(p2)/Math.log(2);
			}
			
			private int total() {
				return trueCount + falseCount;
			}
		}

		HashMap<String, EntropyCount> countResults = new HashMap<>();
		double total = 0.0;
		
		private void add(String choice, boolean b) {
			if(!countResults.containsKey(choice)) {
				countResults.put(choice, new EntropyCount());
			}
			countResults.get(choice).add(b);
			total += 1.0;
		}
		
		private double weightedEntropy() {
			double hBar = 0.0;
			for(EntropyCount e : countResults.values()) {
				double weight = (((double) e.total())/total);
				double entropy = e.entropy();
				hBar +=  weight * entropy;
			}
			
			return hBar;
		}
	}
	
	public ArrayList<DataRow> table;
	public ArrayList<String> listAttributeNames;
	
	
	public DataTable(ArrayList<String> listAttributeNames) {
		table = new ArrayList<>();
		this.listAttributeNames = cloneArrayList(listAttributeNames);
	}
	
	public DataTable(ArrayList<DataRow> table, ArrayList<String> listAttributeNames) {
		this.table = table;
		this.listAttributeNames = cloneArrayList(listAttributeNames);
	}
	
	public boolean insertRow(DataRow r) {
		return table.add(r);
	}
	
	public boolean insertRow(ArrayList<String> listAttributes, boolean result) {
		try {
			return table.add(new DataRow(cloneArrayList(listAttributeNames), listAttributes, result));
		} catch (Exception e) {
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
		
		for(String attr : listAttributeNames) {
			EntropyCountAttr c = new EntropyCountAttr();
			
			for(DataRow r : table) {
				c.add(r.attributes.get(attr), r.result);
			}
			
			if(c.weightedEntropy() < minEntr) {
				minEntr = c.weightedEntropy();
				minAttr = attr;
				options = c.countResults.keySet();
			}
		}
		
		ArrayList<String> output = new ArrayList<String>();
		output.add(minAttr);
		output.addAll(options);
		return output;
	}
	
	public boolean isSameResult() {
		boolean b = table.get(0).result;
		
		for(DataRow r : table) {
			if(r.result != b) {
				return false;
			}
		}
		
		return true;
	}
	
	public void removeAttribute(String attr) {
		this.listAttributeNames.remove(attr);
		for(DataRow r : table) {
			r.attributes.remove(attr);
		}
	}
	
	public static ArrayList<String> cloneArrayList(ArrayList<String> list){
		ArrayList<String> out = new ArrayList<>();
		for(String s : list) {
			out.add(s);
		}
		return out;
	}
	
	public DataTable singleOption(String attr, String option) {
		ArrayList<DataRow> tableClone = new ArrayList<>();
		for(DataRow r : table) {
			if(r.attributes.get(attr).equals(option)) {
				tableClone.add(r.clone());
			}
		}

		return new DataTable(tableClone, cloneArrayList(listAttributeNames));
	}
	
	public DataTable clone() {
		ArrayList<DataRow> tableClone = new ArrayList<>();
		for(DataRow r : table) {
			tableClone.add(r.clone());
		}
		
		return new DataTable(tableClone, cloneArrayList(listAttributeNames));
	}
}
