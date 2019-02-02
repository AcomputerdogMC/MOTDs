package net.acomputerdog.motd;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class MotdList {
    private final List<Motd> motdList = new LinkedList<>();
    private final PluginMotd plugin;

    public MotdList(PluginMotd plugin) {
        this.plugin = plugin;
    }

    public List<Motd> getMotdsFor(Permissible sender) {
        List<Motd> motds = new LinkedList<>();
        for (Motd motd : motdList) {
            String perm = motd.getPermission();
            if (perm == null || sender.hasPermission(perm)) {
                motds.add(motd);
            }
        }
        return motds;
    }

    public void sendMotdsFor(CommandSender sender) {
        for (Motd motd : getMotdsFor(sender)) {
            sender.sendMessage(motd.getMessage());
        }
        /*
        for (Motd motd : motdList) {
            String perm = motd.getPermission();
            if (sender.hasPermission(perm)) {
                sender.sendMessage(motd.getMessage());
            }
        }
        */
    }

    public void load(File motdDir) {
        if (motdDir.isDirectory()) {
            File[] files = motdDir.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.getName().endsWith(".motd")) {
                        try {
                            String permission = null;
                            StringBuilder contents = new StringBuilder();
                            BufferedReader reader = new BufferedReader(new FileReader(f));
                            Object[] lines = reader.lines().toArray();
                            for (Object obj : lines) {
                                String line = (String) obj;
                                if (line.startsWith("$perm=")) {
                                    permission = line.substring(6);
                                } else {
                                    contents.append(line);
                                    contents.append('\n');
                                }
                            }
                            reader.close();

                            Motd motd = new Motd(contents.toString(), permission);
                            motdList.add(motd);
                        } catch (Exception e) {
                            plugin.getLogger().warning("Exception reading MOTD: " + f.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
