package nl.rotterdam.shared.docloods;

import javax.xml.bind.JAXBElement;

import nl.onefox.ebus._2009._3._20.attachment.ReadDocumentResultMsg;
import nl.onefox.ebus._2009._3._20.document.ObjectFactory;
import nl.onefox.ebus._2009._3._20.document.ReadContentResultMsg;

public class ReadDocumentResult extends ReadContentResultMsg {
    ReadDocumentResultMsg msg;
    JAXBElement<byte[]> content;
    public ReadDocumentResult(ReadDocumentResultMsg msg, byte[] content) {
        this.msg = msg;
        this.content =  new ObjectFactory().createReadContentResultMsgContent(content);
    }

    public Integer getDocNumber() {
        return msg.getDocNumber();
    }

    public Integer getErrCode() {
        return msg.getErrCode();
    }

    public JAXBElement getErrDescription() {
        return msg.getErrDescription();
    }

    public JAXBElement getFields() {
        return msg.getFields();
    }

    public Integer getVersion() {
        return msg.getVersion();
    }

    public JAXBElement<byte[]> getContent() {
        return content;
    }
}
