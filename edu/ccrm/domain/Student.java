package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Student class extending Person - demonstrates inheritance.
 * Shows polymorphism through method overriding.
 */
public class Student extends Person {

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    private String regNo;
    private Status status;
    private List<Enrollment> enrolledCourses;
    private LocalDate enrollmentDate;

    // Constructor demonstrating inheritance and super() call
    public Student(int id, String regNo, String fullName, String email, Status status,
                   List<Enrollment> enrolledCourses, LocalDate enrollmentDate, LocalDate dateOfBirth, String phoneNumber) {
        super(id, fullName, email, dateOfBirth, phoneNumber); // Call to parent constructor
        this.regNo = regNo;
        this.status = status;
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
        this.enrollmentDate = enrollmentDate;
    }

    // Implementing abstract methods from Person
    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void performDuty() {
        System.out.println("Studying courses and completing assignments");
    }

    // Student-specific getters and setters
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<Enrollment> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(List<Enrollment> enrolledCourses) { this.enrolledCourses = enrolledCourses; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    // Student-specific methods
    public void addEnrollment(Enrollment enrollment) {
        if (!enrolledCourses.contains(enrollment)) {
            enrolledCourses.add(enrollment);
        }
    }

    public void updateStudent(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public void deactivateStudent() {
        this.status = Status.INACTIVE;
    }

    // Method overloading examples
    public void updateStudent(String fullName) {
        this.fullName = fullName;
    }

    public void updateStudent(String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Demonstrating polymorphism - overriding toString()
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", regNo='" + regNo + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", enrolledCourses=" + enrolledCourses +
                ", enrollmentDate=" + enrollmentDate +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
