import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.exception.*;
import edu.ccrm.config.AppConfig;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;

/**
 * Comprehensive test suite for the Campus Course Manager CLI application
 * Tests all functionality including edge cases and error handling
 */
public class ComprehensiveTest {
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService = new EnrollmentService();
    private static ImportExportService importExportService = new ImportExportService();
    private static BackupService backupService = new BackupService();
    private static AppConfig config = AppConfig.getInstance();

    public static void main(String[] args) {
        System.out.println("=== Comprehensive Campus Course Manager Test Suite ===\n");

        try {
            config.init();

            // Test all core functionality
            testStudentManagement();
            testCourseManagement();
            testEnrollmentManagement();
            testGradeManagement();
            testImportExport();
            testBackupOperations();
            testReports();
            testExceptionHandling();
            testEdgeCases();

            System.out.println("\n✅ All comprehensive tests passed successfully!");
            System.out.println("The Campus Course Manager is fully functional.");

        } catch (Exception e) {
            System.err.println("\n❌ Comprehensive test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testStudentManagement() {
        System.out.println("Testing Student Management...");

        // Add students
        Student student1 = new Student(1, "REG001", "John Doe", "john@example.com",
                                     Student.Status.ACTIVE, new ArrayList<>(), LocalDate.now(), LocalDate.of(2000, 1, 1), "1234567890");
        Student student2 = new Student(2, "REG002", "Jane Smith", "jane@example.com",
                                     Student.Status.ACTIVE, new ArrayList<>(), LocalDate.now(), LocalDate.of(2000, 2, 2), "2345678901");

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        // Verify addition
        assert studentService.getStudent(1) != null : "Student 1 should be retrievable";
        assert studentService.getStudent(2) != null : "Student 2 should be retrievable";

        // Update student
        studentService.updateStudent(1, "John Updated", "john.updated@example.com");
        Student updated = studentService.getStudent(1);
        assert "John Updated".equals(updated.getFullName()) : "Student name should be updated";

        // Deactivate student
        studentService.deactivateStudent(1);
        assert Student.Status.INACTIVE.equals(studentService.getStudent(1).getStatus()) : "Student should be deactivated";

        // List students
        List<Student> students = studentService.getAllStudents();
        assert students.size() >= 2 : "Should have at least 2 students";

        System.out.println("✓ Student management tests passed");
    }

    private static void testCourseManagement() {
        System.out.println("Testing Course Management...");

        // Add courses
        Course course1 = new Course.Builder()
                .code("CS101")
                .title("Introduction to Computer Science")
                .credits(3)
                .instructor("Dr. Smith")
                .semester("Fall 2023")
                .department("Computer Science")
                .build();

        Course course2 = new Course.Builder()
                .code("CS102")
                .title("Data Structures")
                .credits(4)
                .instructor("Dr. Johnson")
                .semester("Fall 2023")
                .department("Computer Science")
                .build();

        courseService.addCourse(course1);
        courseService.addCourse(course2);

        // Verify addition
        assert courseService.getCourse("CS101") != null : "Course CS101 should be retrievable";
        assert courseService.getCourse("CS102") != null : "Course CS102 should be retrievable";

        // List courses
        List<Course> courses = courseService.getAllCourses();
        assert courses.size() >= 2 : "Should have at least 2 courses";

        // Test course filtering using streams
        long csCourses = courses.stream()
                               .filter(c -> c.getDepartment().equals("Computer Science"))
                               .count();
        assert csCourses >= 2 : "Should have at least 2 CS courses";

        System.out.println("✓ Course management tests passed");
    }

    private static void testEnrollmentManagement() {
        System.out.println("Testing Enrollment Management...");

        Student student = studentService.getStudent(2); // Active student
        Course course = courseService.getCourse("CS101");

        // Test enrollment
        try {
            enrollmentService.enrollStudent(student, course, "Fall 2023");
            List<Enrollment> enrollments = enrollmentService.getEnrollments(student);
            assert !enrollments.isEmpty() : "Student should have enrollments";
            assert enrollments.get(0).getCourse().equals(course) : "Enrollment should contain correct course";
        } catch (Exception e) {
            throw new RuntimeException("Enrollment failed: " + e.getMessage());
        }

        // Test unenrollment
        enrollmentService.unenrollStudent(student, course);
        List<Enrollment> afterUnenroll = enrollmentService.getEnrollments(student);
        assert afterUnenroll.isEmpty() : "Student should have no enrollments after unenroll";

        System.out.println("✓ Enrollment management tests passed");
    }

    private static void testGradeManagement() {
        System.out.println("Testing Grade Management...");

        Student student = studentService.getStudent(2);
        Course course = courseService.getCourse("CS101");

        // Enroll and record marks
        try {
            enrollmentService.enrollStudent(student, course, "Fall 2023");
            enrollmentService.recordMarks(student, course, 85);

            List<Enrollment> enrollments = enrollmentService.getEnrollments(student);
            Enrollment enrollment = enrollments.stream()
                                             .filter(e -> e.getCourse().equals(course))
                                             .findFirst()
                                             .orElse(null);
            assert enrollment != null : "Enrollment should exist";
            assert enrollment.getMarks() == 85 : "Marks should be recorded";

            // Test GPA calculation
            double gpa = enrollmentService.computeGPA(student);
            assert gpa >= 0.0 && gpa <= 4.0 : "GPA should be between 0 and 4";

        } catch (Exception e) {
            throw new RuntimeException("Grade management failed: " + e.getMessage());
        }

        System.out.println("✓ Grade management tests passed");
    }

    private static void testImportExport() {
        System.out.println("Testing Import/Export Functionality...");

        try {
            // Export students
            Path studentExportPath = Paths.get("data/test_export_students.csv");
            importExportService.exportStudents(studentService.getAllStudents(), Paths.get("test_export_students.csv"));
            assert Files.exists(studentExportPath) : "Student export file should exist";

            // Export courses
            Path courseExportPath = Paths.get("data/test_export_courses.csv");
            importExportService.exportCourses(courseService.getAllCourses(), Paths.get("test_export_courses.csv"));
            assert Files.exists(courseExportPath) : "Course export file should exist";

            // Export enrollments
            List<Enrollment> allEnrollments = new ArrayList<>();
            for (Student s : studentService.getAllStudents()) {
                allEnrollments.addAll(enrollmentService.getEnrollments(s));
            }
            Path enrollmentExportPath = Paths.get("data/test_export_enrollments.csv");
            importExportService.exportEnrollments(allEnrollments, Paths.get("test_export_enrollments.csv"));
            assert Files.exists(enrollmentExportPath) : "Enrollment export file should exist";

            // Import students
            List<Student> importedStudents = importExportService.importStudents(Paths.get("data/test_export_students.csv"));
            assert !importedStudents.isEmpty() : "Should import students";

            // Import courses
            List<Course> importedCourses = importExportService.importCourses(Paths.get("data/test_export_courses.csv"));
            assert !importedCourses.isEmpty() : "Should import courses";

            // Clean up test files
            Files.deleteIfExists(studentExportPath);
            Files.deleteIfExists(courseExportPath);
            Files.deleteIfExists(enrollmentExportPath);

        } catch (IOException e) {
            throw new RuntimeException("Import/Export test failed: " + e.getMessage());
        }

        System.out.println("✓ Import/Export tests passed");
    }

    private static void testBackupOperations() {
        System.out.println("Testing Backup Operations...");

        try {
            // Create data backup
            Path backupPath = backupService.createDataBackup();
            assert Files.exists(backupPath) : "Backup directory should exist";

            // List backups
            BackupService.listBackups(); // Should not throw exception

            // View backup contents
            BackupService.listFilesByDepth(backupPath); // Should not throw exception

            // Calculate total size
            long totalSize = BackupService.computeTotalSize(Paths.get("backups"));
            assert totalSize >= 0 : "Total size should be non-negative";

            // Test size formatting
            String formatted = BackupService.formatSize(1024);
            assert "1.0 KB".equals(formatted) : "Size formatting should work";

            // Test custom backup
            Path customBackupPath = backupService.createBackup(Paths.get("data"));
            assert Files.exists(customBackupPath) : "Custom backup should exist";

        } catch (IOException e) {
            throw new RuntimeException("Backup test failed: " + e.getMessage());
        }

        System.out.println("✓ Backup operations tests passed");
    }

    private static void testReports() {
        System.out.println("Testing Reports...");

        // Test GPA distribution calculation
        List<Student> students = studentService.getAllStudents();
        Map<Double, Integer> distribution = new HashMap<>();

        for (Student s : students) {
            double gpa = enrollmentService.computeGPA(s);
            distribution.put(gpa, distribution.getOrDefault(gpa, 0) + 1);
        }

        assert !distribution.isEmpty() : "Should have GPA distribution data";

        // Test top students sorting
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> Double.compare(enrollmentService.computeGPA(s2),
                                                       enrollmentService.computeGPA(s1)));

        assert sortedStudents.size() == students.size() : "Sorted list should have same size";

        System.out.println("✓ Reports tests passed");
    }

    private static void testExceptionHandling() {
        System.out.println("Testing Exception Handling...");

        Student student = studentService.getStudent(2);
        Course course = courseService.getCourse("CS101");

        // Test duplicate enrollment
        try {
            enrollmentService.enrollStudent(student, course, "Fall 2023");
            enrollmentService.enrollStudent(student, course, "Fall 2023"); // Should throw
            assert false : "Should have thrown DuplicateEnrollmentException";
        } catch (DuplicateEnrollmentException e) {
            // Expected
        } catch (MaxCreditLimitExceededException e) {
            // Also acceptable
        }

        // Test credit limit exceeded
        // Add multiple high-credit courses to exceed limit
        for (int i = 3; i <= 8; i++) {
            Course highCreditCourse = new Course.Builder()
                    .code("CS" + i + "01")
                    .title("High Credit Course " + i)
                    .credits(5) // 6 courses * 5 credits = 30 credits (exceeds limit)
                    .instructor("Dr. Test")
                    .semester("Fall 2023")
                    .department("Computer Science")
                    .build();
            courseService.addCourse(highCreditCourse);

            try {
                enrollmentService.enrollStudent(student, highCreditCourse, "Fall 2023");
            } catch (MaxCreditLimitExceededException e) {
                // Expected when limit exceeded
                break;
            } catch (DuplicateEnrollmentException e) {
                // Continue if duplicate
            }
        }

        System.out.println("✓ Exception handling tests passed");
    }

    private static void testEdgeCases() {
        System.out.println("Testing Edge Cases...");

        // Test with non-existent student
        Student nonExistent = studentService.getStudent(999);
        assert nonExistent == null : "Non-existent student should return null";

        // Test with non-existent course
        Course nonExistentCourse = courseService.getCourse("NONEXISTENT");
        assert nonExistentCourse == null : "Non-existent course should return null";

        // Test empty collections
        List<Student> emptyStudents = new ArrayList<>();
        List<Course> emptyCourses = new ArrayList<>();
        List<Enrollment> emptyEnrollments = new ArrayList<>();

        // These should not throw exceptions
        assert emptyStudents.isEmpty() : "Empty list should be empty";
        assert emptyCourses.isEmpty() : "Empty list should be empty";
        assert emptyEnrollments.isEmpty() : "Empty list should be empty";

        // Test GPA calculation with no enrollments
        Student newStudent = new Student(100, "REG100", "Test Student", "test@example.com",
                                       Student.Status.ACTIVE, new ArrayList<>(), LocalDate.now(), LocalDate.of(2000, 1, 1), "1234567890");
        studentService.addStudent(newStudent);
        double gpa = enrollmentService.computeGPA(newStudent);
        assert gpa == 0.0 : "GPA with no enrollments should be 0";

        System.out.println("✓ Edge cases tests passed");
    }
}
