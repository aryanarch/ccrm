package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * Enhanced service class for creating backups using Java NIO.2 and modern Date/Time API.
 * Supports file filtering, rollback functionality, and human-readable size formatting.
 */
public class BackupService {

    private static final String BACKUP_DIR = "backups";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of(".csv", ".json", ".txt", ".xml");

    /**
     * Creates a timestamped backup of the specified source directory.
     * Only copies files with supported extensions (.csv, .json, .txt, .xml).
     *
     * @param sourceDir the source directory to backup
     * @return the path to the created backup directory
     * @throws IOException if backup creation fails
     */
    public Path createBackup(Path sourceDir) throws IOException {
        // Ensure source directory exists
        if (!Files.exists(sourceDir)) {
            throw new IOException("Source directory does not exist: " + sourceDir);
        }

        // Create timestamped backup directory
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(TIMESTAMP_FORMATTER);
        Path backupDir = Paths.get(BACKUP_DIR, timestamp);

        try {
            // Create backup directory structure
            Files.createDirectories(backupDir);
            System.out.println("Created backup directory: " + backupDir);

            // Copy files with filtering and progress tracking
            AtomicLong filesCopied = new AtomicLong(0);
            AtomicLong totalSize = new AtomicLong(0);

            try (Stream<Path> paths = Files.walk(sourceDir)) {
                paths.filter(Files::isRegularFile)
                     .filter(this::isSupportedFileType)
                     .forEach(sourcePath -> {
                         try {
                             Path targetPath = backupDir.resolve(sourceDir.relativize(sourcePath));
                             Files.createDirectories(targetPath.getParent());
                             Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                             BasicFileAttributes attrs = Files.readAttributes(sourcePath, BasicFileAttributes.class);
                             filesCopied.incrementAndGet();
                             totalSize.addAndGet(attrs.size());

                             System.out.println("Copied: " + sourcePath.getFileName() +
                                              " (" + formatSize(attrs.size()) + ")");

                         } catch (IOException e) {
                             System.err.println("Error copying file " + sourcePath + ": " + e.getMessage());
                         }
                     });
            }

            System.out.println("Backup completed successfully!");
            System.out.println("Files copied: " + filesCopied.get());
            System.out.println("Total size: " + formatSize(totalSize.get()));

            return backupDir;

        } catch (IOException e) {
            // Rollback: delete partially created backup directory
            try {
                if (Files.exists(backupDir)) {
                    deleteDirectoryRecursively(backupDir);
                    System.out.println("Rolled back incomplete backup: " + backupDir);
                }
            } catch (IOException rollbackException) {
                System.err.println("Failed to rollback backup: " + rollbackException.getMessage());
            }
            throw new IOException("Backup creation failed: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a backup of exported data files from the data directory.
     *
     * @return the path to the created backup directory
     * @throws IOException if backup creation fails
     */
    public Path createDataBackup() throws IOException {
        Path dataDir = Paths.get("data");
        if (!Files.exists(dataDir)) {
            // Create data directory if it doesn't exist
            Files.createDirectories(dataDir);
            System.out.println("Created data directory: " + dataDir);
        }
        return createBackup(dataDir);
    }

    /**
     * Checks if a file has a supported extension for backup.
     *
     * @param filePath the file path to check
     * @return true if the file type is supported
     */
    private boolean isSupportedFileType(Path filePath) {
        String fileName = filePath.getFileName().toString().toLowerCase();
        return SUPPORTED_EXTENSIONS.stream().anyMatch(fileName::endsWith);
    }

    /**
     * Recursively deletes a directory and all its contents.
     *
     * @param directory the directory to delete
     * @throws IOException if deletion fails
     */
    private void deleteDirectoryRecursively(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Computes the total size of a directory recursively.
     *
     * @param directory the directory to measure
     * @return total size in bytes
     * @throws IOException if an I/O error occurs
     */
    public static long computeTotalSize(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return 0;
        }

        AtomicLong totalSize = new AtomicLong(0);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                totalSize.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });
        return totalSize.get();
    }

    /**
     * Formats a size in bytes to human-readable format.
     *
     * @param bytes the size in bytes
     * @return formatted size string
     */
    public static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * Lists all backup directories with their sizes.
     *
     * @throws IOException if an I/O error occurs
     */
    public static void listBackups() throws IOException {
        Path backupRoot = Paths.get(BACKUP_DIR);
        if (!Files.exists(backupRoot)) {
            System.out.println("No backups found.");
            return;
        }

        System.out.println("\n=== Available Backups ===");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(backupRoot)) {
            for (Path backupDir : stream) {
                if (Files.isDirectory(backupDir)) {
                    long size = computeTotalSize(backupDir);
                    System.out.printf("%s - %s%n", backupDir.getFileName(), formatSize(size));
                }
            }
        }
    }

    /**
     * Lists files in a backup directory by depth.
     *
     * @param backupDir the backup directory to list
     * @throws IOException if an I/O error occurs
     */
    public static void listFilesByDepth(Path backupDir) throws IOException {
        if (!Files.exists(backupDir)) {
            System.out.println("Backup directory does not exist: " + backupDir);
            return;
        }

        System.out.println("\n=== Backup Contents: " + backupDir + " ===");
        Files.walkFileTree(backupDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                int depth = backupDir.relativize(dir).getNameCount();
                System.out.println("Directory: " + dir.getFileName() + " (depth: " + depth + ")");
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                int depth = backupDir.relativize(file).getNameCount();
                System.out.printf("File: %s (depth: %d, size: %s)%n",
                                file.getFileName(), depth, formatSize(attrs.size()));
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
