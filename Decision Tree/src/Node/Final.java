package Node;

public class Final extends TreeNode {
	public boolean state;
	
	public Final(boolean state) {
		super();
		this.state = state;
	}

	public String toString() {
		return Boolean.toString(state);
	}
}
