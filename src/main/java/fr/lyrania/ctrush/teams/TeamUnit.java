package fr.lyrania.ctrush.teams;

import org.bukkit.ChatColor;

public enum TeamUnit {

    BLUE("Bleu", ChatColor.BLUE),
    RED("Rouge", ChatColor.RED),
    YELLOW("Jaune", ChatColor.YELLOW),
    GREEN("Vert", ChatColor.GREEN),
    NONE("Sans équipe", ChatColor.DARK_PURPLE);

    private String name;
    private ChatColor color;

    TeamUnit(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public String getColoredName() {
        String coloredname = color + name;
        return coloredname;
    }
}
