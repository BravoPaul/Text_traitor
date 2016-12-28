package exception;

/**
 * Created by Thomas on 04/10/2016.
 */
public class LangDoesntExistException extends Exception{
    public LangDoesntExistException(String message) {
        super(message);
    }

    public LangDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

