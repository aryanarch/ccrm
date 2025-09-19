package edu.ccrm.domain;

/**
 * Enum representing academic semesters.
 * Demonstrates enum with constructors and fields.
 */
public enum Semester {
    SPRING("Spring Semester", 1),
    SUMMER("Summer Semester", 2),
    FALL("Fall Semester", 3);

    private final String displayName;
    private final int semesterNumber;

    Semester(String displayName, int semesterNumber) {
        this.displayName = displayName;
        this.semesterNumber = semesterNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
