package fr.lyrania.ctrush.listeners;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.managers.GameState;
import fr.lyrania.ctrush.teams.TeamManager;
import fr.lyrania.ctrush.users.UserManager;
import fr.lyrania.ctrush.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerQuit implements Listener {

    private final Main Instance = Main.getInstance();
    private UserManager userManager = Instance.getUserManager();
    private static List<UUID> playersDeconnect = new ArrayList<>();
    private TeamManager teamManager = Instance.getTeamManager();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(GameState.isState(GameState.GAME)) {
            if(event.getPlayer().hasPermission("lyrania.acces.ctrush")){
                event.setQuitMessage("");
            } else {
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "-" + ChatColor.GRAY + "] " + event.getPlayer().getDisplayName() + " a quitté la partie");
                Bukkit.broadcastMessage(ChatColor.GRAY + "Ce joueur a 3 minutes pour se reconnecter");
                playersDeconnect.add(event.getPlayer().getUniqueId());
                Bukkit.getScheduler().runTaskLater(Instance, new BukkitRunnable() {
                    @Override
                    public void run() {
                        UUID uuid = event.getPlayer().getUniqueId();
                        if(playersDeconnect.contains(uuid)) {
                            playersDeconnect.remove(uuid);
                            userManager.delete(uuid);
                            teamManager.leave(uuid);
                        }
                    }
                }, 3*60*1000);
            }
        } else {
            Utils.updateTeamsInInventory();
            userManager.delete(event.getPlayer());
        }
    }

    public static List<UUID> getPlayersDeconnect() {
        return playersDeconnect;
    }
}
