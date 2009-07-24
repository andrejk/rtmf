package nl.interaccess.arch.webservice;

import java.io.IOException;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;


public class WebserviceClient {

    private static SOAPConnectionFactory connectionFactory;
    
    private String url;
    private WebserviceUtil wsUtil;
    
    public WebserviceClient(String schema,String url) throws SOAPException, 
                                                            JAXBException {
        this.wsUtil = new WebserviceUtil(schema);
        this.url = url;
    }
    
    public List call(Object object, String action) throws SOAPException, JAXBException, 
                                           IOException {
        SOAPMessage soapMessage = wsUtil.createSOAPMessage(object);
        if( action != null) {
            MimeHeaders hd = soapMessage.getMimeHeaders();
            hd.addHeader("SOAPAction", action);
        }

        SOAPConnection connection = createConnection();
        soapMessage = connection.call(soapMessage,url);
        return wsUtil.getObjects(soapMessage);
    }
    
    private static synchronized SOAPConnection createConnection() throws SOAPException {
        if (connectionFactory == null) {
            connectionFactory = SOAPConnectionFactory.newInstance();
        }
        
        return connectionFactory.createConnection();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
