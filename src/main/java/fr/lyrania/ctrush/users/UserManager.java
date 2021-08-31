package fr.lyrania.ctrush.users;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserManager {
    private final Set<User> users = new HashSet<>();

    public void onLogin(Player player) {
        users.add(new User(player));
    }

    public Optional<User> getUser(UUID uuid) {
        return users.stream().filter(user -> user.getUuid().toString().equals(uuid.toString())).findFirst();
    }

    public Optional<User> getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    public void delete(UUID uuid) {
        getUser(uuid).ifPresent(users::remove);
    }

    public void delete(Player player) {
        delete(player.getUniqueId());
    }

    public Set<User> getUsers() {
        return users;
    }
}
