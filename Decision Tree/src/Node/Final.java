package Node;

public class Final extends TreeNode {
	public boolean state;
	public double certainty;
	
	public Final(boolean state) {
		super();
		this.state = state;
		certainty = 1.0;
	}
	
	public Final(boolean state, double certainty) {
		this.state = state;
		this.certainty = certainty;
	}

	public String toString() {
		return Boolean.toString(state) + " with " + certainty + " certainty.";
	}
}
