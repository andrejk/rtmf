package nl.rotterdam.shared.docloods;

import java.io.File;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import nl.onefox.ebus._2009._3._20.AuthInfo;
import nl.onefox.ebus._2009._3._20.ObjectFactory;
import nl.onefox.ebus._2009._3._20.attachment.ReadDocumentMsg;
import nl.onefox.ebus._2009._3._20.attachment.ReadDocumentResultMsg;
import nl.onefox.ebus._2009._3._20.attachment.WriteDocumentMsg;
import nl.onefox.ebus._2009._3._20.attachment.WriteDocumentResultMsg;
import nl.onefox.ebus._2009._3._20.data.ReturnFields;
import nl.onefox.ebus._2009._3._20.document.ReadContentResultMsg;

import nl.rotterdam.arch.webservice.WebserviceClient;
import nl.rotterdam.util.MTOMAttachment;
import nl.rotterdam.util.XmlSchema;


public class DocLoodsAttachmentClient {
    static ObjectFactory baseObjectFactory = new ObjectFactory();
    static nl.onefox.ebus._2009._3._20.attachment.ObjectFactory attachmentObjectFactory = 
        new nl.onefox.ebus._2009._3._20.attachment.ObjectFactory();

    static XmlSchema baseSchema = null;
    static XmlSchema attachmentSchema = null;
    WebserviceClient indienenClient;

    public DocLoodsAttachmentClient(String endpoint) {
        try {
            initSchema();
            indienenClient = new WebserviceClient(baseSchema, endpoint);
            indienenClient.addSchema(attachmentSchema);
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
    public DocLoodsAttachmentClient setTimeouts(Integer soapConnectionTimeout, Integer soapReadTimeout) {
        indienenClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        return this;
    }    
 
    


    private static synchronized void initSchema() throws Exception {
        if (baseSchema == null) {
            baseSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20", "http://ebus.onefox.nl/2009/3/20");
        }
        if (attachmentSchema == null) {
            attachmentSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20.attachment", "http://ebus.onefox.nl/2009/3/20/attachment");
        }
    }

    public ReadContentResultMsg readDocument(AuthInfo authInfo, 
                                     ReadDocumentMsg lookupList) throws Exception {
        
        List response = (List)
            indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), attachmentObjectFactory.createReadDocumentMsg(lookupList),  "http://ebus.onefox.nl/2009/3/20/AttachmentService/ReadDocument");
                                                                  
        JAXBElement<ReadDocumentResultMsg> read = (JAXBElement<ReadDocumentResultMsg>) response.get(0);
        if (read.getValue().getErrCode() != 0)
            throw new DmsException(read.getValue().getErrCode(), read.getValue().getErrDescription().getValue());
        JAXBElement<byte[]> bytes = (JAXBElement) response.get(1);
        
        return new ReadDocumentResult(read.getValue(), bytes.getValue());
    }

    public WriteDocumentResultMsg writeDocument(AuthInfo authInfo, 
                                     WriteDocumentMsg lookupList, File file) throws Exception {
        List header = new ArrayList();
        header.add(attachmentObjectFactory.createWriteDocumentMsg(lookupList));
        header.add(baseObjectFactory.createAuthInfo(authInfo));
        
        MTOMAttachment mtomAttachment = null;
        if( file != null) mtomAttachment = new MTOMAttachment( "WriteDocument", "http://ebus.onefox.nl/2009/3/20/attachment", file);
        JAXBElement<WriteDocumentResultMsg> response = (JAXBElement<WriteDocumentResultMsg>)
            indienenClient.callWithHeader(header, mtomAttachment,  "http://ebus.onefox.nl/2009/3/20/AttachmentService/WriteDocument");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue();
    }
    
    public WriteDocumentResultMsg writeDocument(AuthInfo authInfo, 
                                     WriteDocumentMsg lookupList, InputStream file) throws Exception {
        List header = new ArrayList();
        header.add(attachmentObjectFactory.createWriteDocumentMsg(lookupList));
        header.add(baseObjectFactory.createAuthInfo(authInfo));
        
        MTOMAttachment mtomAttachment = new MTOMAttachment( "WriteDocument", "http://ebus.onefox.nl/2009/3/20/attachment", file);
        JAXBElement<WriteDocumentResultMsg> response = (JAXBElement<WriteDocumentResultMsg>)
            indienenClient.callWithHeader(header, mtomAttachment,  "http://ebus.onefox.nl/2009/3/20/AttachmentService/WriteDocument");
        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue();
    }
    
    public static void main(String[] args) throws Exception {
            DocLoodsAttachmentClient myPort = 
                // new DocLoodsAttachmentClient("http://localhost:8099/ebus/AttachmentService.svc");
                new DocLoodsAttachmentClient("http://10.19.10.154/ebus/AttachmentService.svc");
            // Add your own code here
            AuthInfo authInfo = new AuthInfo();
            authInfo.setLibrary("HB_L");
            authInfo.setUsername("jboer");
            authInfo.setPassword("welkom123");
            
            
            try {
                WriteDocumentMsg msg = new WriteDocumentMsg();
                    msg.setForm("R_STUK_DOS");
                msg.setDocNumber(4436);
                msg.setCreateNewVersion(Boolean.FALSE);
                ReturnFields fields = new ReturnFields();
                msg.setReturnFields(attachmentObjectFactory.createWriteDocumentMsgReturnFields(fields));
                fields.getReturnField().add("DOCNAME");
                Object result = myPort.writeDocument(authInfo, msg, new File("C:\\temp\\doctree.xml"));
            } catch(Exception ex ) {
                ex.printStackTrace();
            }
            

//            try {
//                ReadDocumentMsg msg = new ReadDocumentMsg();
//                msg.setForm("R_STUK_DOS");
//                msg.setDocNumber(4364);
//                msg.setCheckout(Boolean.FALSE);
//                // msg.setVersion(55);
//                ReturnFields fields = new ReturnFields();
//                msg.setReturnFields(attachmentObjectFactory.createReadDocumentMsgReturnFields(fields));
//                fields.getReturnField().add("DOCNAME");
//                ReadContentResultMsg result = myPort.readDocument(authInfo, msg);
//    
//                System.out.println(result.getDocNumber());
//                System.out.println(result.getContent().getValue().length);
//    
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
    }
}
