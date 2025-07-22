package dataaccess.exceptions;
//for when the user did not have the correct inputs required for a method call
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
