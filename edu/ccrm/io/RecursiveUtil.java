package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for recursive file operations.
 */
public class RecursiveUtil {

    /**
     * Recursively computes the total size of the directory.
     * @param dir the directory path
     * @return total size in bytes
     * @throws IOException if an I/O error occurs
     */
    public static long computeTotalSize(Path dir) throws IOException {
        AtomicLong totalSize = new AtomicLong(0);
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                totalSize.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });
        return totalSize.get();
    }

    /**
     * Recursively lists all files in the directory by depth.
     * @param dir the directory path
     * @throws IOException if an I/O error occurs
     */
    public static void listFilesByDepth(Path dir) throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                int depth = dir.getNameCount();
                System.out.println("Directory: " + dir + " (depth: " + depth + ")");
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                int depth = file.getNameCount();
                System.out.println("File: " + file + " (depth: " + depth + ", size: " + attrs.size() + " bytes)");
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
