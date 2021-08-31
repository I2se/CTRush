package fr.lyrania.ctrush.listeners;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.managers.GameState;
import fr.lyrania.ctrush.teams.TeamUnit;
import fr.lyrania.ctrush.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private Main Instance = Main.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getItem() != null) {
            int maxPlayerInTeam = Utils.getMaximumPlayerInTeam();
            Player player = event.getPlayer();
            if(GameState.isState(GameState.LOBBY)) {
                if(event.getItem().getType().equals(Material.WOOL)) {
                    switch (event.getItem().getDurability()) {
                        case 14:
                            boolean containsPlayerRED = Instance.getTeamManager().containsPlayer(player, TeamUnit.RED);
                            int playerInTeamRED = Instance.getTeamManager().getTeam(TeamUnit.RED).getSize();
                            if(containsPlayerRED) {
                                player.sendMessage("Vous êtes deja dans cette team");
                            } else {
                                if(playerInTeamRED == maxPlayerInTeam) {
                                    player.sendMessage("Team Rouge Complete");
                                } else {
                                    Instance.getTeamManager().leave(player);
                                    Instance.getTeamManager().join(player, TeamUnit.RED);
                                    player.sendMessage("Vous êtes dans la team Rouge");
                                }
                            }
                            break;
                        case 11:
                            boolean containsPlayerBLUE = Instance.getTeamManager().containsPlayer(player, TeamUnit.BLUE);
                            int playerInTeamBLUE = Instance.getTeamManager().getTeam(TeamUnit.BLUE).getSize();
                            if(containsPlayerBLUE) {
                                player.sendMessage("Vous êtes deja dans cette team");
                            } else {
                                if(playerInTeamBLUE == maxPlayerInTeam) {
                                    player.sendMessage("Team Bleu Complete");
                                } else {
                                    Instance.getTeamManager().leave(player);
                                    Instance.getTeamManager().join(player, TeamUnit.BLUE);
                                    player.sendMessage("Vous êtes dans la team Bleu");
                                }
                            }
                            break;
                        case 4:
                            boolean containsPlayerYELLOW = Instance.getTeamManager().containsPlayer(player, TeamUnit.YELLOW);
                            int playerInTeamYELLOW   = Instance.getTeamManager().getTeam(TeamUnit.YELLOW).getSize();
                            if(containsPlayerYELLOW) {
                                player.sendMessage("Vous êtes deja dans cette team");
                            } else {
                                if(playerInTeamYELLOW == maxPlayerInTeam) {
                                    player.sendMessage("Team Jaune Complete");
                                } else {
                                    Instance.getTeamManager().leave(player);
                                    Instance.getTeamManager().join(player, TeamUnit.YELLOW);
                                    player.sendMessage("Vous êtes dans la team Jaune");
                                }
                            }
                            break;
                        case 13:
                            boolean containsPlayerGREEN = Instance.getTeamManager().containsPlayer(player, TeamUnit.GREEN);
                            int playerInTeamGREEN = Instance.getTeamManager().getTeam(TeamUnit.GREEN).getSize();
                            if(containsPlayerGREEN) {
                                player.sendMessage("Vous êtes deja dans cette team");
                            } else {
                                if(playerInTeamGREEN == maxPlayerInTeam) {
                                    player.sendMessage("Team Verte Complete");
                                } else {
                                    Instance.getTeamManager().leave(player);
                                    Instance.getTeamManager().join(player, TeamUnit.GREEN);
                                    player.sendMessage("Vous êtes dans la team Verte");
                                }
                            }
                            break;
                    }
                }
            }
        }
    }
}
