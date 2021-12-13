package Node;
import java.util.HashMap;
import java.util.HashSet;

import Data.DataRow;
import Data.DataTable;

/**
 * Class to represent a node of the DecisionTree
 * @author Jeffrey Tran
 */
public class TreeNode {
	public String attribute;
	public HashMap<String, TreeNode> children;
	public DataTable table;

	public TreeNode() {	
		attribute = null;
		children = null;
		table = null;
	}
	
	public TreeNode(String attribute, DataTable t) {
		this.attribute = attribute;
		children = new HashMap<>();
		table = t.clone();
	}

	/**
	 * @return HashSet<String> of all the attribute's options in the table
	 */
	public HashSet<String> findOptions() {
		HashSet<String> options = new HashSet<String>();

		for(DataRow r : table.table) {
			options.add(r.attributes.get(attribute));
		}

		return options;
	}

	/**
	 * Recursive method to constantly create new children until base Final nodes can be placed
	 */
	public void makeChildren() {
		HashSet<String> options = findOptions();

		// for each option of the attribute, find next best entropy child and build
		for(String option : options) {
			DataTable t = table.singleOption(attribute, option);
			t.removeAttribute(attribute);

			// check to see if all true or all false			
			if(t.table.size() <= 0) {
				System.out.println("Error! Table empty");
				System.exit(0);
			}
			else if (t.isSameResult()) {
				children.put(option, new Final(t.table.get(0).result));
			}
			else if(t.listAttributeNames.size() > 0) {
				TreeNode node = new TreeNode(t.chooseAttribute().get(0), t);
				children.put(option, node);

				node.makeChildren();
			}
			else {
				double trueCount = 0;
				double falseCount = 0;
				for(DataRow r : t.table) {
					if(r.result) {
						trueCount ++;
					}
					else {
						falseCount ++;
					}
				}

				TreeNode s = (trueCount >= falseCount) ? children.put(option, new Final(true, trueCount / (trueCount+falseCount))) : children.put(option, new Final(false, falseCount / (trueCount+falseCount)));
			}
		}
	}
}