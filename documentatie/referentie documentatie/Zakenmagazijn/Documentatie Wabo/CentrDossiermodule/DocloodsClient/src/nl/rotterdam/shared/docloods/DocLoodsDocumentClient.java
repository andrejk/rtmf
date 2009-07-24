package nl.rotterdam.shared.docloods;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import nl.onefox.ebus._2009._3._20.AuthInfo;
import nl.onefox.ebus._2009._3._20.ObjectFactory;
import nl.onefox.ebus._2009._3._20.data.ReturnFields;
import nl.onefox.ebus._2009._3._20.document.ReadContentResultMsg;
import nl.onefox.ebus._2009._3._20.document.ReadDocumentContentMsg;
import nl.onefox.ebus._2009._3._20.document.WriteContentResultMsg;
import nl.onefox.ebus._2009._3._20.document.WriteDocumentContentMsg;

import nl.rotterdam.arch.webservice.WebserviceClient;
import nl.rotterdam.util.XmlSchema;


public class DocLoodsDocumentClient {
    static ObjectFactory baseObjectFactory = new ObjectFactory();
    static nl.onefox.ebus._2009._3._20.document.ObjectFactory documentObjectFactory = 
        new nl.onefox.ebus._2009._3._20.document.ObjectFactory();

    static XmlSchema baseSchema = null;
    static XmlSchema documentSchema = null;
    WebserviceClient indienenClient;

    public DocLoodsDocumentClient(String endpoint) {
        try {
            initSchema();
            indienenClient = new WebserviceClient(baseSchema, endpoint);
            indienenClient.addSchema(documentSchema);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void initSchema() throws Exception {
        if (baseSchema == null) {
            baseSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20", "http://ebus.onefox.nl/2009/3/20");
        }
        if (documentSchema == null) {
            documentSchema = 
                    new XmlSchema("nl.onefox.ebus._2009._3._20.profile", 
                                  "http://ebus.onefox.nl/2009/3/20/document");
        }
    }

    public ReadContentResultMsg readDocument(AuthInfo authInfo, 
                                           ReadDocumentContentMsg lookupList) throws Exception {
        JAXBElement<ReadContentResultMsg> response = 
            (JAXBElement<ReadContentResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), documentObjectFactory.createReadDocumentContentMsg(lookupList), "http://ebus.onefox.nl/2009/3/20/DocumentService/ReadDocumentContent");

        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue();
    }

    public Object writeDocument(AuthInfo authInfo, WriteDocumentContentMsg lookupList, 
                                File file) throws Exception {
        List header = new ArrayList();
        header.add(documentObjectFactory.createWriteDocumentContentMsg(lookupList));
        header.add(baseObjectFactory.createAuthInfo(authInfo));
        byte[] bytes = new byte[(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();
        lookupList.setContent(documentObjectFactory.createWriteDocumentContentMsgContent(bytes));
        JAXBElement<WriteContentResultMsg> response = 
            (JAXBElement<WriteContentResultMsg>)indienenClient.callWithHeader(baseObjectFactory.createAuthInfo(authInfo), documentObjectFactory.createWriteDocumentContentMsg(lookupList), "http://ebus.onefox.nl/2009/3/20/DocumentService/WriteDocumentContent");

        if (response.getValue().getErrCode() != 0)
            throw new DmsException(response.getValue().getErrCode(), response.getValue().getErrDescription().getValue());
        return response.getValue();
    }

    public static void main(String[] args) throws Exception {
        DocLoodsDocumentClient myPort = 
            new DocLoodsDocumentClient("http://10.19.10.154/ebus/DocumentService.svc");
        // Add your own code here
        AuthInfo authInfo = new AuthInfo();
        authInfo.setLibrary("HB_L");
        authInfo.setUsername("jboer");
        authInfo.setPassword("welkom123");
        try {
            WriteDocumentContentMsg msg = new WriteDocumentContentMsg();
            msg.setForm(documentObjectFactory.createWriteDocumentContentMsgForm("R_STUK_DOS"));
            msg.setDocNumber(4398);
            msg.setCreateNewVersion(Boolean.TRUE);
            ReturnFields fields = new ReturnFields();
            msg.setReturnFields(documentObjectFactory.createWriteDocumentMsgReturnFields(fields));
            fields.getReturnField().add("DOCNAME");
            Object result = myPort.writeDocument(authInfo, msg, new File("C:\\work\\documenten\\brief-belanghebbenden.txt"));
            System.out.println(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


                    try {
                        ReadDocumentContentMsg msg = new ReadDocumentContentMsg();
                        msg.setForm("R_STUK_DOS");
                        msg.setDocNumber(4364);
                        msg.setCheckout(Boolean.FALSE);
                        // msg.setVersion(55);
                        ReturnFields fields = new ReturnFields();
                        msg.setReturnFields(documentObjectFactory.createReadDocumentContentMsgReturnFields(fields));
                        fields.getReturnField().add("DOCNAME");
                        ReadContentResultMsg result = myPort.readDocument(authInfo, msg);
            
                        System.out.println(result.getDocNumber());
                        System.out.println(result.getContent().getValue().length);
            
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
    }
}
