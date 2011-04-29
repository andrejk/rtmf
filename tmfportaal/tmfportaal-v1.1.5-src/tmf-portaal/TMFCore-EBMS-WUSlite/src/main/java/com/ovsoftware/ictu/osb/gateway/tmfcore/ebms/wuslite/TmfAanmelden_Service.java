
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "tmf-aanmelden", targetNamespace = "http://tmfportal.ovsoftware.com/services", wsdlLocation = "file:/C:/Documents%20and%20Settings/ktinselboer/My%20Documents/Eclipse%20Workspace/TMFPortal/WSDL/TMFCORE/EBMS/WUSLITE/Service.wsdl")
public class TmfAanmelden_Service
    extends Service
{

    private final static URL TMFAANMELDEN_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Documents%20and%20Settings/ktinselboer/My%20Documents/Eclipse%20Workspace/TMFPortal/WSDL/TMFCORE/EBMS/WUSLITE/Service.wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        TMFAANMELDEN_WSDL_LOCATION = url;
    }

    public TmfAanmelden_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TmfAanmelden_Service() {
        super(TMFAANMELDEN_WSDL_LOCATION, new QName("http://tmfportal.ovsoftware.com/services", "tmf-aanmelden"));
    }

    /**
     * 
     * @return
     *     returns TmfAanmelden
     */
    @WebEndpoint(name = "tmf-aanmelden")
    public TmfAanmelden getTmfAanmelden() {
        return (TmfAanmelden)super.getPort(new QName("http://tmfportal.ovsoftware.com/services", "tmf-aanmelden"), TmfAanmelden.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TmfAanmelden
     */
    @WebEndpoint(name = "tmf-aanmelden")
    public TmfAanmelden getTmfAanmelden(WebServiceFeature... features) {
        return (TmfAanmelden)super.getPort(new QName("http://tmfportal.ovsoftware.com/services", "tmf-aanmelden"), TmfAanmelden.class, features);
    }

}