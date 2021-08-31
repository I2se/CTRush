package fr.lyrania.ctrush.listeners;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.game.Game;
import fr.lyrania.ctrush.managers.config.Settings;
import fr.lyrania.ctrush.teams.Team;
import fr.lyrania.ctrush.teams.TeamManager;
import fr.lyrania.ctrush.users.User;
import fr.lyrania.ctrush.users.UserManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerInteractEntity implements Listener {

    private Main Instance = Main.getInstance();
    private Game game = Instance.getGame();
    private Settings settings = Instance.getSettings();
    private UserManager userManager = Instance.getUserManager();
    private TeamManager teamManager = Instance.getTeamManager();

    @EventHandler
    public void onPlayerInteractEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            EntityType entityType = event.getEntityType();
            if(entityType.equals(EntityType.ENDER_CRYSTAL)) {
                Player player = ((Player) event.getDamager()).getPlayer();
                event.setCancelled(true);
                Material item = ((Player) event.getDamager()).getItemInHand().getType();
                if(item.equals(Material.IRON_SWORD)) {
                    if(game.getHeartlife() == settings.getHeartlife()) {
                        teamManager.getTeam(teamManager.getPlayerTeam(player)).setFirstHit(true);
                    }
                    if(game.getHeartlife() == 1) {
                        player.setGameMode(GameMode.SPECTATOR);
                        event.setCancelled(false);
                        game.win();
                    }
                    game.removeLife();
                    userManager.getUser(player).get().addDamage();
                    teamManager.getTeam(teamManager.getPlayerTeam(player)).addDamage();
                }
            }
        }
    }
}
