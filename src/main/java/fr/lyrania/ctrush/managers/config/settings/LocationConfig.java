package fr.lyrania.ctrush.managers.config.settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationConfig {

    private String world;
    private double X;
    private double Y;
    private double Z;
    private float yaw, pitch;

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        this.X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        this.Y = y;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(double z) {
            this.Z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Location toBukkitLocation() {
        return toBukkitLocation(world);
    }

    public Location toBukkitLocation(String Gameworld) {
        return new Location(Bukkit.getWorld(Gameworld), X, Y, Z);
    }
}
