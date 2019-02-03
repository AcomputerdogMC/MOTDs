package net.acomputerdog.motd;

import org.bukkit.permissions.Permissible;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Data structure that contains all of the MOTDs and provides methods to load
 * and access them
 */
public class MOTDList {
    private final List<MOTD> motds = new LinkedList<>();

    /**
     * Get all MOTDs for a user
     */
    public Stream<MOTD> getMOTDsFor(Permissible sender) {
        return motds.stream().filter(m -> !m.hasPermission() || sender.hasPermission(m.getPermission()));
    }

    /**
     * Load MOTDs from a directory
     */
    public void load(File motdDir, Logger logger) {
        try (Stream<Path> paths = Files.walk(motdDir.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(f -> f.toString().toLowerCase().endsWith(".motd"))
                    .forEach(f -> loadMOTD(f, logger));
        } catch (IOException e) {
            logger.log(Level.WARNING, "IO error occurred while enumerating MOTD files", e);
        }
    }

    private void loadMOTD(Path f, Logger logger) {
        try (Stream<String> lines = Files.lines(f)) {
            // Permission string needed.
            String permission = null;

            // Text contents of MOTD
            StringBuilder contents = new StringBuilder();

            // read and parse lines
            // TODO redesign format so that there is no need to write to the permissions variable.
            //   This will allow the stream API to be used here
            for (String line : lines.collect(Collectors.toList())) {
                // If line starts with $perm=, then use the rest as a
                // permission name instead of contents
                if (line.startsWith("$perm=")) {
                    permission = line.substring(6);
                } else {
                    contents.append(line);
                    contents.append('\n');
                }
            }

            // create and store MOTD
            MOTD motd = new MOTD(contents.toString(), permission);
            motds.add(motd);
        } catch (IOException e) {
            logger.warning("IO error reading from '" + f.toString() + "' - " + e.toString());
        }
    }
}
