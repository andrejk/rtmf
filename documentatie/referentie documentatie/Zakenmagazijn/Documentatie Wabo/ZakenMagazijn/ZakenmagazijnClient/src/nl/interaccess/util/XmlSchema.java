package nl.interaccess.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XmlSchema {

    private String schema;
    private JAXBContext jaxbContext;
    
    public XmlSchema() {
    }
    
    public XmlSchema(String schema) throws JAXBException {
        setSchema(schema);
    }
    
    public void setSchema(String schema) throws JAXBException {
        if (this.schema != null) {
            throw new IllegalStateException("schema already set");
        }
        this.schema = schema;
        jaxbContext = JAXBContext.newInstance(this.schema);
    }
    
    public String getSchema() {
        return schema;
    }
    
    public JAXBContext getJAXBContext() {
        return jaxbContext;
    }
    
    public Marshaller createMarshaller() throws JAXBException {
        return jaxbContext.createMarshaller();
    }
    
    public Unmarshaller createUnmarshaller() throws JAXBException {
        return jaxbContext.createUnmarshaller();
    }
    
    public String toXmlString(Object object,boolean formattedOutput) throws JAXBException {
        StringWriter sw = new StringWriter();
        Marshaller m = createMarshaller();
        if (formattedOutput) {
            m.setProperty(m.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
        }
        m.marshal(object,sw);
        return sw.toString();
    }
}
