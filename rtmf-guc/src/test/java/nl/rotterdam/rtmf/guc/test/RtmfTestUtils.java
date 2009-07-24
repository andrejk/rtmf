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
