package fr.lyrania.ctrush.scoreboard;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.game.Game;
import fr.lyrania.ctrush.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class BoardManager {

    private static final Main Instance = Main.getInstance();
    private static TeamManager teamManager = Instance.getTeamManager();
    private static Game game = Instance.getGame();

    public BoardManager() {
    }

    public static void createBoard(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard board = scoreboardManager.getNewScoreboard();
        Objective objective = board.registerNewObjective("lobby","dummy");
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lCTRush"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score empty1 = objective.getScore(" ");
        empty1.setScore(0);

        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        int nbrplayers = players.size();
        if(nbrplayers >= 4) {
            Score bleu = objective.getScore(ChatColor.GRAY + "• " + ChatColor.BLUE + "Bleu " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + teamManager.getBlue().getDamage() + " ✖");
            bleu.setScore(1);
            Score red = objective.getScore(ChatColor.GRAY + "• " + ChatColor.RED + "Rouge " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + teamManager.getRed().getDamage() + " ✖");
            red.setScore(2);
        }
        if(nbrplayers > 4) {
            Score yellow = objective.getScore(ChatColor.GRAY + "• " + ChatColor.YELLOW + "Jaune " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + teamManager.getYellow().getDamage() + " ✖");
            yellow.setScore(3);
        }
        if(nbrplayers > 6) {
            Score green = objective.getScore(ChatColor.GRAY + "• " + ChatColor.GREEN + "Vert " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + teamManager.getGreen().getDamage() + " ✖");
            green.setScore(4);
        }

        Score empty2 = objective.getScore(" ");
        Score vie = objective.getScore(ChatColor.GRAY + "• " + ChatColor.WHITE + "Vie restante : " + ChatColor.RED + game.getHeartlife());
        Score empty3 = objective.getScore(" ");
        Score ip = objective.getScore(ChatColor.YELLOW + "play.lyrania.fr");
        if(nbrplayers == 4) {
            empty2.setScore(3);
            vie.setScore(4);
            empty3.setScore(5);
            ip.setScore(6);
        } else if(nbrplayers > 4 && nbrplayers < 7) {
            empty2.setScore(4);
            vie.setScore(5);
            empty3.setScore(6);
            ip.setScore(7);
        } else if(nbrplayers >= 7) {
            empty2.setScore(5);
            vie.setScore(6);
            empty3.setScore(7);
            ip.setScore(8);
        }

        player.setScoreboard(board);
    }
}
