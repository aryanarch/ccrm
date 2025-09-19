package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.util.AdvancedUtilities;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.config.AppConfig;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;

/**
 * Menu-driven CLI for the campus course manager.
 *
 * To enable assertions, run the program with: java -ea MainMenu
 *
 * Errors are unrecoverable (e.g., OutOfMemoryError).
 * Exceptions are recoverable and should be handled (e.g., IOException, IllegalArgumentException).
 */
public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService = new EnrollmentService();
    private static ImportExportService importExportService = new ImportExportService();
    private static BackupService backupService = new BackupService();
    private static AppConfig config = AppConfig.getInstance();

    public static void main(String[] args) {
        config.init();
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    manageStudents();
                    break;
                case 2:
                    manageCourses();
                    break;
                case 3:
                    manageEnrollments();
                    break;
                case 4:
                    recordGradesAndTranscripts();
                    break;
                case 5:
                    importExportData();
                    break;
                case 6:
                    backupAndShowSize();
                    break;
                case 7:
                    generateReports();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    printJavaSummary();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Campus Course Manager ===");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Enroll/Unenroll Students");
        System.out.println("4. Record Grades & Print Transcripts");
        System.out.println("5. Import/Export Data");
        System.out.println("6. Backup & Show Backup Size");
        System.out.println("7. Reports");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private static void manageStudents() {
        System.out.println("\n--- Manage Students ---");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Update Student");
        System.out.println("4. Deactivate Student");
        System.out.println("5. List Students");
        System.out.println("6. Demonstrate Advanced Features");
        int choice = getIntInput("Enter choice: ");

        switch (choice) {
            case 1 -> addStudent();
            case 2 -> removeStudent();
            case 3 -> updateStudent();
            case 4 -> deactivateStudent();
            case 5 -> listStudents();
            case 6 -> demonstrateAdvancedFeatures();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void demonstrateAdvancedFeatures() {
        System.out.println("\nAdvanced Java Features Demo");

        Person person = new Student(1, "REG001", "John Doe", "john@example.com",
                Student.Status.ACTIVE, null, LocalDate.now(), LocalDate.of(2000, 1, 1), "1234567890");

        System.out.println("Upcast: Person person = new Student(...)");
        System.out.println("Person role: " + person.getRole());

        if (person instanceof Student student) {
            System.out.println("Downcast successful: Student regNo = " + student.getRegNo());
        } else {
            System.out.println("Downcast failed");
        }

        Student[] students = {
            new Student(1, "REG001", "Alice", "alice@example.com", Student.Status.ACTIVE, null, LocalDate.now(), LocalDate.of(2000, 1, 1), "1111111111"),
            new Student(2, "REG002", "Bob", "bob@example.com", Student.Status.ACTIVE, null, LocalDate.now(), LocalDate.of(2000, 2, 2), "2222222222"),
            new Student(3, "REG003", "Charlie", "charlie@example.com", Student.Status.ACTIVE, null, LocalDate.now(), LocalDate.of(2000, 3, 3), "3333333333")
        };

        AdvancedUtilities.demonstrateArrays(students);
        AdvancedUtilities.demonstrateOperatorPrecedence();
        demonstrateLabeledJumps();
    }

    private static void demonstrateLabeledJumps() {
        System.out.println("\n=== Labeled Jumps Demonstration ===");

        outerLoop:
        for (int i = 1; i <= 3; i++) {
            System.out.println("Outer loop iteration: " + i);

            for (int j = 1; j <= 3; j++) {
                System.out.println("  Inner loop iteration: " + j);

                if (i == 2 && j == 2) {
                    System.out.println("    Breaking out of outer loop at i=2, j=2");
                    break outerLoop; // Labeled break
                }

                if (j == 1) {
                    System.out.println("    Continuing to next inner iteration");
                    continue; // Regular continue
                }
            }
        }
        System.out.println("Exited outer loop");
    }

    private static void addStudent() {
        int id = getIntInput("Enter student ID: ");
        String regNo = getStringInput("Enter registration number: ");
        String fullName = getStringInput("Enter full name: ");
        String email = getStringInput("Enter email: ");
        String phoneNumber = getStringInput("Enter phone number: ");
        assert id > 0 : "Student ID must be positive";
        assert regNo != null && !regNo.isEmpty() : "Registration number cannot be null or empty";
        assert fullName != null && !fullName.isEmpty() : "Full name cannot be null or empty";
        assert email != null && !email.isEmpty() : "Email cannot be null or empty";
        assert phoneNumber != null && !phoneNumber.isEmpty() : "Phone number cannot be null or empty";
        Student.Status status = Student.Status.ACTIVE;
        LocalDate enrollmentDate = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1); // Default date of birth
        Student student = new Student(id, regNo, fullName, email, status, new ArrayList<>(), enrollmentDate, dateOfBirth, phoneNumber);
        studentService.addStudent(student);
        System.out.println("Student added.");
    }

    private static void removeStudent() {
        int id = getIntInput("Enter student ID to remove: ");
        studentService.removeStudent(id);
        System.out.println("Student removed.");
    }

    private static void updateStudent() {
        int id = getIntInput("Enter student ID: ");
        String fullName = getStringInput("Enter new full name: ");
        String email = getStringInput("Enter new email: ");
        studentService.updateStudent(id, fullName, email);
        System.out.println("Student updated.");
    }

    private static void deactivateStudent() {
        int id = getIntInput("Enter student ID: ");
        studentService.deactivateStudent(id);
        System.out.println("Student deactivated.");
    }

    private static void listStudents() {
        List<Student> students = studentService.getAllStudents();
        for (Student s : students) { // enhanced for
            System.out.println(s);
        }
    }

    private static void manageCourses() {
        System.out.println("\n--- Manage Courses ---");
        System.out.println("1. Add Course");
        System.out.println("2. Remove Course");
        System.out.println("3. List Courses");
        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                removeCourse();
                break;
            case 3:
                listCourses();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addCourse() {
        String code = getStringInput("Enter course code: ");
        String title = getStringInput("Enter course title: ");
        int credits = getIntInput("Enter credits: ");
        String instructor = getStringInput("Enter instructor: ");
        String semester = getStringInput("Enter semester: ");
        String department = getStringInput("Enter department: ");
        assert code != null && !code.isEmpty() : "Course code cannot be null or empty";
        assert title != null && !title.isEmpty() : "Course title cannot be null or empty";
        assert credits > 0 : "Credits must be positive";
        assert instructor != null && !instructor.isEmpty() : "Instructor cannot be null or empty";
        assert semester != null && !semester.isEmpty() : "Semester cannot be null or empty";
        assert department != null && !department.isEmpty() : "Department cannot be null or empty";
        Course course = new Course.Builder()
                .code(code)
                .title(title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(department)
                .build();
        courseService.addCourse(course);
        System.out.println("Course added.");
    }

    private static void removeCourse() {
        String code = getStringInput("Enter course code to remove: ");
        courseService.removeCourse(code);
        System.out.println("Course removed.");
    }

    private static void listCourses() {
        List<Course> courses = courseService.getAllCourses();
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void manageEnrollments() {
        System.out.println("\n--- Enroll/Unenroll Students ---");
        System.out.println("1. Enroll Student");
        System.out.println("2. Unenroll Student");
        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1:
                enrollStudent();
                break;
            case 2:
                unenrollStudent();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void enrollStudent() {
        int studentId = getIntInput("Enter student ID: ");
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String courseCode = getStringInput("Enter course code: ");
        Course course = courseService.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        String semester = getStringInput("Enter semester: ");
        try {
            enrollmentService.enrollStudent(student, course, semester);
            System.out.println("Student enrolled.");
        } catch (MaxCreditLimitExceededException | DuplicateEnrollmentException e) {
            System.out.println("Enrollment error: " + e.getMessage());
        }
    }

    private static void unenrollStudent() {
        int studentId = getIntInput("Enter student ID: ");
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String courseCode = getStringInput("Enter course code: ");
        Course course = courseService.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        enrollmentService.unenrollStudent(student, course);
        System.out.println("Student unenrolled.");
    }

    private static void recordGradesAndTranscripts() {
        System.out.println("\n--- Record Grades & Print Transcripts ---");
        int studentId = getIntInput("Enter student ID: ");
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String courseCode = getStringInput("Enter course code: ");
        Course course = courseService.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        int marks = getIntInput("Enter marks: ");
        enrollmentService.recordMarks(student, course, marks);
        System.out.println("Marks recorded.");

        // Print transcript
        System.out.println("\n--- Transcript for " + student.getFullName() + " ---");
        List<Enrollment> enrollments = enrollmentService.getEnrollments(student);
        for (Enrollment e : enrollments) {
            System.out.println(e);
        }
        double gpa = enrollmentService.computeGPA(student);
        System.out.println("GPA: " + gpa);
    }

    private static void importExportData() {
        System.out.println("\n--- Import/Export Data ---");
        System.out.println("1. Import Students");
        System.out.println("2. Import Courses");
        System.out.println("3. Export Students");
        System.out.println("4. Export Courses");
        System.out.println("5. Export Enrollments");
        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1:
                importStudents();
                break;
            case 2:
                importCourses();
                break;
            case 3:
                exportStudents();
                break;
            case 4:
                exportCourses();
                break;
            case 5:
                exportEnrollments();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void importStudents() {
        String filePath = getStringInput("Enter file path for students CSV: ");
        try {
            List<Student> students = importExportService.importStudents(Paths.get(filePath));
            for (Student s : students) {
                studentService.addStudent(s);
            }
            System.out.println("Students imported.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error importing students: " + e.getMessage());
        } finally {
            System.out.println("Import operation completed.");
        }
    }

    private static void importCourses() {
        String filePath = getStringInput("Enter file path for courses CSV: ");
        try {
            List<Course> courses = importExportService.importCourses(Paths.get(filePath));
            for (Course c : courses) {
                courseService.addCourse(c);
            }
            System.out.println("Courses imported.");
        } catch (IOException e) {
            System.out.println("Error importing courses: " + e.getMessage());
        }
    }

    private static void exportStudents() {
        String filePath = getStringInput("Enter file path for export: ");
        try {
            importExportService.exportStudents(studentService.getAllStudents(), Paths.get(filePath));
            System.out.println("Students exported.");
        } catch (IOException e) {
            System.out.println("Error exporting students: " + e.getMessage());
        }
    }

    private static void exportCourses() {
        String filePath = getStringInput("Enter file path for export: ");
        try {
            importExportService.exportCourses(courseService.getAllCourses(), Paths.get(filePath));
            System.out.println("Courses exported.");
        } catch (IOException e) {
            System.out.println("Error exporting courses: " + e.getMessage());
        }
    }

    private static void exportEnrollments() {
        String filePath = getStringInput("Enter file path for export: ");
        try {
            List<Enrollment> allEnrollments = new ArrayList<>();
            for (Student s : studentService.getAllStudents()) {
                allEnrollments.addAll(enrollmentService.getEnrollments(s));
            }
            importExportService.exportEnrollments(allEnrollments, Paths.get(filePath));
            System.out.println("Enrollments exported.");
        } catch (IOException e) {
            System.out.println("Error exporting enrollments: " + e.getMessage());
        }
    }

    private static void backupAndShowSize() {
        System.out.println("\n--- Backup Management ---");
        System.out.println("1. Create Data Backup");
        System.out.println("2. Create Custom Backup");
        System.out.println("3. List All Backups");
        System.out.println("4. View Backup Contents");
        System.out.println("5. Show Total Backup Size");
        int choice = getIntInput("Enter choice: ");

        switch (choice) {
            case 1:
                createDataBackup();
                break;
            case 2:
                createCustomBackup();
                break;
            case 3:
                listAllBackups();
                break;
            case 4:
                viewBackupContents();
                break;
            case 5:
                showTotalBackupSize();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void createDataBackup() {
        System.out.println("\n--- Creating Data Backup ---");
        try {
            Path backupPath = backupService.createDataBackup();
            System.out.println("✅ Data backup created successfully!");
            System.out.println("📁 Backup location: " + backupPath.toAbsolutePath());

            long size = BackupService.computeTotalSize(backupPath);
            System.out.println("📊 Backup size: " + BackupService.formatSize(size));

        } catch (IOException e) {
            System.err.println("❌ Error creating data backup: " + e.getMessage());
            System.out.println("💡 Make sure you have exported some data first.");
        }
    }

    private static void createCustomBackup() {
        System.out.println("\n--- Creating Custom Backup ---");
        String sourceDir = getStringInput("Enter source directory path: ");
        Path sourcePath = Paths.get(sourceDir);

        if (!Files.exists(sourcePath)) {
            System.out.println("❌ Source directory does not exist: " + sourcePath);
            return;
        }

        if (!Files.isDirectory(sourcePath)) {
            System.out.println("❌ Path is not a directory: " + sourcePath);
            return;
        }

        try {
            Path backupPath = backupService.createBackup(sourcePath);
            System.out.println("✅ Custom backup created successfully!");
            System.out.println("📁 Backup location: " + backupPath.toAbsolutePath());

            long size = BackupService.computeTotalSize(backupPath);
            System.out.println("📊 Backup size: " + BackupService.formatSize(size));

        } catch (IOException e) {
            System.err.println("❌ Error creating custom backup: " + e.getMessage());
        }
    }

    private static void listAllBackups() {
        System.out.println("\n--- Listing All Backups ---");
        try {
            BackupService.listBackups();
        } catch (IOException e) {
            System.err.println("❌ Error listing backups: " + e.getMessage());
        }
    }

    private static void viewBackupContents() {
        System.out.println("\n--- View Backup Contents ---");
        String backupName = getStringInput("Enter backup name (e.g., 20231225_143052): ");
        Path backupPath = Paths.get("backups", backupName);

        try {
            BackupService.listFilesByDepth(backupPath);
        } catch (IOException e) {
            System.err.println("❌ Error viewing backup contents: " + e.getMessage());
            System.out.println("💡 Make sure the backup name is correct.");
        }
    }

    private static void showTotalBackupSize() {
        System.out.println("\n--- Total Backup Size ---");
        Path backupRoot = Paths.get("backups");

        try {
            if (!Files.exists(backupRoot)) {
                System.out.println("📁 No backups directory found.");
                return;
            }

            long totalSize = BackupService.computeTotalSize(backupRoot);
            System.out.println("📊 Total size of all backups: " + BackupService.formatSize(totalSize));

            // Count number of backup directories
            try (var stream = Files.list(backupRoot)) {
                long backupCount = stream.filter(Files::isDirectory).count();
                System.out.println("📂 Number of backups: " + backupCount);
            }

        } catch (IOException e) {
            System.err.println("❌ Error calculating total backup size: " + e.getMessage());
        }
    }

    private static void generateReports() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. GPA Distribution");
        System.out.println("2. Top Students");
        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1:
                gpaDistribution();
                break;
            case 2:
                topStudents();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void gpaDistribution() {
        List<Student> students = studentService.getAllStudents();
        Map<Double, Integer> distribution = new HashMap<>();
        for (Student s : students) {
            double gpa = enrollmentService.computeGPA(s);
            distribution.put(gpa, distribution.getOrDefault(gpa, 0) + 1);
        }
        System.out.println("GPA Distribution:");
        for (Map.Entry<Double, Integer> entry : distribution.entrySet()) {
            System.out.println("GPA " + entry.getKey() + ": " + entry.getValue() + " students");
        }
    }

    private static void topStudents() {
        List<Student> students = studentService.getAllStudents();
        students.sort((s1, s2) -> Double.compare(enrollmentService.computeGPA(s2), enrollmentService.computeGPA(s1)));
        System.out.println("Top Students:");
        int count = Math.min(5, students.size());
        for (int i = 0; i < count; i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s.getFullName() + " - GPA: " + enrollmentService.computeGPA(s));
        }
    }

    private static int getIntInput(String prompt) {
        int value;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            value = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } while (value < 0); // simple validation
        return value;
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void printJavaSummary() {
        System.out.println("\n--- Java Editions Summary ---");
        System.out.println("Java SE (Standard Edition): Core Java platform for desktop and server applications.");
        System.out.println("Java ME (Micro Edition): For embedded and mobile devices.");
        System.out.println("Java EE (Enterprise Edition): For large-scale enterprise applications, now Jakarta EE.");
    }
}
