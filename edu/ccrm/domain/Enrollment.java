package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Represents an enrollment of a student in a course.
 */
public class Enrollment {
    private Student student;
    private Course course;
    private String semester;
    private int marks;
    private Grade grade;
    private LocalDate enrollmentDate;

    public Enrollment(Student student, Course course, String semester, LocalDate enrollmentDate) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.enrollmentDate = enrollmentDate;
        this.marks = 0; // default
        this.grade = Grade.F; // default
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
        this.grade = Grade.fromMarks(marks);
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student.getFullName() +
                ", course=" + course.getTitle() +
                ", semester='" + semester + '\'' +
                ", marks=" + marks +
                ", grade=" + grade +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
