package nl.rotterdam.shared.docloods;

import java.util.ResourceBundle;

public class DmsException extends RuntimeException {
    static ResourceBundle bundle = ResourceBundle.getBundle("ebus_messages");
    
    private String ebusMessage;
    
    public DmsException(int code, String ebusMessage) {
        super("ebus.error." + code);
        this.ebusMessage = ebusMessage;
    }   
    
    public String getMessage() {
        try {
            return bundle.getString(super.getMessage());
        } catch( Exception ex) {
            return ebusMessage;
        }
    }

    public void setEbusMessage(String ebusMessage) {
        this.ebusMessage = ebusMessage;
    }

    public String getEbusMessage() {
        return ebusMessage;
    }
}
