package daos;

import org.apache.log4j.Logger;

public class PersistException extends Exception {
    private static final Logger logger = Logger.getLogger(TransactionFailedException.class);

    public PersistException() {}

    public PersistException(String message) {
        super(message);
        logger.warn(message);
    }

    public PersistException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);
    }

    public PersistException(Throwable cause) {
        super(cause);
    }

    public PersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.warn(message);
    }
}
