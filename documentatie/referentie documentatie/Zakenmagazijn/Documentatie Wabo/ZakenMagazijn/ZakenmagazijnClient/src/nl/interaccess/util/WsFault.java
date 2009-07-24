package nl.interaccess.util;

public class WsFault extends RuntimeException {
    private Object faultObject;
    public WsFault(Object faultObject) {
        super("Error in webservice operation");
        this.faultObject = faultObject;
    }

    public Object getFaultObject() {
        return faultObject;
    }
}
