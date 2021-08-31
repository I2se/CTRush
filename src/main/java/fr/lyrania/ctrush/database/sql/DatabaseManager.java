package fr.lyrania.ctrush.database.sql;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.managers.config.Settings;

public enum DatabaseManager {

    CTRUSH(new DatabaseCredentials(getSettings().getRedis().getHost(),getSettings().getRedis().getUser(),getSettings().getRedis().getPass(),getSettings().getRedis().getNameDB(),getSettings().getRedis().getPort()));

    private DatabaseAccess databaseAccess;
    private static final Main Instance = Main.getInstance();
    private static Settings settings = Instance.getSettings();

    DatabaseManager(DatabaseCredentials credentials) {
        this.databaseAccess = new DatabaseAccess(credentials);
    }

    public DatabaseAccess getDatabaseAccess() {
        return databaseAccess;
    }

    public static void initAllDatabaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.initPool();
        }
    }

    public static void closeAllDatabaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.closePool();
        }
    }

    public static Settings getSettings() {
        return settings;
    }
}
