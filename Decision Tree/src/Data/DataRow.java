package Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to hold a row of data on the table; maps attributes to its option and
 * holds boolean result
 * @author Jeffrey Tran
 */
public class DataRow {
	public HashMap<String, String> attributes;
	public boolean result;
	
	public DataRow(final ArrayList<String> listAttributeNames,
			final ArrayList<String> listAttributes,
			final boolean result) {
		attributes = new HashMap<>();
		
		for (int i = 0; i < listAttributes.size(); i ++) {
			attributes.put(listAttributeNames.get(i), listAttributes.get(i));
		}
		this.result = result;
	}
	
	public DataRow(final HashMap<String, String> attributes,
			final boolean result) {
		this.attributes = attributes;
		this.result = result;
	}

	/**
	 * @return a deep copy of an existing DataRow
	 */
	public DataRow clone() {
		final HashMap<String, String> attributesClone = new HashMap<>();
		
		for (String key : attributes.keySet()) {
			attributesClone.put(key, attributes.get(key));
		}
		
		return new DataRow(attributesClone, result);
	}
}