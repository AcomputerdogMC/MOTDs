package net.acomputerdog.motd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Plugin main class
 */
public class PluginMotd extends JavaPlugin implements Listener {

    /**
     * The directory from which to load MOTDs
     */
    private File motdDir;

    /**
     * Data structure containing MOTDs
     */
    private MOTDList motds;

    @Override
    public void onEnable() {
        // Set default plugin data directory to be MOTD directory
        motdDir = getDataFolder();

        // create it if missing
        if (!(motdDir.exists() || motdDir.mkdirs())) {
            getLogger().warning("Unable to create MOTD directory!");
        }

        // load MOTDs
        motds = new MOTDList();
        motds.load(motdDir, getLogger());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        motdDir = null;
        motds = null;
        HandlerList.unregisterAll((JavaPlugin) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName().toLowerCase()) {
            case "motd":
                return onCmdMotd(sender);
            case "reloadmotd":
                return onCmdReload(sender);
            default:
                sender.sendMessage(ChatColor.RED + "Unknown command!");
                return true;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        sendMotdsTo(e.getPlayer());
    }

    private boolean onCmdMotd(CommandSender sender) {
        if (sender.hasPermission("motd.command")) {
            sender.sendMessage(ChatColor.AQUA + "MOTDs for " + sender.getName() + ": ");
            sendMotdsTo(sender);
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission!");
        }

        return true;
    }

    private boolean onCmdReload(CommandSender sender) {
        if (sender.hasPermission("motd.reload")) {
            onDisable();
            onEnable();
            getLogger().info("Reloaded.");
            sender.sendMessage(ChatColor.AQUA + "Reload complete.");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission!");
        }

        return true;
    }

    /**
     * Send a user all of their MOTDs
     */
    private void sendMotdsTo(CommandSender sender) {
        motds.getMOTDsFor(sender).forEach(m -> sender.sendMessage(m.getMessage()));
    }
}
