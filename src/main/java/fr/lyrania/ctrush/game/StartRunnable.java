package fr.lyrania.ctrush.game;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartRunnable extends BukkitRunnable {

    private final Main Instance = Main.getInstance();
    private final Game game;
    private boolean starting;
    private int countdown;

    public StartRunnable(Game game) {
        this.game = game;
        this.starting = false;
        this.countdown = 90;
    }

    @Override
    public void run() {
        if (countdown == 90 || countdown == 60 || countdown == 30 || countdown == 10 || (countdown <= 5 && countdown >= 1)) {
            Bukkit.broadcastMessage("Lancement de la partie dans " + countdown + " secondes" );

            for(Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.NOTE_BASS_DRUM, 1f, 1f);
            }
        }

        int nbrplayers = Bukkit.getOnlinePlayers().size();
        if(game.getStartRunnable().isStarting()) {
                if(nbrplayers == 8) {
                game.updateCountdown(30);
            } else if(nbrplayers == 16) {
                game.updateCountdown(10);
            }
        }

        if(countdown == 0) {
            cancel();
            GameState.setState(GameState.GAME);
            game.startGame();
        }

        countdown--;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }
}
