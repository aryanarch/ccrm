package edu.ccrm.domain;

/**
 * Immutable value class for course codes.
 * Demonstrates immutability with final fields and defensive copying.
 */
public final class CourseCode implements Persistable {
    private final String code;
    private final String department;
    private final int number;

    public CourseCode(String code, String department, int number) {
        this.code = code;
        this.department = department;
        this.number = number;
    }

    // Implementing Persistable interface
    @Override
    public String getId() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public String getDepartment() {
        return department;
    }

    public int getNumber() {
        return number;
    }

    // Factory method for creating CourseCode
    public static CourseCode of(String code, String department, int number) {
        return new CourseCode(code, department, number);
    }

    // Method to create a new CourseCode with different number (demonstrates immutability)
    public CourseCode withNumber(int newNumber) {
        return new CourseCode(this.code, this.department, newNumber);
    }

    @Override
    public String toString() {
        return "CourseCode{" +
                "code='" + code + '\'' +
                ", department='" + department + '\'' +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CourseCode that = (CourseCode) obj;
        return number == that.number &&
               code.equals(that.code) &&
               department.equals(that.department);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(code, department, number);
    }
}
