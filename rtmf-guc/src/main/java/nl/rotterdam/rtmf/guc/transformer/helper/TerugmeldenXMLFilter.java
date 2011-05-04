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
