package edu.ccrm.domain;

import java.util.List;
import java.util.function.Predicate;

/**
 * Generic interface for searchable objects.
 * Demonstrates generics, functional interfaces, and lambda expressions.
 */
public interface Searchable<T> {

    // Abstract method with generic return type
    List<T> findAll();

    // Default method using Predicate functional interface
    default List<T> findBy(Predicate<T> predicate) {
        return findAll().stream()
                .filter(predicate)
                .toList();
    }

    // Default method demonstrating lambda usage
    default T findFirst(Predicate<T> predicate) {
        return findAll().stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    // Static method for common search operations
    static <T> List<T> search(List<T> items, Predicate<T> condition) {
        return items.stream()
                .filter(condition)
                .toList();
    }
}
