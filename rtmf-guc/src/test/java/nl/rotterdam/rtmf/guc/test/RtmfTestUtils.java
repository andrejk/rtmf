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
package nl.rotterdam.rtmf.guc.test;

import org.junit.Assert;

/**
 * Common test utilities voor de rtmf tests.
 * 
 * @author Peter Paul Bakker
 *
 */
public class RtmfTestUtils {
	private RtmfTestUtils() {
		super();
	}

	public static void assertContainsText(String text,
			String expected) {
		Assert.assertTrue("text bevat geen '" + expected + "': " + text, 
				text.contains(expected));
	}

	public static void assertDoesNotContainText(String text,
			String unexpected) {
		Assert.assertTrue("text bevat niet verwachte text '" + unexpected + "': " + text, 
				!text.contains(unexpected));
	}
}
