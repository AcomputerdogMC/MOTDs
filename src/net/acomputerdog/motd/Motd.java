package net.acomputerdog.motd;

public class Motd {
    private final String message;
    private final String permission;

    public Motd(String message, String permissions) {
        this.message = message;
        this.permission = permissions;
    }

    public String getMessage() {
        return message;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return message;
    }
}
