package nl.rotterdam.shared.docloods;

import javax.xml.bind.JAXBElement;

import nl.onefox.ebus._2009._3._20.AuthInfo;
import nl.onefox.ebus._2009._3._20.ObjectFactory;
import nl.onefox.ebus._2009._3._20.data.Field;
import nl.onefox.ebus._2009._3._20.data.ReturnFields;
import nl.onefox.ebus._2009._3._20.data.SearchCriteria;
import nl.onefox.ebus._2009._3._20.data.SearchItems;
import nl.onefox.ebus._2009._3._20.data.SortField;
import nl.onefox.ebus._2009._3._20.data.SortFields;
import nl.onefox.ebus._2009._3._20.search.ResultMsg;
import nl.onefox.ebus._2009._3._20.search.SearchProfileMsg;

import nl.rotterdam.arch.webservice.WebserviceClient;
import nl.rotterdam.util.XmlSchema;


public class DocLoodsSearchClient {
    static ObjectFactory baseObjectFactory = new ObjectFactory();
    static nl.onefox.ebus._2009._3._20.search.ObjectFactory searchObjectFactory = 
        new nl.onefox.ebus._2009._3._20.search.ObjectFactory();

    static XmlSchema baseSchema = null;
    static XmlSchema searchSchema = null;
    WebserviceClient indienenClient;

    public DocLoodsSearchClient(String endpoint) {
        try {
            initSchema();
            indienenClient = new WebserviceClient(baseSchema, endpoint);
            indienenClient.addSchema(searchSchema);
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
    public DocLoodsSearchClient setTimeouts(Integer soapConnectionTimeout, Integer soapReadTimeout) {
        indienenClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        return this;
    }  


    private static synchronized void initSchema() throws Exception {
        if (baseSchema == null) {
            baseSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20", "http://ebus.onefox.nl/2009/3/20");
        }
        if (searchSchema == null) {
            searchSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20.search", "http://ebus.onefox.nl/2009/3/20/search");
        }
    }

    public SearchItems searchProfile(AuthInfo authInfo, 
                                     SearchProfileMsg lookupList) throws Exception {
        if( lookupList.getSortFields() == null) lookupList.setSortFields(searchObjectFactory.createSearchProfileMsgSortFields(new SortFields()));

        JAXBElement<ResultMsg> response = 
            (JAXBElement<ResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                  searchObjectFactory.createSearchProfileMsg(lookupList), 
                                                                  "http://ebus.onefox.nl/2009/3/20/SearchService/SearchProfile");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue().getSearchItems().getValue();
    }

    public static void main(String[] args) throws Exception {
        try {
            DocLoodsSearchClient myPort = 
                new DocLoodsSearchClient("http://10.19.10.154/ebus/SearchService.svc");
            // Add your own code here
            AuthInfo authInfo = new AuthInfo();
            authInfo.setLibrary("HB_L");
            authInfo.setUsername("WABOSUP");
            authInfo.setPassword("WABOSUP");

            SearchProfileMsg msg = new SearchProfileMsg();
            msg.setForm("R_ZOEKEN_DOS");
            msg.setSearchCriteria(new SearchCriteria());
            Field field = new Field();
//            field.setName("X2726");
//            field.setValue("4316");
//            msg.getSearchCriteria().getSearchCriterium().add(field);
//            field = new Field();
            
            field.setName("R_ZAAKNRDIENST");
            field.setValue("MGE133");

            msg.getSearchCriteria().getSearchCriterium().add(field);
            ReturnFields returnFields = new ReturnFields();
            
//            data.getReturnFields().getString().add("DOCNAME");
//            data.getReturnFields().getString().add("CREATION_DATE");
//            data.getReturnFields().getString().add("R_ZAAKNRDIENST");
//            data.getReturnFields().getString().add("R_STATUS");
//            data.getReturnFields().getString().add("R_RICHTING");
//            data.getReturnFields().getString().add("R_BEHANDELAAR");
            returnFields.getReturnField().add("TYPE_ID");
            returnFields.getReturnField().add("CREATION_DATE");
            returnFields.getReturnField().add("DOCNUM");
            
            SortFields sortFields = new SortFields();
            SortField sortField = new SortField();
            sortField.setName("CREATION_DATE");;
            sortField.setAscending(false);
            sortFields.getSortField().add(sortField);
            msg.setSortFields(searchObjectFactory.createSearchProfileMsgSortFields(sortFields));
            msg.setReturnFields(searchObjectFactory.createSearchProfileMsgReturnFields(returnFields));
            SearchItems result = myPort.searchProfile(authInfo, msg);
            System.out.println(result.getSearchItem().size());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
