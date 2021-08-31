package fr.lyrania.ctrush.utils;

import fr.lyrania.ctrush.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WorldEventService {

    public List<String> worlds;

    public WorldEventService() {
        this.worlds = new ArrayList<>();
    }

    public String getAvailableWorldName() {
        int i = 0;
        String worldName;
        do {
            worldName = "event-" + i++;
        } while (Bukkit.getWorld(worldName) != null);
        return worldName;
    }

    public World createWorld(WorldCreator worldCreator) {
        return this.reserveWorld(new WorldCreator(this.getAvailableWorldName()).copy(worldCreator).createWorld());
    }

    public World createEmptyWorld() {
        return this.createWorld(new WorldCreator("").generator(new EmptyChunkGenerator()));
    }

    public Optional<World> createWorldFromTemplate(String worldPath) {
        File root = Main.getInstance().getServer().getWorldContainer().getAbsoluteFile();
        File source = new File(root, worldPath);
        File destination = new File(root, this.getAvailableWorldName());
        try {
            FileUtils.copyDirectory(source, destination);
            return Optional.of(this.createWorld(new WorldCreator("")));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public World reserveWorld(World world) {
        this.worlds.add(world.getName());
        return world;
    }

    public List<String> getWorlds() {
        return worlds;
    }

    public static class EmptyChunkGenerator extends ChunkGenerator {

        @Override
        public List<BlockPopulator> getDefaultPopulators(World world) {
            return Collections.emptyList();
        }

        @Override
        public boolean canSpawn(World world, int x, int z) {
            return true;
        }

        @Override
        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[16 * 16 * 128];
        }

        @Override
        public Location getFixedSpawnLocation(World world, Random random) {
            return new Location(world, 0, 128, 0);
        }
    }
}