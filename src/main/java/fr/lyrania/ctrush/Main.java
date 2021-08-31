package fr.lyrania.ctrush;

import fr.lyrania.ctrush.game.Game;
import fr.lyrania.ctrush.managers.GameState;
import fr.lyrania.ctrush.managers.Registration;
import fr.lyrania.ctrush.managers.config.Settings;
import fr.lyrania.ctrush.managers.files.CTFiles;
import fr.lyrania.ctrush.managers.files.FileManager;
import fr.lyrania.ctrush.scoreboard.BoardManager;
import fr.lyrania.ctrush.teams.TeamManager;
import fr.lyrania.ctrush.users.UserManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Main extends JavaPlugin {

    private static Main Instance;
    private Settings settings;
    private TeamManager teamManager;
    private UserManager userManager;
    private Game game;

    @Override
    public void onEnable() {
        Instance = this;
        getLogger().info("Le plugin est demarré");

        setupConfigFile();
        setupResourcesConfig();

        teamManager = new TeamManager();
        userManager = new UserManager();
        game = new Game();

        GameState.setState(GameState.LOBBY);
        Registration.register();
    }

    @Override
    public void onDisable() {
        getLogger().info("Le plugin est éteint");
    }

    private void setupResourcesConfig() {
        try (final Reader reader = Files.newBufferedReader(CTFiles.CONFIG.getFile().toPath(), StandardCharsets.UTF_8)){
            final Yaml yaml = new Yaml(new CustomClassLoaderConstructor(getClassLoader()));
            yaml.setBeanAccess(BeanAccess.FIELD);
            settings = yaml.loadAs(reader, Settings.class);
            getLogger().info("Le plugin a bien prit en compte le fichier de config");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setupConfigFile() {
        String config = CTFiles.CONFIG.getFileName();
        FileManager.saveResourceAs(config, config);
    }

    public static Main getInstance() {
        return Instance;
    }

    public Settings getSettings() {
        return settings;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public Game getGame() {
        return game;
    }
}
