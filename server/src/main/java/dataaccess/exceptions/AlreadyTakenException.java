package dataaccess.exceptions;
//for if user tried to create something that already existed
public class AlreadyTakenException extends Exception {
    public AlreadyTakenException(String message) {
        super(message);
    }
}
