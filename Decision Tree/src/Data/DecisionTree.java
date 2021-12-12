package Data;
import Data.DataTable;
import Node.TreeNode;

/**
 * Class to hold the DecisionTree
 * @author Jeffrey Tran
 *
 */
public class DecisionTree {
	public TreeNode root;
	
	public DecisionTree(DataTable t) {
		root = new TreeNode(t.chooseAttribute().get(0), t);
		root.makeChildren();
	}
}