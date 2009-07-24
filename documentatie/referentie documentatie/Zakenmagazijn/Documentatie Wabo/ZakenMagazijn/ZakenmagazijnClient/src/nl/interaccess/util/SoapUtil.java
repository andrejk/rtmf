package nl.interaccess.util;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import oracle.j2ee.ws.saaj.soap.NameImpl;

import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class SoapUtil {

    private MessageFactory messageFactory = null;
    //private DocumentBuilderFactory documentBuilderFactory = null;
    //private DocumentBuilder documentBuilder = null;

    private XmlSchema schema;
    
    public SoapUtil() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
        //documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }
    
    public SoapUtil(XmlSchema schema) throws SOAPException {
        this();
        this.schema = schema;
    }

    public SOAPMessage createSOAPMessage(Object object) throws SOAPException, 
                                                                  JAXBException {
        List lObject = new ArrayList();
        lObject.add(object);
        
        return createSOAPMessage(lObject);
    }
    
    public SOAPMessage createSOAPFault(Throwable result) throws SOAPException {
        SOAPMessage message = null;
        synchronized(messageFactory) {

            message = messageFactory.createMessage();
            SOAPBody part = message.getSOAPBody();
            SOAPFault fault = 
                part.addFault(NameImpl.create("Client", "soap", "http://schemas.xmlsoap.org/soap/envelope/"), 
                              String.valueOf(result));
        }
        return message;
    }
    
                                                                
    public SOAPMessage createSOAPMessage(List lObject) throws SOAPException, 
                                                                  JAXBException {
        SOAPMessage soapMessage = null;
        
        synchronized(messageFactory) {
            soapMessage = messageFactory.createMessage();
            SOAPBody part = soapMessage.getSOAPBody();
            Marshaller marshaller = schema.createMarshaller();
            marshaller.setProperty(marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            if (lObject != null) {
                for (Object object : lObject) {
                    marshaller.marshal(object, part);
                }
            }
        }
        return soapMessage;
    }
    
    public List getObjects(SOAPMessage soapMessage) throws JAXBException, 
                                                           SOAPException {
        List lObject = new ArrayList();
        Unmarshaller m = schema.createUnmarshaller();

        if( soapMessage.getSOAPBody().hasFault()) {
            Node n = soapMessage.getSOAPBody().getFault().getDetail();
            for( int i = 0 ; i <  n.getChildNodes().getLength(); i++) {
                Node el = n.getChildNodes().item(i);
                if( el instanceof Element) {
                    Object object = m.unmarshal(el);
                    throw new WsFault(object);
                }
            }

            
        }

        for( Iterator it = soapMessage.getSOAPBody().getChildElements(); it.hasNext(); ) {
            Node el = (Node) it.next();
            if( el instanceof Element) {
                Object object = m.unmarshal(el);
                lObject.add(object);
            }
        }
        
        return lObject;
    }
}
