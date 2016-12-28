package exception;

/**
 * Created by Thomas on 04/10/2016.
 */
public class RoundOutOfBoundException extends Exception {
    public RoundOutOfBoundException(String message) {
        super(message);
    }

    public RoundOutOfBoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
