import Node.DataTable;
import Node.TreeNode;

public class DecisionTree {
	public TreeNode root;
	
	public DecisionTree(DataTable t) {
		root = new TreeNode(t.chooseAttribute().get(0), t);
		root.makeChildren();
	}
	
	
}
