package nl.rotterdam.shared.docloods;

import javax.xml.bind.JAXBElement;

import nl.onefox.ebus._2009._3._20.AuthInfo;
import nl.onefox.ebus._2009._3._20.ObjectFactory;
import nl.onefox.ebus._2009._3._20.data.Trustee;
import nl.onefox.ebus._2009._3._20.data.Trustees;
import nl.onefox.ebus._2009._3._20.security.GetTrusteeMsg;
import nl.onefox.ebus._2009._3._20.security.ResultMsg;

import nl.onefox.ebus._2009._3._20.security.SetTrusteeMsg;

import nl.rotterdam.arch.webservice.WebserviceClient;
import nl.rotterdam.util.XmlSchema;


public class DocLoodsSecurityClient {
    static ObjectFactory baseObjectFactory = new ObjectFactory();
    static nl.onefox.ebus._2009._3._20.security.ObjectFactory securityObjectFactory = 
        new nl.onefox.ebus._2009._3._20.security.ObjectFactory();

    static XmlSchema baseSchema = null;
    static XmlSchema securitySchema = null;
    WebserviceClient indienenClient;

    public DocLoodsSecurityClient(String endpoint) {
        try {
            initSchema();
            indienenClient = new WebserviceClient(baseSchema, endpoint);
            indienenClient.addSchema(securitySchema);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stelt de te gebruiken timeouts in voor deze client.
     *
     * @param soapConnectionTimeout aantal milliseconden dat een connect zal blijven wachten
     * voordat een timeout optreedt. null voor geen timeout.
     * @param soapReadTimeout aantal milliseconden dat een read zal blijven wachten voordat
     * een timeout optreedt. null voor geen timeout.
     * @return this, voor gemakkelijke syntax: setTimeout(1000,1000).doe_iets()
     */
    public DocLoodsSecurityClient setTimeouts(Integer soapConnectionTimeout, Integer soapReadTimeout) {
        indienenClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        return this;
    }  

    private static synchronized void initSchema() throws Exception {
        if (baseSchema == null) {
            baseSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20", "http://ebus.onefox.nl/2009/3/20");
        }
        if (securitySchema == null) {
            securitySchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20.search", "http://ebus.onefox.nl/2009/3/20/security");
        }
    }

    public Trustees getTrustees(AuthInfo authInfo, 
                                     GetTrusteeMsg lookupList) throws Exception {

        JAXBElement<ResultMsg> response = 
            (JAXBElement<ResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                  securityObjectFactory.createGetTrusteeMsg(lookupList), 
                                                                  "http://ebus.onefox.nl/2009/3/20/SecurityService/GetTrustees");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue().getTrustees().getValue();
    }

    public Trustees setTrustees(AuthInfo authInfo, 
                                     SetTrusteeMsg lookupList) throws Exception {

        JAXBElement<ResultMsg> response = 
            (JAXBElement<ResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                  securityObjectFactory.createSetTrusteeMsg(lookupList), 
                                                                  "http://ebus.onefox.nl/2009/3/20/SecurityService/SetTrustees");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue().getTrustees().getValue();
    }

    public static void main(String[] args) throws Exception {
        try {
            DocLoodsSecurityClient myPort = 
                new DocLoodsSecurityClient("http://10.19.10.154/ebus/SecurityService.svc");
            // Add your own code here
            AuthInfo authInfo = new AuthInfo();
            authInfo.setLibrary("HB_L");
            authInfo.setUsername("jboer");
            authInfo.setPassword("welkom123");

            GetTrusteeMsg msg = new GetTrusteeMsg();
            msg.setForm("R_STUK_DOS");
            msg.setDocNumber(4364);
            Trustees t = myPort.getTrustees(authInfo, msg);
            for( Trustee trustee: t.getTrustee()) {
                System.out.println( trustee.getName());
                System.out.println( trustee.getRights() & 0x80);
                System.out.println( trustee.getRights() & 0x40);
                System.out.println( trustee.getRights() & 0x20);
                System.out.println( trustee.getRights() & 0x10);
                System.out.println( trustee.getRights() & 0x8);
                System.out.println( trustee.getRights() & 0x4);
                System.out.println( trustee.getRights() & 0x2);
                System.out.println( trustee.getRights() & 0x1);
            }
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
