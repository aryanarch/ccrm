package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.config.AppConfig;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Service class to import and export student, course, and enrollment data using Java NIO.2 and Streams.
 */
public class ImportExportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private AppConfig config = AppConfig.getInstance();

    public List<Student> importStudents(Path filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0].trim());
                    String regNo = parts[1].trim();
                    String fullName = parts[2].trim();
                    String email = parts[3].trim();
                    Student.Status status = Student.Status.valueOf(parts[4].trim().toUpperCase());
                    LocalDate date = LocalDate.parse(parts[5].trim(), DATE_FORMATTER);
                    // enrolledCourses will be empty here; can be populated later
                    Student student = new Student(id, regNo, fullName, email, status, new ArrayList<>(), date, LocalDate.of(2000, 1, 1), "1234567890");
                    students.add(student);
                }
            });
        }
        return students;
    }

    public List<Course> importCourses(Path filePath) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String code = parts[0].trim();
                    String title = parts[1].trim();
                    int credits = Integer.parseInt(parts[2].trim());
                    String instructor = parts[3].trim();
                    String semester = parts[4].trim();
                    String department = parts[5].trim();
                    Course course = new Course.Builder()
                            .code(code)
                            .title(title)
                            .credits(credits)
                            .instructor(instructor)
                            .semester(semester)
                            .department(department)
                            .build();
                    courses.add(course);
                }
            });
        }
        return courses;
    }

    public void exportStudents(List<Student> students, Path filePath) throws IOException {
        Path fullPath = filePath.isAbsolute() ? filePath : Paths.get(config.getDataFolderPath()).resolve(filePath);
        Files.createDirectories(fullPath.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(fullPath)) {
            writer.write("id,regNo,fullName,email,status,date\n");
            for (Student s : students) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s\n",
                        s.getId(), s.getRegNo(), s.getFullName(), s.getEmail(),
                        s.getStatus().name(), s.getEnrollmentDate().format(DATE_FORMATTER)));
            }
        }
    }

    public void exportCourses(List<Course> courses, Path filePath) throws IOException {
        Path fullPath = Paths.get(config.getDataFolderPath()).resolve(filePath);
        Files.createDirectories(fullPath.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(fullPath)) {
            writer.write("code,title,credits,instructor,semester,department\n");
            for (Course c : courses) {
                writer.write(String.format("%s,%s,%d,%s,%s,%s\n",
                        c.getCode(), c.getTitle(), c.getCredits(), c.getInstructor(), c.getSemester(), c.getDepartment()));
            }
        }
    }

    public void exportEnrollments(List<Enrollment> enrollments, Path filePath) throws IOException {
        Path fullPath = Paths.get(config.getDataFolderPath()).resolve(filePath);
        Files.createDirectories(fullPath.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(fullPath)) {
            writer.write("studentId,courseCode,semester,marks,grade,enrollmentDate\n");
            for (Enrollment e : enrollments) {
                writer.write(String.format("%d,%s,%s,%d,%s,%s\n",
                        e.getStudent().getId(), e.getCourse().getCode(), e.getSemester(),
                        e.getMarks(), e.getGrade().name(), e.getEnrollmentDate().format(DATE_FORMATTER)));
            }
        }
    }
}
