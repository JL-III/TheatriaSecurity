package com.playtheatria.jliii.theatriasecurity;

import com.playtheatria.jliii.theatriasecurity.commands.ConsoleCommand;
import com.playtheatria.jliii.theatriasecurity.player.PlayerSecurity;
import com.playtheatria.jliii.theatriasecurity.token.TokenManager;
import com.playtheatria.jliii.theatriasecurity.utils.GeneralUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
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
        GeneralUtils.sendLog("TheatriaSecurity has been enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerSecurityList.add(new PlayerSecurity(player.getUniqueId(), player.hasPermission("theatria.security.token.enforce"), false));

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        addPlayerIfNotExists(event.getPlayer());
        playerSecurityList.forEach(x -> {
            if (x.getPlayerUUID() == event.getPlayer().getUniqueId()) {
                if (x.getIsEnforced()) {
                    if (!x.getIsAuthenticated()) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        addPlayerIfNotExists(event.getPlayer());
        playerSecurityList.forEach(x -> {
            if (x.getPlayerUUID() == event.getPlayer().getUniqueId()) {
                if (x.getIsEnforced()) {
                    if (!x.getIsAuthenticated()) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("You have not been authenticated.");
                        GeneralUtils.sendLog(event.getPlayer().getName() + " has attempted to run a command without being authenticated.");
                    }
                }
            }
        });
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncChatEvent event) {
        addPlayerIfNotExists(event.getPlayer());
        playerSecurityList.forEach(x -> {
            if (x.getPlayerUUID() == event.getPlayer().getUniqueId()) {
                if (x.getIsEnforced()) {
                    if (x.getIsAuthenticated()) {
                        return;
                    }
                    if (tokenManager.isTokenValid(PlainTextComponentSerializer.plainText().serialize(event.message()))) {
                        x.setIsAuthenticated(true);
                        event.getPlayer().sendMessage("You have been authenticated.");
                        event.setCancelled(true);
                    } else {
                        event.getPlayer().sendMessage("You have not been authenticated.");
                        event.setCancelled(true);
                        GeneralUtils.sendLog(event.getPlayer().getName() + " has attempted to run a command without being authenticated.");
                    }

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
