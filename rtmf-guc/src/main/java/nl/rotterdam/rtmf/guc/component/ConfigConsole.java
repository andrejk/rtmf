package nl.rotterdam.rtmf.guc.component;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * Create string (page) from properties from the (Spring) configuration.
 *
 */
public class ConfigConsole  {
	
	Properties properties = null;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String createConfigPage(String input) {
		String msg = "";
		Set<Object> keys = properties.keySet();
		TreeSet<Object> sortedKeys = new TreeSet<Object>(keys);
		
		Iterator<Object> keysIterator = sortedKeys.iterator();
		while (keysIterator.hasNext()){
			String key = (String)keysIterator.next();
			msg += key + " = " + properties.getProperty(key) + "\n";
		}
		return msg;
	}
}
