/*
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*/
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
