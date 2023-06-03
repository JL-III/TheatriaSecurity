package com.playtheatria.jliii.theatriasecurity;

import com.playtheatria.jliii.generalutils.enums.Status;
import com.playtheatria.jliii.generalutils.utils.Console;
import com.playtheatria.jliii.generalutils.utils.PlayerMessage;
import com.playtheatria.jliii.theatriasecurity.commands.ConsoleCommand;
import com.playtheatria.jliii.theatriasecurity.player.PlayerSecurity;
import com.playtheatria.jliii.theatriasecurity.token.TokenManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class TheatriaSecurity extends JavaPlugin implements Listener {

    private TokenManager tokenManager;
    private List<PlayerSecurity> playerSecurityList;

    @Override
    public void onEnable() {
        tokenManager = new TokenManager(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        playerSecurityList = new ArrayList<>();
        Objects.requireNonNull(Bukkit.getPluginCommand("ts")).setExecutor(new ConsoleCommand(tokenManager));
        Console.sendLog("TheatriaSecurity has been enabled.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        addPlayerIfNotExists(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        addPlayerIfNotExists(event.getPlayer());
        playerSecurityList.forEach(x -> {
            if (!x.getIsEnforced()) return;
            if (x.getPlayerUUID() == event.getPlayer().getUniqueId()) {
                if (x.getIsAuthenticated()) return;
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        addPlayerIfNotExists(event.getPlayer());
        playerSecurityList.forEach(x -> {
            if (!x.getIsEnforced()) return;
            if (x.getPlayerUUID() == event.getPlayer().getUniqueId()) {
                if (x.getIsAuthenticated()) return;
                event.setCancelled(true);
                Console.sendLog(event.getPlayer().getName() + " has attempted to run a command without being authenticated.");
                PlayerMessage.sendResponse(event.getPlayer(), "You have not been authenticated.", Status.INVALID);
            }
        });
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncChatEvent event) {
        addPlayerIfNotExists(event.getPlayer());
        playerSecurityList.forEach(x -> {
            if (!x.getIsEnforced()) return;
            if (x.getPlayerUUID() == event.getPlayer().getUniqueId()) {
                if (x.getIsAuthenticated()) return;
                if (tokenManager.isTokenValid(PlainTextComponentSerializer.plainText().serialize(event.message()))) {
                    event.setCancelled(true);
                    x.setIsAuthenticated(true);
                    PlayerMessage.sendResponse(event.getPlayer(), "You have been authenticated", Status.VALID);
                } else {
                    event.setCancelled(true);
                    Console.sendLog(event.getPlayer().getName() + " has attempted to run a command without being authenticated.");
                    PlayerMessage.sendResponse(event.getPlayer(), "You have not been authenticated.", Status.INVALID);
                }
            }
        });
    }

    public void addPlayerIfNotExists(Player player) {
        List<UUID> playerUUIDList = playerSecurityList.stream().map(PlayerSecurity::getPlayerUUID).toList();
        if (!playerUUIDList.contains(player.getUniqueId())) {
            playerSecurityList.add(new PlayerSecurity(player.getUniqueId(), player.hasPermission("theatria.security.token.enforce"), false));
        }
    }
}
