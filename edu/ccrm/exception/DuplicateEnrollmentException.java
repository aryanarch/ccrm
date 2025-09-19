package edu.ccrm.exception;

/**
 * Custom exception thrown when a student tries to enroll in a course they are already enrolled in.
 */
public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String message) {
        super(message);
    }

    public DuplicateEnrollmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
