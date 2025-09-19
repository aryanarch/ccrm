package edu.ccrm.domain;

/**
 * Interface for objects that can be persisted.
 * Demonstrates interface design and default methods.
 */
public interface Persistable {

    // Abstract method - must be implemented
    String getId();

    // Default method - provides default implementation
    default boolean isValid() {
        return getId() != null && !getId().isEmpty();
    }

    // Static method - utility method
    static String generateId(String prefix, int number) {
        return prefix + String.format("%04d", number);
    }

    // Private method (Java 9+) - internal helper
    // Note: Private methods in interfaces require Java 9+
    // private void validateId() {
    //     if (!isValid()) {
    //         throw new IllegalStateException("Invalid ID");
    //     }
    // }
}
