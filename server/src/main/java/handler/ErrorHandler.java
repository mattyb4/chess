package handler;

public class ErrorHandler {
    private final String message;

    public ErrorHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
