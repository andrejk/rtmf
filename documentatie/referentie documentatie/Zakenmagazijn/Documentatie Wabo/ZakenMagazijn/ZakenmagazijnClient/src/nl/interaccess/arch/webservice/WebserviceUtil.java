package nl.interaccess.arch.webservice;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import nl.interaccess.util.SoapUtil;
import nl.interaccess.util.XmlSchema;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WebserviceUtil {

    private static Log log = LogFactory.getLog(WebserviceUtil.class);

    private XmlSchema schema;
    private SoapUtil soapUtil;

    public WebserviceUtil(String schema) throws SOAPException, JAXBException {
        setSchema(schema);
    }

    public WebserviceUtil(XmlSchema schema) throws SOAPException {
        setXmlSchema(schema);
    }

    public final void setSchema(String schema) throws SOAPException, 
                                                      JAXBException {
        setXmlSchema(new XmlSchema(schema));
    }
    
    public final void setXmlSchema(XmlSchema schema) throws SOAPException {
        assert schema != null : "schema";
        
        log.debug("schema set to " + schema.getSchema());
        this.schema = schema;
        soapUtil = new SoapUtil(schema);
    }

    public XmlSchema getXmlSchema() {
        return schema;
    }
    
    public SoapUtil getSoapUtil() {
        return soapUtil;
    }
    
    public List getObjects(SOAPMessage soapMessage) throws JAXBException, 
                                                           SOAPException {
        return soapUtil.getObjects(soapMessage);
    }
    
    public SOAPMessage createSOAPMessage(List lResult) throws SOAPException, 
                                                            JAXBException {
        return soapUtil.createSOAPMessage(lResult);
    }

    public SOAPMessage createSOAPFault(Throwable throwable) throws SOAPException {
        return soapUtil.createSOAPFault(throwable);
    }
    
    public SOAPMessage createSOAPMessage(Object object) throws SOAPException, 
                                                            JAXBException {
        return soapUtil.createSOAPMessage(object);
    }
    
}
