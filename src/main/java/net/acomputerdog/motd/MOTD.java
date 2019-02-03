package net.acomputerdog.motd;

import java.util.Objects;

/**
 * An instance of a single MOTD message
 */
public class MOTD {
    /**
     * The message to display
     */
    private final String message;

    /**
     * Permission required to display this MOTD
     */
    private final String permission;

    public MOTD(String message, String permissions) {
        this.message = message;
        this.permission = permissions;
    }

    public String getMessage() {
        return message;
    }

    public String getPermission() {
        return permission;
    }

    public boolean hasPermission() {
        return permission != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MOTD motd = (MOTD) o;
        return Objects.equals(message, motd.message) &&
                Objects.equals(permission, motd.permission);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message, permission);
    }

    @Override
    public String toString() {
        return message;
    }
}
