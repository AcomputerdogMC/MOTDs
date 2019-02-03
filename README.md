## MOTDs - A simple bukkit plugin to display messages when players log in

MOTDs is a simple plugin to display a message when a player logs in.
It supports permission-specific MOTDs and multiple lines are
automatically split and sent to the client separately.

The command /motd can be used to replay MOTDs displayed at login, if
the user has permission "motd.command".

MOTDs are created this way:

1. Create a new text file in the plugin data directory.
By default this is <server_root>/plugins/Motd.
2. Set the file extension to ".motd".  The filename does not matter and is ignored by the plugin.  
3. Structure the contents as follows:   
```
    $perm=motds.global         //optional permission node needed for MOTD (can be anywhere)
    Welcome to AcomputerdogMC! //actual contents                          (everything not a comment, name, or permission)
```
In this example, any player with the permission "motds.global"
will see the message "Welcome to AcomputerdogMC!"
when they log in.   

If no permission is specified, then all players will see the MOTD.
If multiple permissions are listed, then whichever appears
**last** will be the counted. 
