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

public class PluginMotd extends JavaPlugin implements Listener {
    private File motdDir;
    private MotdList motds;

    @Override
    public void onEnable() {
        motdDir = getDataFolder();
        if (!(motdDir.exists() || motdDir.mkdirs())) {
            getLogger().warning("Unable to create MOTD directory!");
        }

        motds = new MotdList(this);
        motds.load(motdDir);

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
        String cmd = command.getName().toLowerCase();
        switch (cmd) {
            case "motd":
                onCmdMotd(sender);
                break;
            case "reloadmotd":
                onCmdReload(sender);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown command!");
                break;
        }
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        //todo threads?
        motds.sendMotdsFor(e.getPlayer());
    }

    private void onCmdMotd(CommandSender sender) {
        if (sender.hasPermission("motd.command")) {
            sender.sendMessage(ChatColor.AQUA + "MOTDs for " + sender.getName() + ": ");
            motds.sendMotdsFor(sender);
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission!");
        }
    }

    private void onCmdReload(CommandSender sender) {
        if (sender.hasPermission("motd.reload")) {
            onDisable();
            onEnable();
            getLogger().info("Reloaded.");
            sender.sendMessage(ChatColor.AQUA + "Reload complete.");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission!");
        }
    }
}
