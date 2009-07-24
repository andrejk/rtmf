/**
 * 
 */
package nl.rotterdam.rtmf.guc.transformer.helper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * @author rweverwijk
 * 
 */
public class TerugmeldenXMLFilter extends XMLFilterImpl {
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
//		if (filterElement(localName)) {
			super.startElement(uri, localName, qName, atts);
//		} else {
//			((RTMFDataHandler)this.getContentHandler()).setVerwerken(false);
//		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
//		if (filterElement(localName))
			super.endElement(uri, localName, qName);
//		else {
//			((RTMFDataHandler)this.getContentHandler()).setVerwerken(true);
//		}
	}
	
	
	
	private boolean filterElement(String element) {
		if (element != null)
		return ! (element.equals("attachment") ||element.equals("filename") || element.equals("base64attachment"));
		return false;
	}

}
