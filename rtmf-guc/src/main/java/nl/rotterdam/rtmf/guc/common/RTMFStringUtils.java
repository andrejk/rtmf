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
package nl.rotterdam.rtmf.guc.common;

public class RTMFStringUtils {

	
	public static String[] payloadToArray(String payloadAsString) {
		
		String[] results;
		
		/*
		 * Wellicht een of andere 'smart' regex hiero
		 * Nu ff oplossen mbv: ','
		 */
		results = payloadAsString.split(","); 
		for (int i=0; i<results.length; i++) {
			boolean doTrim = false;
			results[i].trim();
			if (results[i].startsWith("{")) {
				results[i] = results[i].substring(1);
				doTrim = true;
			}
			if (results[i].endsWith("}")) {
				results[i] = results[i].substring(0, results[i].length() - 1);
				doTrim = true;
			}
			if (doTrim) {
				results[i].trim();
			}
		}
		
		return results;
	}
}
