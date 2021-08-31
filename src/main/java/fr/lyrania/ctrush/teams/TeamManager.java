package fr.lyrania.ctrush.teams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamManager {

    private final Team blue;
    private final Team red;
    private final Team yellow;
    private final Team green;

    public TeamManager() {
        this.blue = new Team(TeamUnit.BLUE);
        this.red = new Team(TeamUnit.RED);
        this.yellow = new Team(TeamUnit.YELLOW);
        this.green = new Team(TeamUnit.GREEN);
    }

    public void join(Player player, TeamUnit teamUnit) {
        leave(player);

        Team team = getTeam(teamUnit);

        if(team != null) {
            team.getPlayers().add(player.getUniqueId());
        }
    }

    public void leave(UUID uuid) {
        blue.getPlayers().remove(uuid);
        red.getPlayers().remove(uuid);
        yellow.getPlayers().remove(uuid);
        green.getPlayers().remove(uuid);
    }

    public void leave(Player player) {
        leave(player.getUniqueId());
    }

    public Team getTeam(TeamUnit teamUnit) {
        if(teamUnit == TeamUnit.BLUE) {
            return blue;
        }
        if(teamUnit == TeamUnit.RED) {
            return red;
        }
        if(teamUnit == TeamUnit.YELLOW) {
            return yellow;
        }
        if(teamUnit == TeamUnit.GREEN) {
            return green;
        }
        return null;
    }

    public TeamUnit getPlayerTeam(UUID uuid) {
        if(blue.getPlayers().contains(uuid)) {
            return TeamUnit.BLUE;
        }
        if(red.getPlayers().contains(uuid)) {
            return TeamUnit.RED;
        }
        if(yellow.getPlayers().contains(uuid)) {
            return TeamUnit.YELLOW;
        }
        if(green.getPlayers().contains(uuid)) {
            return TeamUnit.GREEN;
        }
        return TeamUnit.NONE;
    }

    public boolean containsPlayer(Player player,TeamUnit teamUnit) {
        Team team = getTeam(teamUnit);
        if(team.getPlayers().contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public TeamUnit getPlayerTeam(Player player) {
        return getPlayerTeam(player.getUniqueId());
    }

    public boolean areAllInTeams() {
        return blue.getSize() + red.getSize() + yellow.getSize() + green.getSize() == Bukkit.getOnlinePlayers().size();
    }

    public Team getBlue() {
        return blue;
    }

    public Team getRed() {
        return red;
    }

    public Team getYellow() {
        return yellow;
    }

    public Team getGreen() {
        return green;
    }
}
