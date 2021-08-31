package fr.lyrania.ctrush.listeners;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.managers.GameState;
import fr.lyrania.ctrush.managers.config.Settings;
import fr.lyrania.ctrush.teams.Team;
import fr.lyrania.ctrush.teams.TeamManager;
import fr.lyrania.ctrush.teams.TeamUnit;
import fr.lyrania.ctrush.users.UserManager;
import fr.lyrania.ctrush.utils.Utils;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Main Instance = Main.getInstance();
    private Settings settings = Instance.getSettings();
    private UserManager userManager = Instance.getUserManager();
    private TeamManager teamManager = Instance.getTeamManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + "Bienvenue sur le CTRush !" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 80, 5);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);

        if(GameState.isState(GameState.GAME)) {
            if(PlayerQuit.getPlayersDeconnect().contains(player.getUniqueId())) {
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
                PlayerQuit.getPlayersDeconnect().remove(player.getUniqueId());
                player.setGameMode(GameMode.ADVENTURE);
                Utils.equipeStuffForPlayer(player);
            } else if(player.hasPermission("lyrania.acces.ctrush")){
                event.setJoinMessage("");
                player.teleport(settings.getHeart());
                player.setGameMode(GameMode.SPECTATOR);
            } else {
                player.kickPlayer("Game already started");
            }
        } else if(Bukkit.getOnlinePlayers().size() == 16) {
            player.kickPlayer("Game Full");
        } else {
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.setLevel(0);
            player.setExp(0);
            player.setHealth(20);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            player.teleport(settings.getLobby());
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1f, 1f);

            Instance.getUserManager().onLogin(player);
            Instance.getTeamManager().join(player, TeamUnit.NONE);

            Utils.updateTeamsInInventory();

            int nbrplayers = Bukkit.getOnlinePlayers().size();
            if(Instance.getGame().getStartRunnable().isStarting()) {
                if(nbrplayers < 4) {
                    Instance.getGame().stopCountdown();
                }
            } else {
                if(nbrplayers >= 4) {
                    Instance.getGame().startCountdown();
                }
            }
        }
    }
}
