package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Deze klasse dient alleen om het statussen.properties bestand
 * makkelijker te kunnen benaderen.
 * 
 * @author ktinselboer
 *
 */
public class Statussen {
	
	private static Logger logger = Logger.getLogger(Statussen.class);
	private static Properties properties = new Properties();
	private static String propertiesFilename = "statussen.properties";
	
	/**
	 * Nep constructor die voorkomt dat je een instantie kunt aanmaken
	 * van deze utility klasse.
	 * 
	 * @throws UnsupportedOperationException
	 */
	protected Statussen() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Haalt de lijst met alle waardes (statussen) op die eindigen op "naam".
	 * 
	 * @return De lijst met alle waardes gesorteerd.
	 */
	public static ArrayList<String> getValues() {
		//Laad het bestand indien nodig
	    if (properties.size() == 0) { reload(); }

	    ArrayList<String> stringResult = new ArrayList<String>();
	    ArrayList<String> unsorted = new ArrayList<String>();
	    
	    //sla de waarde van alle properties die eindigen op 'naam' op in de arraylist
	    for (Object obj : properties.keySet()) {
			String key = (String) obj;
			if (key.endsWith("naam")) {
				unsorted.add(key);
				//stringResult.add((String) properties.getProperty(key));
			}
		}   
	    Collections.sort(unsorted);
	    
	    Iterator<String> i = unsorted.iterator();
	    while (i.hasNext()){
	    	stringResult.add(properties.getProperty(i.next()));
	    }
	    //return de lijst met strings
	    return stringResult;	    
	}
	
	/**
	 * Geeft terug of een status een geldige status is.
	 * 
	 * @param status De te controleren status
	 * @return True indien de status in statussen.properties voorkomt en anders false
	 */
	public static boolean geldigeStatus(String status) {
		//indien null altijd false
		if (status==null) { return false; }
		
		//controleer of de status gelijk is aan een geldige status
		for (String geldigeStatus : Statussen.getValues()) {
			if (status.equals(geldigeStatus)) { return true; } //zo ja return true
		}
		
		//zo nee dan return false
		return false;
	}
	
	/**
	 * (Her)laad de statussen.
	 */
	private static void reload() {
		InputStream resourceAsStream = Statussen.class.getClassLoader().getResourceAsStream(propertiesFilename);
		
		if ( resourceAsStream == null ){
			throw new IllegalStateException( "Unable to find properties file in classpath:	" + propertiesFilename );
		}
         try {
			properties.load( resourceAsStream );
		} catch (IOException e) {
			logger.error("IOException while terying to load resource-stream in Statussen.", e);
		}
	}
	
	/**
	 * Controleert of deze status intrekbaar is of niet.
	 * 
	 * @param status De status waarvan de intrekbaarheid gecontroleerd moet worden
	 * @return True indien deze status intrekbaar is, anders False.
	 */
	public static boolean isIntrekbaar(String status){
		if (!geldigeStatus(status)) { return false; }
	    for (Object obj : properties.keySet()) {
			String key = (String) obj;
			if (key.endsWith(status.replace(" ","_")+".intrekbaar")) {
				return properties.getProperty(key).equalsIgnoreCase("true");
			}
		}  
		return false;		
	}
}
