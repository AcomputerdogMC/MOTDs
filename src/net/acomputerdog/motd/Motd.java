package net.acomputerdog.motd;

import java.io.File;
import java.util.Arrays;

public class Motd {
    private final String name;
    private final String message;
    private final String[] permissions;
    public Motd(String name, String message,String... permissions) {
        this.name = name;
        this.message = message;
        this.permissions = permissions;
    }

    public String getMessage() {
        return message;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Motd)) return false;

        Motd motd = (Motd) o;

        return name.equals(motd.name) && message.equals(motd.message) && Arrays.equals(permissions, motd.permissions);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + Arrays.hashCode(permissions);
        return result;
    }

    @Override
    public String toString() {
        return message;
    }
}
