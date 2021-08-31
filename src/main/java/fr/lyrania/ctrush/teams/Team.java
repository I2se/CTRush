package fr.lyrania.ctrush.teams;

import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Team {

    private TeamUnit teamUnit;
    private final Set<UUID> players;
    private boolean firstHit;
    private int damage;

    public Team(TeamUnit teamUnit) {
        this.teamUnit = teamUnit;
        this.players = new HashSet<>();
        this.firstHit = false;
    }

    public String getName() {
        return teamUnit.getName();
    }

    public ChatColor getColor() {
        return teamUnit.getColor();
    }

    public String getColoredName() {
        return teamUnit.getColoredName();
    }

    public Set<UUID> getPlayers() {
        return players;
    }

    public int getSize() {
        return players.size();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public boolean isFirstHit() {
        return firstHit;
    }

    public void setFirstHit(boolean firstHit) {
        this.firstHit = firstHit;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void addDamage() {
        this.damage++;
    }
}
