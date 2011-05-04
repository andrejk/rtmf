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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class DateUtil.
 * 
 * This class contains date utility functions.

 * @author Enno Buis
 *
 */
public class DateUtils {

	/**
	 * Prevent instantiation of this class.
	 */
	private DateUtils() {
	}

	/**
	 * Gets current Time in xsd:dateTime format.
	 * 
	 * @return the current date time string value
	 */
	public static String getCurrentDateTimeStringValue() {

		Calendar cal = Calendar.getInstance();
		Date thisDate = cal.getTime();
		SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
		String currDate = formatterDate.format(thisDate) + "T"
				+ formatterTime.format(thisDate);

		return currDate;
	}
	
	/**
	 * Formats a date in a String.
	 * 
	 * @param dat
	 *            the dat
	 * @param pattern
	 *            the pattern
	 * 
	 * @return the string
	 */
	static public String formatDate(Date dat, String pattern) {
		DateFormat objForm = new SimpleDateFormat(pattern);

		return objForm.format(dat);
	}
	
	/**
	 * Gets the current date in ms.
	 * 
	 * @return the current date in ms
	 */
	static public long getCurrentDateInMs() {
		return new Date().getTime();
	}


	
}
