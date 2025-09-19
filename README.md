# Campus Course & Records Manager (CCRM)

A comprehensive Java SE console application for managing campus course records, built with modern Java features and OOP principles.

## 📋 Project Overview

The Campus Course & Records Manager (CCRM) is a console-based Java application that provides complete management functionality for:
- **Student Management**: Add, update, deactivate, and track students
- **Course Management**: Create and manage courses with instructors and departments
- **Enrollment & Grading**: Handle student enrollments with credit limits and GPA calculation
- **File Operations**: Import/export data via CSV and automated backup system
- **Reports**: Generate GPA distributions and top student rankings

## 🚀 Evolution of Java

### Timeline of Major Java Versions
- **1995**: Java 1.0 - "Write Once, Run Anywhere" concept introduced
- **1998**: Java 2 (J2SE 1.2) - Swing GUI, Collections framework
- **2004**: Java 5 - Generics, enhanced for-loops, autoboxing, enums, annotations
- **2006**: Java 6 - Scripting support, JDBC 4.0, JAXB
- **2011**: Java 7 - Diamond operator, try-with-resources, NIO.2
- **2014**: Java 8 - Lambda expressions, Stream API, Date/Time API, Nashorn JavaScript engine
- **2017**: Java 9 - Modules (Jigsaw), JShell, private methods in interfaces
- **2018**: Java 10 - Local variable type inference (var)
- **2019**: Java 11 - LTS release, HTTP client, ZGC garbage collector
- **2020**: Java 14 - Records, text blocks, pattern matching (preview)
- **2021**: Java 17 - LTS release, sealed classes, pattern matching for switch
- **2023**: Java 21 - LTS release, virtual threads, record patterns

## 🏗️ Java Architecture Comparison

### Java Editions Overview

| Feature | Java SE (Standard Edition) | Java ME (Micro Edition) | Java EE (Enterprise Edition) |
|---------|---------------------------|-------------------------|------------------------------|
| **Target** | Desktop/Server applications | Embedded/Mobile devices | Large-scale enterprise apps |
| **Size** | Full-featured platform | Lightweight footprint | Extended enterprise features |
| **Key APIs** | Core Java APIs, Collections | CLDC/MIDP subsets | Servlets, JPA, EJB, CDI |
| **Use Cases** | Desktop apps, web servers | Mobile phones, IoT | Banking, e-commerce, ERP |
| **Current Status** | Actively maintained | Discontinued (merged into SE) | Evolved to Jakarta EE |

### JDK, JRE, and JVM Relationship

```
┌─────────────────────────────────────────────────────────────┐
│                    JDK (Java Development Kit)               │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                JRE (Java Runtime Environment)          │ │
│  │  ┌─────────────────────────────────────────────────────┐ │ │
│  │  │               JVM (Java Virtual Machine)           │ │ │
│  │  │                                                     │ │ │
│  │  │  - Class Loader                                     │ │ │
│  │  │  - Bytecode Verifier                                │ │ │
│  │  │  - Interpreter/Just-In-Time Compiler               │ │ │
│  │  │  - Garbage Collector                                │ │ │
│  │  │  - Runtime System                                   │ │ │
│  │  └─────────────────────────────────────────────────────┘ │ │
│  │                                                         │ │
│  │  - Core Libraries (java.*, javax.*)                    │ │
│  │  - Runtime binaries                                    │ │
│  └─────────────────────────────────────────────────────────┘ │
│                                                             │
│  - Development Tools (javac, javadoc, jar, etc.)           │
│  - Source code                                             │
│  - Documentation                                           │
└─────────────────────────────────────────────────────────────┘
```

**JDK**: Complete development environment for building Java applications
**JRE**: Runtime environment for executing Java applications
**JVM**: Virtual machine that executes bytecode, providing platform independence

## 🛠️ Installation & Setup

### Prerequisites
- Windows 10/11
- Minimum 4GB RAM
- 2GB free disk space

### Java Installation Steps

1. **Download JDK 17** from [Oracle JDK Downloads](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://openjdk.java.net/)

2. **Run Installer**:
   - Double-click the downloaded `.exe` file
   - Follow the installation wizard
   - Choose default installation directory (recommended)

3. **Set Environment Variables**:
   - Open System Properties → Advanced → Environment Variables
   - Add to `Path`: `C:\Program Files\Java\jdk-17\bin`
   - Create `JAVA_HOME`: `C:\Program Files\Java\jdk-17`

4. **Verify Installation**:
   ```cmd
   java -version
   javac -version
   ```

   Expected output:
   ```
   java version "17.0.8" 2023-07-18 LTS
   Java(TM) SE Runtime Environment (build 17.0.8+9-LTS-211)
   Java HotSpot(TM) 64-Bit Server VM (build 17.0.8+9-LTS-211, mixed mode, sharing)
   ```

### Eclipse IDE Setup

1. **Download Eclipse IDE** for Java Developers from [eclipse.org](https://www.eclipse.org/downloads/)

2. **Create New Java Project**:
   - File → New → Java Project
   - Project name: `CCRM`
   - JRE: Use default JRE (JavaSE-17)
   - Finish

3. **Import Source Files**:
   - Copy all `.java` files to `src` folder
   - Copy data files to project root
   - Refresh project (F5)

4. **Run Configuration**:
   - Run → Run Configurations
   - Create new Java Application
   - Main class: `edu.ccrm.cli.MainMenu`
   - Apply → Run

## 📁 Project Structure

```
CCRM/
├── src/
│   ├── edu/ccrm/
│   │   ├── cli/           # User interface layer
│   │   │   ├── MainMenu.java
│   │   │   └── Main.java
│   │   ├── config/        # Configuration management
│   │   │   └── AppConfig.java
│   │   ├── domain/        # Business entities
│   │   │   ├── Person.java (abstract)
│   │   │   ├── Student.java
│   │   │   ├── Instructor.java
│   │   │   ├── Course.java
│   │   │   ├── Enrollment.java
│   │   │   ├── Grade.java (enum)
│   │   │   ├── Semester.java (enum)
│   │   │   ├── CourseCode.java (immutable)
│   │   │   ├── Persistable.java (interface)
│   │   │   └── Searchable.java (interface)
│   │   ├── exception/     # Custom exceptions
│   │   │   ├── DuplicateEnrollmentException.java
│   │   │   └── MaxCreditLimitExceededException.java
│   │   ├── io/            # Data persistence
│   │   │   ├── ImportExportService.java
│   │   │   ├── BackupService.java
│   │   │   └── RecursiveUtil.java
│   │   ├── service/       # Business logic
│   │   │   ├── StudentService.java
│   │   │   ├── CourseService.java
│   │   │   └── EnrollmentService.java
│   │   └── util/          # Utilities
│   │       ├── UtilClass.java
│   │       ├── AdvancedUtilities.java
│   │       └── MaxCreditLimitExceededException.java
│   ├── TestSuite.java
│   ├── ComprehensiveTest.java
│   └── BackupTest.java
├── data/
│   ├── students.csv
│   └── courses.csv
├── backups/               # Auto-generated
├── README.md
└── USAGE.md
```

## 🎯 Feature Mapping Table

| Syllabus Topic | File/Class/Method | Description |
|---------------|-------------------|-------------|
| **OOP Principles** | | |
| Encapsulation | `Student.java`, `Course.java` | Private fields with getters/setters |
| Inheritance | `Person.java` → `Student.java`, `Instructor.java` | Abstract base class with inheritance |
| Abstraction | `Person.java` (abstract methods) | Abstract class and methods |
| Polymorphism | `toString()` overrides, `getRole()` | Method overriding |
| **Advanced Java** | | |
| Interfaces | `Persistable.java`, `Searchable.java` | Custom interfaces with default methods |
| Inner Classes | `AdvancedUtilities.java` (static nested & inner) | Static nested and inner classes |
| Anonymous Classes | `MainMenu.java` (comparator) | Anonymous inner class for sorting |
| Functional Interfaces | `Searchable.java` (Predicate) | Lambda expressions and predicates |
| Enhanced Switch | `MainMenu.java` (switch expressions) | Modern switch syntax |
| Labeled Jumps | `MainMenu.java` (labeled break) | break/continue with labels |
| Arrays Utilities | `AdvancedUtilities.java` | Arrays.sort, binarySearch, etc. |
| Immutability | `CourseCode.java` | Final fields, defensive copying |
| Upcast/Downcast | `MainMenu.java` | instanceof checks and casting |
| Method Overloading | `Student.java` (updateStudent) | Multiple method signatures |
| Assertions | `MainMenu.java` (assert statements) | Runtime assertions for validation |
| **Design Patterns** | | |
| Singleton | `AppConfig.java` | Single instance configuration |
| Builder | `Course.java` (Builder pattern) | Complex object construction |
| **File I/O** | | |
| NIO.2 | `ImportExportService.java`, `BackupService.java` | Path, Files, Streams |
| Streams API | `Searchable.java`, reports | Stream pipelines for data processing |
| Date/Time API | `Enrollment.java`, `BackupService.java` | LocalDate, LocalDateTime |
| **Exception Handling** | | |
| Custom Exceptions | `DuplicateEnrollmentException.java` | Checked custom exceptions |
| Try/Catch Blocks | All service classes | Comprehensive error handling |
| **Language Features** | | |
| Primitive Variables | All classes | int, double, boolean usage |
| Operators | `AdvancedUtilities.java` | Arithmetic, relational, logical, bitwise |
| Decision Structures | `MainMenu.java` | if-else, enhanced switch |
| Loops | `MainMenu.java`, `AdvancedUtilities.java` | while, do-while, for, enhanced for |
| Strings | `UtilClass.java` | substring, split, join, equals |
| Arrays | `AdvancedUtilities.java` | Array operations and utilities |

## 🚀 Running the Application

### Compilation
```bash
# Create output directory
mkdir bin

# Compile all source files
javac -d bin -cp . edu/ccrm/**/*.java *.java

# Or using PowerShell
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { javac -d bin $_.FullName }
```

### Execution
```bash
# Run main application
java -cp bin edu.ccrm.cli.MainMenu

# Run with assertions enabled
java -ea -cp bin edu.ccrm.cli.MainMenu

# Run comprehensive tests
java -cp bin ComprehensiveTest
```

### Sample Usage
```
=== Campus Course Manager ===

Main Menu:
1. Manage Students
2. Manage Courses
3. Enroll/Unenroll Students
4. Record Grades & Print Transcripts
5. Import/Export Data
6. Backup & Show Backup Size
7. Reports
8. Exit

Choose option: 1

--- Manage Students ---
1. Add Student
2. Remove Student
3. Update Student
4. Deactivate Student
5. List Students
6. Demonstrate Advanced Features

Choose option: 6

=== Advanced Java Features Demonstration ===
Upcast: Person person = new Student(...)
Person role: Student
Downcast successful: Student regNo = REG001

=== Arrays Utilities Demonstration ===
Before sorting:
1: Alice
2: Bob
3: Charlie

After sorting by name:
3: Alice
2: Bob
1: Charlie

Binary search for ID 2: Found at index 1
```

## 🧪 Testing & Validation

### Enabling Assertions
```bash
# Run with assertions enabled
java -ea -cp bin edu.ccrm.cli.MainMenu
```

### Test Coverage
- ✅ **Unit Tests**: Individual component testing
- ✅ **Integration Tests**: Service layer interactions
- ✅ **System Tests**: End-to-end CLI workflows
- ✅ **Edge Cases**: Error conditions and boundary values
- ✅ **Performance Tests**: Large dataset handling

### Sample Test Commands
```bash
# Run comprehensive test suite
java -cp bin ComprehensiveTest

# Run backup tests
java -cp bin BackupTest

# Run basic functionality tests
java -cp bin TestSuite
```

## 📊 Screenshots

### JDK Installation Verification
![JDK Version Check](screenshots/jdk_version.png)

### Eclipse Project Setup
![Eclipse New Project](screenshots/eclipse_new_project.png)

### Application Running
![Main Menu](screenshots/main_menu.png)

### Advanced Features Demo
![Advanced Features](screenshots/advanced_features.png)

### Backup System
![Backup Creation](screenshots/backup_demo.png)

## 🎓 Academic Integrity

This project was developed following the problem statement requirements. All code is original implementation demonstrating Java concepts taught in the syllabus. References used:
- Oracle Java Documentation
- Java API Specifications
- Design Patterns literature

## 📞 Support

For issues or questions:
1. Check the comprehensive test suite output
2. Verify JDK installation and environment variables
3. Ensure all source files are properly compiled
4. Review the troubleshooting section in USAGE.md

## 🔄 Future Enhancements

- GUI interface using JavaFX
- Database integration with JPA
- REST API endpoints
- Multi-threading for concurrent operations
- Advanced reporting with charts
- User authentication and authorization

---

**Built with Java 17 SE • Demonstrates 40+ Java concepts • Fully compliant with academic requirements**
