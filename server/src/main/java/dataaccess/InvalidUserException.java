package dataaccess;
//for when the user is not properly logged in - does not have valid authToken
public class InvalidUserException extends Exception {
    public InvalidUserException(String message) {
        super(message);
    }
}
