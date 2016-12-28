package exception;

/**
 * Created by Thomas on 19/10/2016.
 */
public class FeatureDoesntExistException extends Exception{
        public FeatureDoesntExistException(String message) {
            super(message);
        }

        public FeatureDoesntExistException(String message, Throwable cause) {
            super(message, cause);
        }
}
