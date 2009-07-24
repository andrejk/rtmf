package nl.interaccess.zakenmagazijn;

public class ZakenmagazijnException extends RuntimeException {
    
    public ZakenmagazijnException() {
    }
    
    public ZakenmagazijnException(Throwable cause) {
        super(cause);
    }
    
    public ZakenmagazijnException(String message) {
        super(message);
    }

    public ZakenmagazijnException(String message,Throwable cause) {
        super(message,cause);
    }
    
}
