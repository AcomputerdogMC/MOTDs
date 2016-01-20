package net.acomputerdog.motd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PluginMotd extends JavaPlugin implements Listener {
    private File motdDir;
    private MotdList motds;

    @Override
    public void onEnable() {
        motdDir = new File(getDataFolder(), "/motds/");
        if (!(motdDir.exists() || motdDir.mkdirs())) {
            getLogger().warning("Unable to create MOTD directory!");
        }

        motds = new MotdList(this);
        motds.load(motdDir);

        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("Startup complete.");
    }

    @Override
    public void onDisable() {
        motdDir = null;
        motds = null;
        getLogger().info("Shutdown complete.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("motd")) {
            if (sender.hasPermission("motd.command")) {
                sender.sendMessage(ChatColor.AQUA + "MOTDs for " + sender.getName() + ": ");
                motds.sendMotdsFor(sender);
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Unknown command!");
        }
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        //todo threads?
        motds.sendMotdsFor(e.getPlayer());
    }
}
