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
