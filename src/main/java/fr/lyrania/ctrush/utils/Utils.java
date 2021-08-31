package fr.lyrania.ctrush.utils;

import fr.lyrania.ctrush.Main;
import fr.lyrania.ctrush.tools.ItemBuilder;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static int getMaximumPlayerInTeam() {
        int nbrplayers = Bukkit.getOnlinePlayers().size();
        if(nbrplayers < 5) {
            return 2;
        } else if(nbrplayers >= 5 && nbrplayers < 9) {
            return 2;
        } else {
            return 4;
        }
    }

    public static void updateTeamsInInventory() {
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        int nbrplayers = players.size();
        if(nbrplayers < 5) {
            for (Player player : players) {
                player.getInventory().clear();
                player.getInventory().setItem(1, new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.BLUE).toItemStack());
                player.getInventory().setItem(2, new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.RED).toItemStack());
            }
        } else {
            for (Player player : players) {
                player.getInventory().clear();
                player.getInventory().setItem(1, new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.BLUE).toItemStack());
                player.getInventory().setItem(2, new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.RED).toItemStack());
                player.getInventory().setItem(3, new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.YELLOW).toItemStack());
                player.getInventory().setItem(4, new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.GREEN).toItemStack());
            }
        }
    }

    public static void equipeStuffForPlayer(Player player) {

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.spigot().setUnbreakable(true);
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        sword.setItemMeta(swordMeta);

        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.spigot().setUnbreakable(true);
        pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 2 ,true);
        pickaxe.setItemMeta(pickaxeMeta);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.spigot().setUnbreakable(true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        helmet.setItemMeta(helmetMeta);

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.spigot().setUnbreakable(true);
        chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2 ,true);
        chestplate.setItemMeta(chestplateMeta);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemMeta leggingsMeta = leggings.getItemMeta();
        leggingsMeta.spigot().setUnbreakable(true);
        leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2 ,true);
        leggings.setItemMeta(leggingsMeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.spigot().setUnbreakable(true);
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2 ,true);
        boots.setItemMeta(bootsMeta);

        player.getInventory().clear();
        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, pickaxe);
        for(int i = 2; i == 9; i++) {
            player.getInventory().setItem(i, new ItemBuilder(Material.SANDSTONE).toItemStack());
        }
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
    }
}
