import javax.activation.DataHandler;
import org.mule.api.MuleMessage;
import javax.mail.util.ByteArrayDataSource;



muleMessage = (MuleMessage)message
assert muleMessage != null : "Het message object is null"

muleMessage.addAttachment("terugmelding", new DataHandler(new ByteArrayDataSource((String) payload, "Content-Type: multipart/mixed")))

return message