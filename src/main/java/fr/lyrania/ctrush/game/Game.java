package fr.lyrania.ctrush.game;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.database.sql.DatabaseManager;
import fr.lyrania.ctrush.managers.GameState;
import fr.lyrania.ctrush.managers.config.Settings;
import fr.lyrania.ctrush.scoreboard.BoardManager;
import fr.lyrania.ctrush.teams.Team;
import fr.lyrania.ctrush.teams.TeamManager;
import fr.lyrania.ctrush.teams.TeamUnit;
import fr.lyrania.ctrush.users.User;
import fr.lyrania.ctrush.users.UserManager;
import fr.lyrania.ctrush.utils.ReturnConfig;
import fr.lyrania.ctrush.utils.Utils;
import fr.lyrania.ctrush.utils.WorldEventService;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Game {

    private final Main Instance = Main.getInstance();
    private TeamManager teamManager = Instance.getTeamManager();
    private UserManager userManager = Instance.getUserManager();
    private Settings settings = Instance.getSettings();

    private StartRunnable startRunnable;
    private GameRunnable gameRunnable;
    private int heartlife;
    private ArmorStand armorStand;

    public Game() {
        this.startRunnable = new StartRunnable(this);
        this.gameRunnable = new GameRunnable(this);
        this.heartlife = settings.getHeartlife();
    }

    public void startCountdown() {
        startRunnable.runTaskTimer(Instance, 0, 20L);
        startRunnable.setStarting(true);
    }

    public void stopCountdown() {
        if(startRunnable.getCountdown() > 0) {
            startRunnable.cancel();
            startRunnable = new StartRunnable(this);
        }
    }

    public void updateCountdown(int newCountDown) {
        if(startRunnable.getCountdown() <= newCountDown) {
            return;
        } else {
            startRunnable.setCountdown(newCountDown);
        }
    }

    public void startGame() {

        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        int nbrplayers = players.size();
        boolean allInteam = teamManager.areAllInTeams();
        Team blue = teamManager.getBlue();
        Team red = teamManager.getRed();
        Team yellow = teamManager.getYellow();
        Team green = teamManager.getGreen();

        int blueplayers = blue.getSize();
        int redplayers = red.getSize();
        int yellowplayers = yellow.getSize();
        int greenplayers = green.getSize();

        if(!allInteam) {
            List<Player> playerWithoutTeam = new ArrayList<>();
            for (Player player : players) {
                if(teamManager.getPlayerTeam(player).equals(TeamUnit.NONE)) {
                    playerWithoutTeam.add(player);
                }
            }
            int playerwithoutteam = playerWithoutTeam.size();
            int config = ReturnConfig.getConfigTeam(nbrplayers);
            switch (config) {
                case 6:
                    int loop = nbrplayers - 12;
                    for(int i = 0; i == loop; i++) {
                        HashMap<Integer, TeamUnit> teamAndNumber = new HashMap<>();
                        List<Integer> valueattributeToTeam = new ArrayList<>();
                        if(blueplayers < 4) {
                            teamAndNumber.put(1, TeamUnit.BLUE);
                            valueattributeToTeam.add(1);
                        }
                        if(redplayers < 4) {
                            teamAndNumber.put(2, TeamUnit.RED);
                            valueattributeToTeam.add(2);
                        }
                        if(yellowplayers < 4) {
                            teamAndNumber.put(3, TeamUnit.YELLOW);
                            valueattributeToTeam.add(3);
                        }
                        if(greenplayers < 4) {
                            teamAndNumber.put(4, TeamUnit.GREEN);
                            valueattributeToTeam.add(4);
                        }
                        int sizeList = valueattributeToTeam.size();
                        Random random = new Random();
                        int randomplayer = random.nextInt(playerwithoutteam);
                        int randomteam = random.nextInt(sizeList);
                        Player player = playerWithoutTeam.get(randomplayer);
                        TeamUnit teamUnit = teamAndNumber.get(valueattributeToTeam.get(randomteam));
                        teamManager.getTeam(teamUnit).getPlayers().add(player.getUniqueId());
                        playerWithoutTeam.remove(player);
                        teamAndNumber.clear();
                        valueattributeToTeam.clear();
                    }
                case 5:
                    int loop2 = nbrplayers - 8;
                    for(int i = 0; i == loop2; i++) {
                        HashMap<Integer, TeamUnit> teamAndNumber = new HashMap<>();
                        List<Integer> valueattributeToTeam = new ArrayList<>();
                        if(blueplayers < 3) {
                            teamAndNumber.put(1, TeamUnit.BLUE);
                            valueattributeToTeam.add(1);
                        }
                        if(redplayers < 3) {
                            teamAndNumber.put(2, TeamUnit.RED);
                            valueattributeToTeam.add(2);
                        }
                        if(yellowplayers < 3) {
                            teamAndNumber.put(3, TeamUnit.YELLOW);
                            valueattributeToTeam.add(3);
                        }
                        if(greenplayers < 3) {
                            teamAndNumber.put(4, TeamUnit.GREEN);
                            valueattributeToTeam.add(4);
                        }
                        int sizeList = valueattributeToTeam.size();
                        Random random = new Random();
                        int randomplayer = random.nextInt(playerwithoutteam);
                        int randomteam = random.nextInt(sizeList);
                        Player player = playerWithoutTeam.get(randomplayer);
                        TeamUnit teamUnit = teamAndNumber.get(valueattributeToTeam.get(randomteam));
                        teamManager.getTeam(teamUnit).getPlayers().add(player.getUniqueId());
                        playerWithoutTeam.remove(player);
                        teamAndNumber.clear();
                        valueattributeToTeam.clear();
                    }
                case 4:
                    if(greenplayers == 2) {
                        System.out.println("Team Bleu Complete");
                    } else if(greenplayers == 1) {
                        Random random = new Random();
                        int rdm = random.nextInt(playerwithoutteam);
                        Player player = playerWithoutTeam.get(rdm);
                        green.getPlayers().add(player.getUniqueId());
                        playerWithoutTeam.remove(player);
                    } else if(greenplayers == 0) {
                        for(int i = 0; i == 2; i++) {
                            Random random = new Random();
                            int rdm = random.nextInt(playerwithoutteam);
                            Player player = playerWithoutTeam.get(rdm);
                            green.getPlayers().add(player.getUniqueId());
                            playerWithoutTeam.remove(player);
                        }
                    }
                case 3:
                    if(yellowplayers == 2) {
                        System.out.println("Team Bleu Complete");
                    } else if(yellowplayers == 1) {
                        Random random = new Random();
                        int rdm = random.nextInt(playerwithoutteam);
                        Player player = playerWithoutTeam.get(rdm);
                        yellow.getPlayers().add(player.getUniqueId());
                        playerWithoutTeam.remove(player);
                    } else if(yellowplayers == 0) {
                        for(int i = 0; i == 2; i++) {
                            Random random = new Random();
                            int rdm = random.nextInt(playerwithoutteam);
                            Player player = playerWithoutTeam.get(rdm);
                            yellow.getPlayers().add(player.getUniqueId());
                            playerWithoutTeam.remove(player);
                        }
                    }
                case 2:
                    if(blueplayers == 2) {
                        System.out.println("Team Bleu Complete");
                    } else if(blueplayers == 1) {
                        Random random = new Random();
                        int rdm = random.nextInt(playerwithoutteam);
                        Player player = playerWithoutTeam.get(rdm);
                        blue.getPlayers().add(player.getUniqueId());
                        playerWithoutTeam.remove(player);
                    } else if(blueplayers == 0) {
                        for(int i = 0; i == 2; i++) {
                            Random random = new Random();
                            int rdm = random.nextInt(playerwithoutteam);
                            Player player = playerWithoutTeam.get(rdm);
                            blue.getPlayers().add(player.getUniqueId());
                            playerWithoutTeam.remove(player);
                        }
                    }
                    if(redplayers == 2) {
                        System.out.println("Team Rouge Complete");
                    } else if(redplayers == 1) {
                        Random random = new Random();
                        int rdm = random.nextInt(playerwithoutteam);
                        Player player = playerWithoutTeam.get(rdm);
                        red.getPlayers().add(player.getUniqueId());
                        playerWithoutTeam.remove(player);
                    } else if(redplayers == 0) {
                        for(int i = 0; i == 2; i++) {
                            Random random = new Random();
                            int rdm = random.nextInt(playerwithoutteam);
                            Player player = playerWithoutTeam.get(rdm);
                            red.getPlayers().add(player.getUniqueId());
                            playerWithoutTeam.remove(player);
                        }
                    }
                    break;
            }
        }

        Set<UUID> playersBlue = teamManager.getBlue().getPlayers();
        for (UUID uuid : playersBlue) {
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(settings.getBlue());
            Utils.equipeStuffForPlayer(player);
        }
        Set<UUID> playersRed = teamManager.getRed().getPlayers();
        for (UUID uuid : playersRed) {
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(settings.getRed());
            Utils.equipeStuffForPlayer(player);
        }
        Set<UUID> playersYellow = teamManager.getYellow().getPlayers();
        for (UUID uuid : playersYellow) {
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(settings.getYellow());
            Utils.equipeStuffForPlayer(player);
        }
        Set<UUID> playersGreen = teamManager.getGreen().getPlayers();
        for (UUID uuid : playersGreen) {
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(settings.getGreen());
            Utils.equipeStuffForPlayer(player);
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            BoardManager.createBoard(player);
        }

        Bukkit.getServer().getWorld(settings.getHeart().getWorld().getName()).spawnEntity(settings.getHeart(), EntityType.ENDER_CRYSTAL);

        Location locationHealth = settings.getHeart();
        double locY = locationHealth.getY();
        locationHealth.setY(locY + 0.75);

        armorStand = (ArmorStand) locationHealth.getWorld().spawnEntity(locationHealth, EntityType.ARMOR_STAND);
        armorStand.setBasePlate(false);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setSmall(false);
        armorStand.setCustomName("Health " + heartlife + ChatColor.RED + " ❤");
        armorStand.setCustomNameVisible(true);
    }

    public void win() {
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player : players) {
            player.spigot().respawn();
            player.getInventory().clear();
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1f, 1f);
        }

        gameRunnable.cancel();

        GameState.setState(GameState.END);

        Bukkit.getScheduler().runTaskLater(Instance, new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : players) {
                    player.teleport(settings.getLobby());
                    userManager.delete(player);
                    userManager.onLogin(player);
                    teamManager.leave(player);
                }
            }
        }, 200L);

        for(User user : userManager.getUsers()) {
            try {
                int kill = 0;
                int death = 0;
                int damage = 0;
                String time = "";
                Connection connection = DatabaseManager.CTRUSH.getDatabaseAccess().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE uuid = ?");

                preparedStatement.setString(1, user.getUuid().toString());
                preparedStatement.executeQuery();
                ResultSet resultSet = preparedStatement.getResultSet();

                if(resultSet.next()) {
                    kill = resultSet.getInt("killed");
                    death = resultSet.getInt("death");
                    damage = resultSet.getInt("damages");
                    time = resultSet.getString("time_played");
                }

                int newkill = kill + user.getKills();
                int newdeath = death + user.getDeath();
                int newdamage = damage + user.getDamage();
                int newtime = Integer.valueOf(time);

                PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE servers SET killed = ?, death = ?, damages = ?, time_played = ? WHERE uuid = ?");

                preparedStatement1.setInt(1, newkill);
                preparedStatement1.setInt(2, newdeath);
                preparedStatement1.setInt(3, newdamage);
                preparedStatement1.setString(4, String.valueOf(newtime));
                preparedStatement1.setString(5, user.getUuid().toString());

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        Team blue = teamManager.getBlue();
        Team red = teamManager.getRed();
        Team yellow = teamManager.getYellow();
        Team green = teamManager.getGreen();

        List<Team> teams = new ArrayList<>();

        if(blue.isFirstHit()) {
            teams.add(blue);
            teams.add(red);
            teams.add(yellow);
            teams.add(green);
        }
        if(red.isFirstHit()) {
            teams.add(red);
            teams.add(blue);
            teams.add(yellow);
            teams.add(green);
        }
        if(yellow.isFirstHit()) {
            teams.add(yellow);
            teams.add(red);
            teams.add(blue);
            teams.add(green);
        }
        if(green.isFirstHit()) {
            teams.add(green);
            teams.add(red);
            teams.add(yellow);
            teams.add(blue);
        }
        teams.stream().sorted(Comparator.comparing(Team::getDamage).reversed()).findFirst().ifPresent(team -> {
            for (Player player : players) {
                IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + "La team " + team.getColoredName() + " a gagné !!" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
                PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
                PacketPlayOutTitle length = new PacketPlayOutTitle(5, 80, 5);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
            }
        });

        blue.setFirstHit(false);
        blue.setDamage(0);
        red.setFirstHit(false);
        red.setDamage(0);
        yellow.setFirstHit(false);
        yellow.setDamage(0);
        green.setFirstHit(false);
        green.setDamage(0);

        regenMap();
    }

    public void regenMap() {
        if(Bukkit.getWorld("map") != null ){
            if(Bukkit.getWorld("map").getWorldFolder() != null) {
                File file = Bukkit.getWorld("map").getWorldFolder();

                Bukkit.unloadWorld("map", false);

                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        WorldEventService worldEventService = new WorldEventService();

    }

    public StartRunnable getStartRunnable() {
        return startRunnable;
    }

    public int getHeartlife() {
        return heartlife;
    }

    public void removeLife() {
        int life = heartlife--;
        life--;
        armorStand.setCustomName("Health " + life + ChatColor.RED + " ❤");
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }
}
