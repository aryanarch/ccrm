package edu.ccrm.config;

/**
 * Singleton class for managing global application settings.
 * Uses lazy initialization and thread safety.
 */
public class AppConfig {
    private static volatile AppConfig instance;
    private String dataFolderPath;
    private String backupFolderPath;
    private String logFilePath;

    private AppConfig() {
        // Private constructor
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    public void init() {
        // Simulate loading config
        this.dataFolderPath = "data/";
        this.backupFolderPath = "backups/";
        this.logFilePath = "logs/app.log";
        System.out.println("AppConfig initialized with default values.");
    }

    public String getDataFolderPath() {
        return dataFolderPath;
    }

    public void setDataFolderPath(String dataFolderPath) {
        this.dataFolderPath = dataFolderPath;
    }

    public String getBackupFolderPath() {
        return backupFolderPath;
    }

    public void setBackupFolderPath(String backupFolderPath) {
        this.backupFolderPath = backupFolderPath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
}
