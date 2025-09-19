package edu.ccrm.domain;

/**
 * Represents an immutable course with Builder pattern.
 */
public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final String instructor;
    private final String semester;
    private final String department;

    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getSemester() {
        return semester;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Course{code='" + code + "', title='" + title + "', credits=" + credits +
                ", instructor='" + instructor + "', semester='" + semester + "', department='" + department + "'}";
    }

    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructor;
        private String semester;
        private String department;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder semester(String semester) {
            this.semester = semester;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
