package nl.rotterdam.shared.docloods;

import javax.xml.bind.JAXBElement;

import nl.onefox.ebus._2009._3._20.AuthInfo;
import nl.onefox.ebus._2009._3._20.ObjectFactory;
import nl.onefox.ebus._2009._3._20.data.Field;
import nl.onefox.ebus._2009._3._20.data.ReturnFields;
import nl.onefox.ebus._2009._3._20.profile.CreateProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.EditProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.GetProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.QueueForDeleteProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.QueueForDeleteProfileResultMsg;
import nl.onefox.ebus._2009._3._20.profile.ResultMsg;

import nl.rotterdam.arch.webservice.WebserviceClient;
import nl.rotterdam.shared.dms.DocumentRegistratie;
import nl.rotterdam.util.XmlSchema;


public class DocLoodsProfileClient {
    static ObjectFactory baseObjectFactory = new ObjectFactory();
    static nl.onefox.ebus._2009._3._20.profile.ObjectFactory profileObjectFactory = 
        new nl.onefox.ebus._2009._3._20.profile.ObjectFactory();

    static XmlSchema baseSchema = null;
    static XmlSchema profileSchema = null;
    WebserviceClient indienenClient;

    public DocLoodsProfileClient(String endpoint) {
        try {
            initSchema();
            indienenClient = new WebserviceClient(baseSchema, endpoint);
            indienenClient.addSchema(profileSchema);
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
    public DocLoodsProfileClient setTimeouts(Integer soapConnectionTimeout, Integer soapReadTimeout) {
        indienenClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        return this;
    }    
    
    private static synchronized void initSchema() throws Exception {
        if (baseSchema == null) {
            baseSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20", "http://ebus.onefox.nl/2009/3/20");
        }
        if (profileSchema == null) {
            profileSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20.profile", "http://ebus.onefox.nl/2009/3/20/profile");
        }

    }



    public ResultMsg createProfile(AuthInfo authInfo, 
                                                  CreateProfileMsg createProfileMsgType) throws Exception {
                JAXBElement<ResultMsg> response = 
                    (JAXBElement<ResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                          profileObjectFactory.createCreateProfileMsg(createProfileMsgType), 
                                                                          "http://ebus.onefox.nl/2009/3/20/ProfileService/CreateProfile");
                if (response.getValue().getErrCode() != 0)
                    throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
                return response.getValue();
    }

    public ResultMsg getProfile(AuthInfo authInfo, 
                                               GetProfileMsg getProfileMsgType) throws Exception {
        JAXBElement<ResultMsg> response = 
            (JAXBElement<ResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                  profileObjectFactory.createGetProfileMsg(getProfileMsgType), 
                                                                  "http://ebus.onefox.nl/2009/3/20/ProfileService/GetProfile");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue();
    }

    public ResultMsg editProfile(AuthInfo authInfo, 
                                                EditProfileMsg editProfileMsgType) throws Exception {
        JAXBElement<ResultMsg> response = 
            (JAXBElement<ResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                  profileObjectFactory.createEditProfileMsg(editProfileMsgType), 
                                                                  "http://ebus.onefox.nl/2009/3/20/ProfileService/EditProfile");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue();
    }

    public QueueForDeleteProfileResultMsg queueForDeleteProfile(AuthInfo authInfo, 
                                                            QueueForDeleteProfileMsg queueForDeletionProfileMsgType)  throws Exception {
        JAXBElement<QueueForDeleteProfileResultMsg> response = 
            (JAXBElement<QueueForDeleteProfileResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), 
                                                                  profileObjectFactory.createQueueForDeleteProfileMsg(queueForDeletionProfileMsgType), 
                                                                  "http://ebus.onefox.nl/2009/3/20/ProfileService/QueueForDeleteProfile");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue();
    }
    


    public static void main(String[] args) throws Exception {
        try {
            DocLoodsProfileClient myPort = 
                new DocLoodsProfileClient("http://10.19.10.154/ebus/ProfileService.svc");
            // Add your own code here
            AuthInfo authInfo = new AuthInfo();
            authInfo.setLibrary("HB_L");
            authInfo.setUsername("jboer");
            authInfo.setPassword("welkom123");
            
            GetProfileMsg msg = new GetProfileMsg();
            msg.setForm("R_STUK_DOS");
            msg.setDocNumber(4533);
            ReturnFields returnFields = new ReturnFields();
            msg.setReturnFields(profileObjectFactory.createGetProfileMsgReturnFields(returnFields));
            
            for( String field : DocumentRegistratie.FIELDS) {
                returnFields.getReturnField().add(field);
            }
            
            ResultMsg result = myPort.getProfile(authInfo, msg);
            for( Field f : result.getFields().getValue().getField()) {
                System.out.println( f.getName() + "=" + f.getValue());
            }
            System.out.println(result);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
