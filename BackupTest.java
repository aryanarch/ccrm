import edu.ccrm.io.BackupService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Simple test for backup functionality
 */
public class BackupTest {
    public static void main(String[] args) {
        System.out.println("=== Backup Functionality Test ===\n");

        try {
            BackupService backupService = new BackupService();

            // Test 1: Create data backup
            System.out.println("Test 1: Creating data backup...");
            Path backupPath = backupService.createDataBackup();
            System.out.println("✅ Data backup created at: " + backupPath);

            // Test 2: List backups
            System.out.println("\nTest 2: Listing all backups...");
            BackupService.listBackups();

            // Test 3: View backup contents
            System.out.println("\nTest 3: Viewing backup contents...");
            BackupService.listFilesByDepth(backupPath);

            // Test 4: Show total backup size
            System.out.println("\nTest 4: Calculating total backup size...");
            long totalSize = BackupService.computeTotalSize(Paths.get("backups"));
            System.out.println("📊 Total backup size: " + BackupService.formatSize(totalSize));

            System.out.println("\n✅ All backup tests passed successfully!");

        } catch (IOException e) {
            System.err.println("❌ Backup test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
