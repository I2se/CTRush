package fr.lyrania.ctrush.managers.files;

import fr.lyrania.ctrush.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public enum CTFiles {

    CONFIG("config.yml");

    private final String fileName;
    private final File dataFolder;

    CTFiles(String fileName) {
        this.fileName = fileName;
        this.dataFolder = Main.getInstance().getDataFolder();
    }

    public File getFile(){
        return new File(dataFolder, fileName);
    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration config) {
        try {
            config.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }
}
