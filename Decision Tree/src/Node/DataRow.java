package Node;

import java.util.ArrayList;
import java.util.HashMap;

public class DataRow {
	public HashMap<String, String> attributes;
	public boolean result;
	
	public DataRow(ArrayList<String> listAttributeNames, ArrayList<String> listAttributes, boolean result) {
		attributes = new HashMap<>();
		
		for(int i = 0; i < listAttributes.size(); i ++) {
			attributes.put(listAttributeNames.get(i), listAttributes.get(i));
		}
		this.result = result;
	}
	
	public DataRow(HashMap<String, String> attributes, boolean result) {
		this.attributes = attributes;
		this.result = result;
	}

	public DataRow clone() {
		HashMap<String, String> attributesClone = new HashMap<>();
		
		for(String key : attributes.keySet()) {
			attributesClone.put(key, attributes.get(key));
		}
		
		return new DataRow(attributesClone, result);
	}
}
