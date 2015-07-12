package service;

import org.apache.log4j.Logger;

public class TransactionFailedException extends RuntimeException {
    private static final Logger logger = Logger.getLogger(TransactionFailedException.class);

    public TransactionFailedException(String message) {
        super(message);
        logger.warn(message);
    }
}