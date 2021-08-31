package fr.lyrania.ctrush.managers.config;

import fr.lyrania.ctrush.managers.config.settings.CredentialsRedisConfig;
import fr.lyrania.ctrush.managers.config.settings.CredentialsSQLConfig;
import fr.lyrania.ctrush.managers.config.settings.LocationConfig;
import org.bukkit.Location;

public class Settings {

    private LocationConfig blue;
    private LocationConfig red;
    private LocationConfig yellow;
    private LocationConfig green;
    private LocationConfig heart;
    private LocationConfig lobby;

    private int heartlife;

    private CredentialsSQLConfig mysql;

    private CredentialsRedisConfig redis;

    public Location getBlue() {
        return blue.toBukkitLocation();
    }

    public void setBlue(LocationConfig blue) {
        this.blue = blue;
    }

    public Location getRed() {
        return red.toBukkitLocation();
    }

    public void setRed(LocationConfig red) {
        this.red = red;
    }

    public Location getYellow() {
        return yellow.toBukkitLocation();
    }

    public void setYellow(LocationConfig yellow) {
        this.yellow = yellow;
    }

    public Location getGreen() {
        return green.toBukkitLocation();
    }

    public void setGreen(LocationConfig green) {
        this.green = green;
    }

    public Location getHeart() {
        return heart.toBukkitLocation();
    }

    public void setHeart(LocationConfig heart) {
        this.heart = heart;
    }

    public int getHeartlife() {
        return heartlife;
    }

    public void setHeartlife(int heartlife) {
        this.heartlife = heartlife;
    }

    public CredentialsSQLConfig getMysql() {
        return mysql;
    }

    public void setMysql(CredentialsSQLConfig mysql) {
        this.mysql = mysql;
    }

    public CredentialsRedisConfig getRedis() {
        return redis;
    }

    public void setRedis(CredentialsRedisConfig redis) {
        this.redis = redis;
    }

    public Location getLobby() {
        return lobby.toBukkitLocation();
    }

    public void setLobby(LocationConfig lobby) {
        this.lobby = lobby;
    }
}
