package fr.lyrania.ctrush.listeners;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.managers.config.Settings;
import fr.lyrania.ctrush.teams.TeamManager;
import fr.lyrania.ctrush.teams.TeamUnit;
import fr.lyrania.ctrush.users.UserManager;
import fr.lyrania.ctrush.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.TimerTask;

public class CollectData implements Listener {

    private final Main Instance = Main.getInstance();
    private Settings settings = Instance.getSettings();
    private TeamManager teamManager = Instance.getTeamManager();
    private UserManager userManager = Instance.getUserManager();

    @EventHandler
    public void onDeathPlayer(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.spigot().respawn();
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(settings.getHeart());
        userManager.getUser(player).get().addDeath();

        Bukkit.getScheduler().runTaskLater(Instance, new BukkitRunnable() {
            @Override
            public void run() {
                TeamUnit teamUnit = teamManager.getPlayerTeam(player);
                switch (teamUnit) {
                    case BLUE:
                        player.teleport(settings.getBlue());
                        break;
                    case RED:
                        player.teleport(settings.getRed());
                        break;
                    case YELLOW:
                        player.teleport(settings.getYellow());
                        break;
                    case GREEN:
                        player.teleport(settings.getGreen());
                        break;
                    default:break;
                }
                player.setGameMode(GameMode.ADVENTURE);
                Utils.equipeStuffForPlayer(player);
            }
        }, 40L);
    }

    @EventHandler
    public void onDamagePlayer(EntityDamageByEntityEvent event) {
        Entity death = event.getEntity();
        Entity damager = event.getDamager();
        if(death instanceof Player) {
            if(damager instanceof Player) {
                if(((Player) death).getHealth() <= 0) {
                    userManager.getUser(damager.getUniqueId()).get().addKill();
                }
            }
        }
    }
}
