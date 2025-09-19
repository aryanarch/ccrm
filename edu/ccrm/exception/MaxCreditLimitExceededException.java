package edu.ccrm.exception;

/**
 * Custom exception thrown when the maximum credit limit is exceeded.
 */
public class MaxCreditLimitExceededException extends Exception {
    public MaxCreditLimitExceededException(String message) {
        super(message);
    }

    public MaxCreditLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
