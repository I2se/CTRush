package fr.lyrania.ctrush.users;

import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private final String name;
    private int kills;
    private int death;
    private int damage;

    public User(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getKills() {
        return kills;
    }

    public int getDeath() {
        return death;
    }

    public int getDamage() {
        return damage;
    }

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.death++;
    }

    public void addDamage() {
        this.damage++;
    }
}
