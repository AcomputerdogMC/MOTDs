package net.acomputerdog.motd;

public class Motd {
    private final String name;
    private final String message;
    private final String permission;

    public Motd(String name, String message, String permissions) {
        this.name = name;
        this.message = message;
        this.permission = permissions;
    }

    public String getMessage() {
        return message;
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Motd)) return false;

        Motd motd = (Motd) o;

        return name.equals(motd.name) && message.equals(motd.message) && permission.equals(motd.permission);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + permission.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return message;
    }
}
