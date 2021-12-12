package Node;
import java.util.HashMap;
import java.util.HashSet;

public class TreeNode {
	public String attribute;
	public HashMap<String, TreeNode> children;
	public DataTable table;
	
	public TreeNode(String attribute, DataTable t) {
		this.attribute = attribute;
		children = new HashMap<>();
		table = t.clone();
	}
	
	public TreeNode() {
		// null set
	}

	public HashSet<String> findOptions() {
		HashSet<String> options = new HashSet<String>();
		
		for(DataRow r : table.table) {
			options.add(r.attributes.get(attribute));
		}
		
		return options;
	}
	
	public void makeChildren() {
		HashSet<String> options = findOptions();
		
		// for each option of the attribute, find next best entropy child and build
		// what happens if the table has no more options?
		for(String option : options) {
			DataTable t = table.singleOption(attribute, option);
			t.removeAttribute(attribute);
			
			// check to see if all true or all false			
			if(t.table.size() > 0 && t.isSameResult()) {
				children.put(option, new Final(t.table.get(0).result));
			}
			else if(t.listAttributeNames.size() > 1) {
				TreeNode node = new TreeNode(t.chooseAttribute().get(0), t);
				children.put(option, node);
				
				node.makeChildren();
			}
			else {
				System.out.println("Error!");
				System.exit(0);
			}
		}
	}
}