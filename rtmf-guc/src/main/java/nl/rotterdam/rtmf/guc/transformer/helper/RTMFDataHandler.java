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




import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;
import nl.rotterdam.rtmf.guc.transformer.Attachment;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class RTMFDataHandler extends DefaultHandler {
	Logger logger = Logger.getLogger(RTMFDataHandler.class);
	StringBuffer sb = new StringBuffer();
	List<Attachment> attachments = new ArrayList<Attachment>();
	private CharArrayWriter attachment = new CharArrayWriter(); 
	private String fileName;
	private boolean filenameB = false;
	private boolean verwerken = true;
    @Override
	public void startElement(String uri, 
                             String localName, 
                             String qName, 
                             Attributes atts) 
                                  throws SAXException { 
//        println("<" + qName + formatAttributen(atts) +">"); 
    	storeAttachment();
    	if (filterElement(localName)) {
        	println("<" + qName + namespaceString(qName, uri) + ">");
        } else if (localName.contains("filename")){
        	filenameB = true;
        	verwerken = false;
        } else {
        	verwerken = false;
        }
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length)
    		throws SAXException {
    	if (verwerken) {
    		println(new String(ch, start, length));
    	} else if (filenameB) {
    		fileName = new String(ch, start, length);
    	} else {
    		attachment.write(ch, start, length);
    	}
    }
    @Override
	public void endElement(String uri, 
                           String localName, 
                           String qName) 
                                  throws SAXException { 
    	storeAttachment();
    	if (filterElement(localName)) {
    		println("</" + qName + ">");
    	} else if (localName.contains("filename")){
        	filenameB = false;
        	verwerken = true;
        } else {
        	verwerken = true;
        }
    } 
 
    /**
	 * @param qName
	 * @param uri
	 * @return
	 */
	private String namespaceString(String qName, String uri) {
		if (qName.contains(":")) {
			return " xmlns:" + qName.subSequence(0, qName.indexOf(":")) + "=\"" + uri + "\"";
		}
		return "";
	}

	 
    private void println(String s) throws SAXException { 
        if (verwerken) {
        	sb.append(s);
        }
        else {
        	try {
				attachment.write(s);
			} catch (IOException e) {
				throw new RtmfGucException("Er is een fout opgetreden tijdens het wegschrijven van de attachment", e);
			}
        }
    }
    
    public String getOutput() {
    	return sb.toString();
    }
    
    public List<Attachment> getAttachments() {
    	return attachments;
    }
    
    public void setVerwerken(boolean verwerken) {
    	this.verwerken = verwerken;
    }
    
    private boolean filterElement(String element) {
		if (element != null)
		return ! (element.equals("attachment") ||element.equals("filename") || element.equals("base64attachment"));
		return false;
	}
    
    /**
	 * 
	 */
	private void storeAttachment() {
		if (attachment.size() != 0 && fileName != null) {
			attachments.add(new Attachment(fileName, attachment.toString()));
			attachment.reset();
			fileName = null;
		}
	}
}