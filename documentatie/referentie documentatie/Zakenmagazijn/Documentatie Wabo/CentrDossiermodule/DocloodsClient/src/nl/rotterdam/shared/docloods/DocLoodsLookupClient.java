package nl.rotterdam.shared.docloods;

import javax.xml.bind.JAXBElement;

import nl.onefox.ebus._2009._3._20.AuthInfo;
import nl.onefox.ebus._2009._3._20.ObjectFactory;
import nl.onefox.ebus._2009._3._20.data.LookupItems;
import nl.onefox.ebus._2009._3._20.data.SearchCriteria;
import nl.onefox.ebus._2009._3._20.data.SortFields;
import nl.onefox.ebus._2009._3._20.lookup.GetLookupListMsg;
import nl.onefox.ebus._2009._3._20.lookup.ResultMsg;

import nl.rotterdam.arch.webservice.WebserviceClient;
import nl.rotterdam.util.XmlSchema;


public class DocLoodsLookupClient {
    static ObjectFactory baseObjectFactory = new ObjectFactory();
    static nl.onefox.ebus._2009._3._20.lookup.ObjectFactory lookupObjectFactory = 
        new nl.onefox.ebus._2009._3._20.lookup.ObjectFactory();

    static XmlSchema baseSchema = null;
    static XmlSchema lookupSchema = null;
    WebserviceClient indienenClient;

    public DocLoodsLookupClient(String endpoint) {
        try {
            initSchema();
            indienenClient = new WebserviceClient(baseSchema, endpoint);
            indienenClient.addSchema(lookupSchema);
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
    public DocLoodsLookupClient setTimeouts(Integer soapConnectionTimeout, Integer soapReadTimeout) {
        indienenClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        return this;
    }  


    private static synchronized void initSchema() throws Exception {
        if (baseSchema == null) {
            baseSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20", "http://ebus.onefox.nl/2009/3/20");
        }
        if (lookupSchema == null) {
            lookupSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20.profile", "http://ebus.onefox.nl/2009/3/20/lookup");
        }
    }

    public LookupItems getLookupList(AuthInfo authInfo, 
                                     GetLookupListMsg lookupList) throws Exception {
        if( lookupList.getSearchCriteria() == null) lookupList.setSearchCriteria(new SearchCriteria());
        if( lookupList.getSortFields() == null) lookupList.setSortFields(lookupObjectFactory.createGetLookupListMsgSortFields(new SortFields()));
        JAXBElement<ResultMsg> response = 
            (JAXBElement<ResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                  lookupObjectFactory.createGetLookupListMsg(lookupList), 
                                                                  "http://ebus.onefox.nl/2009/3/20/LookupService/GetLookupList");
        // "http://ebus.onefox.nl/GetLookupList"); 
        System.out.println(response);
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue().getLookupItems().getValue();
    }

    public static void main(String[] args) throws Exception {
        try {
            DocLoodsLookupClient myPort = 
                new DocLoodsLookupClient("http://10.19.10.154/ebus/LookupService.svc");
            // Add your own code here
            GetLookupListMsg msg = new GetLookupListMsg();
            AuthInfo authInfo = new AuthInfo();
            authInfo.setLibrary("HB_L");
            authInfo.setUsername("JBOER");
            authInfo.setPassword("WELKOM123");
            msg.setForm("R_ZAAK_DOS");
            msg.setLookupId("R_STATUS");
            LookupItems result = myPort.getLookupList(authInfo, msg);
            System.out.println(result.getLookupItem().size());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
