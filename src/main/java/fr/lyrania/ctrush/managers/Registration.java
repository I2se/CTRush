package fr.lyrania.ctrush.managers;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.commands.StartCMD;
import fr.lyrania.ctrush.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Registration {

    private Registration() {
        throw new IllegalStateException("Utility class");
    }

    public static void register() {
        Main Instance = Main.getInstance();
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoin(), Instance);
        pm.registerEvents(new PlayerQuit(), Instance);
        pm.registerEvents(new Cancels(), Instance);
        pm.registerEvents(new PlayerInteract(), Instance);
        pm.registerEvents(new PlayerInteractEntity(), Instance);
        pm.registerEvents(new CollectData(), Instance);

        Instance.getCommand("start").setExecutor(new StartCMD());
    }
}
