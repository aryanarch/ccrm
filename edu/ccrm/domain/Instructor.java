package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Instructor class extending Person - demonstrates inheritance.
 * Represents an instructor with department and courses taught.
 */
public class Instructor extends Person {

    private String department;
    private String[] coursesTaught;

    public Instructor(int id, String fullName, String email, LocalDate dateOfBirth, String phoneNumber,
                      String department, String[] coursesTaught) {
        super(id, fullName, email, dateOfBirth, phoneNumber);
        this.department = department;
        this.coursesTaught = coursesTaught;
    }

    @Override
    public String getRole() {
        return "Instructor";
    }

    @Override
    public void performDuty() {
        System.out.println("Teaching courses and mentoring students");
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String[] getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(String[] coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    @Override
    public String toString() {
        StringBuilder courses = new StringBuilder();
        if (coursesTaught != null) {
            for (String course : coursesTaught) {
                courses.append(course).append(", ");
            }
            if (courses.length() > 0) {
                courses.setLength(courses.length() - 2); // Remove trailing comma and space
            }
        }
        return "Instructor{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department='" + department + '\'' +
                ", coursesTaught=[" + courses.toString() + "]" +
                '}';
    }
}
