package fr.lyrania.ctrush.commands;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCMD implements CommandExecutor {

    private final Main Instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.isOp()) {
                if(label.equalsIgnoreCase("start")) {
                    if(args.length == 0) {
                        if(GameState.isState(GameState.LOBBY)) {
                            Instance.getGame().startCountdown();
                            player.sendMessage("Lancement de la partie");
                        } else if(GameState.isState(GameState.END)) {
                            player.sendMessage("Serveur not ready");
                        } else {
                            player.sendMessage("Game already started");
                        }
                    } else if(args.length == 1) {
                        Instance.getGame().updateCountdown(10);
                        Instance.getGame().startCountdown();
                    } else if(args.length == 2) {
                        Instance.getGame().regenMap();
                    } else if(args.length == 3) {
                        WorldCreator wc = new WorldCreator("map");
                        Bukkit.createWorld(wc);
                    }
                }
            }
        }
        return false;
    }
}
