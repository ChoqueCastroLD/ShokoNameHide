package me.shokocc.plugins.namehide;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

public final class ShokoNameHide extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        ConfigurationLoader.LoadConfigurationFile();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Think(), 0, 1);
        registerEvents();
    }

    @Override
    public void onDisable() {
        for (Entry<UUID, List<PlayerStand>> entry : PlayerStand.Stands.entrySet()) {
            for(int i = 0; i < entry.getValue().size(); i++) {
                entry.getValue().get(i).Remove();
            }
        }
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            try {
                PlayerStand.GetStandForPlayer(p, event.getPlayer()).Remove();
            } catch (Exception e) {}
        }
    }
}
