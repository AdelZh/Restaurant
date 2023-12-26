package peaksoft.exception;

public class NotValidPhoneNumber extends RuntimeException{

    public NotValidPhoneNumber(String message) {
        super(message);
    }
}
