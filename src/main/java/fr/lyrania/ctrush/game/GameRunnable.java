package fr.lyrania.ctrush.game;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.scoreboard.BoardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {

    private final Main Instance = Main.getInstance();
    private final Game game;
    private int deathmatch;
    private int time;

    public GameRunnable(Game game) {
        this.game = game;
        this.deathmatch = 600;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            BoardManager.createBoard(player);
        }

        if(time == deathmatch) {
            Bukkit.broadcastMessage("Le deathmatch commence a partir de maintenant le coeur perd 1 point de vie par seconde");
        } else if(time > deathmatch) {
            if(game.getHeartlife() != 1) {
                game.removeLife();
            }
        }

        time++;
    }

    public int getDeathmatch() {
        return deathmatch;
    }

    public void setDeathmatch(int deathmatch) {
        this.deathmatch = deathmatch;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
